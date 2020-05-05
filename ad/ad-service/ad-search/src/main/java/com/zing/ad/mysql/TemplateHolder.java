package com.zing.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.zing.ad.mysql.constant.OpType;
import com.zing.ad.mysql.dto.ParseTemplate;
import com.zing.ad.mysql.dto.TableTemplate;
import com.zing.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@Component
public class TemplateHolder {

    private String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    private ParseTemplate template;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = cl.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()}, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((updateFields != null && updateFields.contains(colName))
                        || (insertFields != null && insertFields.contains(colName))
                        || (deleteFields != null && deleteFields.contains(colName))) {
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }

}
