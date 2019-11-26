package com.zing.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.zing.ad.mysql.TemplateHolder;
import com.zing.ad.mysql.dto.BinlogRowData;
import com.zing.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    @Autowired
    private TemplateHolder templateHolder;

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String dbName, String tableName, IListener listener) {
        log.info("register: {}-{}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), listener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_WRITE_ROWS && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        String key = genKey(dbName, tableName);
        IListener listener = listenerMap.get(key);
        if (listener == null) {
            log.debug("skip {}", key);
            return;
        }

        try {
            BinlogRowData rowData = build(event.getData());
            if (rowData == null) {
                return;
            }

            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            dbName = "";
            tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    private BinlogRowData build(EventData eventData) {
        TableTemplate table = templateHolder.getTable(tableName);
        if (table == null) {
            log.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();

            int len = after.length;

            for (int i = 0; i < len; i++) {
                String colName = table.getPosMap().get(i);
                if (colName == null) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }

            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
