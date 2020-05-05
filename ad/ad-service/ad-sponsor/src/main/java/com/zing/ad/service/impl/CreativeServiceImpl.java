package com.zing.ad.service.impl;

import com.zing.ad.entity.Creative;
import com.zing.ad.repository.CreativeRepository;
import com.zing.ad.service.ICreativeService;
import com.zing.ad.vo.CreativeRequest;
import com.zing.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    @Autowired
    private CreativeRepository creativeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
