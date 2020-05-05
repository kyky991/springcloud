package com.zing.ad.sender;

import com.zing.ad.mysql.dto.MySqlRowData;

/**
 * @author Zing
 * @date 2019-11-26
 */
public interface ISender {

    void sender(MySqlRowData rowData);

}
