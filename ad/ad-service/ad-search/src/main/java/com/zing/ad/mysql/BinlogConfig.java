package com.zing.ad.mysql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
