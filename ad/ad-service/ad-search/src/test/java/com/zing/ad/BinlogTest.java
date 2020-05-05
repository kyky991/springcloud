package com.zing.ad;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
public class BinlogTest {

    public static void main(String[] args) throws Exception {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "123456");
//        client.setBinlogFilename();
//        client.setBinlogPosition();

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data != null) {
                if (data instanceof WriteRowsEventData) {
                    log.info("write");
                } else if (data instanceof UpdateRowsEventData) {
                    log.info("update");
                } else if (data instanceof DeleteRowsEventData) {
                    log.info("delete");
                }
                log.info(data.toString());
            }
        });

        client.connect();
    }

}
