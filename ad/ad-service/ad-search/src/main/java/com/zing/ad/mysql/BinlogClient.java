package com.zing.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.zing.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    @Autowired
    private BinlogConfig config;

    @Autowired
    private AggregationListener listener;

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
            if (StringUtils.isNotEmpty(config.getBinlogName()) && !config.getPosition().equals(-1)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql stop");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }).start();
    }

    public void close() {
        try {
            client.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
