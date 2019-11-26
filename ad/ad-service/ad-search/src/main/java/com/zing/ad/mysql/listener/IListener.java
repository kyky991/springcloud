package com.zing.ad.mysql.listener;

import com.zing.ad.mysql.dto.BinlogRowData;

/**
 * @author Zing
 * @date 2019-11-26
 */
public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);

}
