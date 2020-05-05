package com.zing.cloud.house.user.service.impl;

import com.zing.cloud.house.user.common.PageParams;
import com.zing.cloud.house.user.mapper.AgencyMapper;
import com.zing.cloud.house.user.model.Agency;
import com.zing.cloud.house.user.model.User;
import com.zing.cloud.house.user.service.IAgencyService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AgencyServiceImpl implements IAgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String IMG_PREFIX;

    @Override
    public List<Agency> getAllAgency() {
        return agencyMapper.select(new Agency());
    }

    @Override
    public Agency getAgency(Integer id) {
        Agency agency = new Agency();
        agency.setId(id);
        List<Agency> agencies = agencyMapper.select(agency);
        if (agencies.isEmpty()) {
            return null;
        }
        return agencies.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Agency agency) {
        return agencyMapper.insert(agency);
    }

    @Override
    public Pair<List<User>, Long> getAllAgent(PageParams pageParams) {
        List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
        setImg(agents);
        Long count = agencyMapper.selectAgentCount(new User());
        return ImmutablePair.of(agents, count);
    }

    private void setImg(List<User> list) {
        list.forEach(user -> user.setAvatar(IMG_PREFIX + user.getAvatar()));
    }

    /**
     * 访问user表获取详情 添加用户头像
     *
     * @param userId
     * @return
     */
    @Override
    public User getAgentDetail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> list = agencyMapper.selectAgent(user, PageParams.build(1, 1));
        if (!list.isEmpty()) {
            User agent = list.get(0);
            //将经纪人关联的经纪机构也一并查询出来
            Agency agency = new Agency();
            agency.setId(agent.getAgencyId().intValue());
            List<Agency> agencies = agencyMapper.select(agency);
            if (!agencies.isEmpty()) {
                agent.setAgencyName(agencies.get(0).getName());
            }
            return agent;
        }
        return null;
    }

}
