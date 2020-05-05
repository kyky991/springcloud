package com.zing.cloud.house.hsrv.service;

import java.util.List;

import com.zing.cloud.house.hsrv.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zing.cloud.house.hsrv.mapper.CityMapper;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> getAllCitys() {
        City query = new City();
        return cityMapper.selectCitys(query);
    }

}
