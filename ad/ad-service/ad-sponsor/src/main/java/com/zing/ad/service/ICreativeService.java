package com.zing.ad.service;

import com.zing.ad.vo.CreativeRequest;
import com.zing.ad.vo.CreativeResponse;

/**
 * @author Zing
 * @date 2019-11-22
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);

}
