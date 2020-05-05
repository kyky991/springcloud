package com.zing.ad.service;

import com.alibaba.fastjson.JSON;
import com.zing.ad.Application;
import com.zing.ad.constant.CommonStatus;
import com.zing.ad.dump.DConstant;
import com.zing.ad.dump.table.*;
import com.zing.ad.entity.AdPlan;
import com.zing.ad.entity.AdUnit;
import com.zing.ad.entity.Creative;
import com.zing.ad.entity.condition.AdUnitDistrict;
import com.zing.ad.entity.condition.AdUnitIt;
import com.zing.ad.entity.condition.AdUnitKeyword;
import com.zing.ad.entity.condition.CreativeUnit;
import com.zing.ad.repository.AdPlanRepository;
import com.zing.ad.repository.AdUnitRepository;
import com.zing.ad.repository.CreativeRepository;
import com.zing.ad.repository.condition.AdUnitDistrictRepository;
import com.zing.ad.repository.condition.AdUnitItRepository;
import com.zing.ad.repository.condition.AdUnitKeywordRepository;
import com.zing.ad.repository.condition.CreativeUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository planRepository;

    @Autowired
    private AdUnitRepository unitRepository;

    @Autowired
    private CreativeRepository creativeRepository;

    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;

    @Autowired
    private AdUnitItRepository unitItRepository;

    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;

    @Test
    public void dumpTableData() {
        dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        dumpCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        dumpCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        dumpUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        dumpUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        dumpUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
    }

    private <T> void write(String fileName, List<T> list) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (T t : list) {
                writer.write(JSON.toJSONString(t));
                writer.newLine();
            }
        } catch (Exception e) {
            log.error("dump table error: {}", fileName);
        }
    }

    private void dumpAdPlanTable(String fileName) {
        List<AdPlan> list = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<AdPlanTable> tables = list.stream()
                .map(t -> new AdPlanTable(t.getId(), t.getUserId(), t.getPlanStatus(), t.getStartDate(), t.getEndDate()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpAdUnitTable(String fileName) {
        List<AdUnit> list = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<AdUnitTable> tables = list.stream()
                .map(u -> new AdUnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpCreativeTable(String fileName) {
        List<Creative> list = creativeRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<CreativeTable> tables = list.stream()
                .map(c -> new CreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(), c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpCreativeUnitTable(String fileName) {
        List<CreativeUnit> list = creativeUnitRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<CreativeUnitTable> tables = list.stream()
                .map(c -> new CreativeUnitTable(c.getCreativeId(), c.getUnitId()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> list = unitKeywordRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<AdUnitKeywordTable> tables = list.stream()
                .map(u -> new AdUnitKeywordTable(u.getUnitId(), u.getKeyword()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpUnitItTable(String fileName) {
        List<AdUnitIt> list = unitItRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<AdUnitItTable> tables = list.stream()
                .map(u -> new AdUnitItTable(u.getUnitId(), u.getItTag()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }

    private void dumpUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> list = unitDistrictRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        List<AdUnitDistrictTable> tables = list.stream()
                .map(u -> new AdUnitDistrictTable(u.getUnitId(), u.getProvince(), u.getCity()))
                .collect(Collectors.toList());

        write(fileName, tables);
    }
}
