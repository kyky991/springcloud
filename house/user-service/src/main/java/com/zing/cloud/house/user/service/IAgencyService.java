package com.zing.cloud.house.user.service;


import com.zing.cloud.house.user.common.PageParams;
import com.zing.cloud.house.user.model.Agency;
import com.zing.cloud.house.user.model.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IAgencyService {

    List<Agency> getAllAgency();

    Agency getAgency(Integer id);

    int add(Agency agency);

    Pair<List<User>, Long> getAllAgent(PageParams pageParams);

    User getAgentDetail(Long userId);

}
