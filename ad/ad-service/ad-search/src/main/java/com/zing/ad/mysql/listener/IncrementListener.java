package com.zing.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.zing.ad.mysql.constant.Constant;
import com.zing.ad.mysql.constant.OpType;
import com.zing.ad.mysql.dto.BinlogRowData;
import com.zing.ad.mysql.dto.MySqlRowData;
import com.zing.ad.mysql.dto.TableTemplate;
import com.zing.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Autowired
    private ISender sender;

    @Autowired
    private AggregationListener aggregationListener;

    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (fieldList == null) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> map : eventData.getAfter()) {
            Map<String, String> after = new HashMap<>();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                after.put(entry.getKey(), entry.getValue());
            }

            rowData.getFieldValueMap().add(after);
        }

        sender.sender(rowData);
    }
}
