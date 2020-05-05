package com.zing.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private Long userId;

    private String username;

    private String token;

    private Date createTime;

    private Date updateTime;

}
