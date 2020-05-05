package com.zing.cloud.house.api.service;


import com.zing.cloud.house.api.common.PageData;
import com.zing.cloud.house.api.common.PageParams;
import com.zing.cloud.house.api.dao.UserDao;
import com.zing.cloud.house.api.model.Agency;
import com.zing.cloud.house.api.model.ListResponse;
import com.zing.cloud.house.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    @Autowired
    private UserDao userDao;


    public List<Agency> getAllAgency() {
        return userDao.getAllAgency();
    }

    public Agency getAgency(Integer id) {
        return userDao.getAgencyById(id);
    }

    public void add(Agency agency) {
        userDao.addAgency(agency);
    }

    public PageData<User> getAllAgent(PageParams pageParams) {
        ListResponse<User> result = userDao.getAgentList(pageParams.getLimit(), pageParams.getOffset());
        Long count = result.getCount();
        return PageData.<User>buildPage(result.getList(), count, pageParams.getPageSize(), pageParams.getPageNum());
    }

    public User getAgentDetail(Long id) {
        return userDao.getAgentById(id);
    }

}
