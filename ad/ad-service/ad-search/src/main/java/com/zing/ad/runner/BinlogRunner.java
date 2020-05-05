package com.zing.ad.runner;

import com.zing.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    @Autowired
    private BinlogClient client;

    @Override
    public void run(String... args) throws Exception {
        log.info("coming in BinlogRunner");
        client.connect();
    }
}
