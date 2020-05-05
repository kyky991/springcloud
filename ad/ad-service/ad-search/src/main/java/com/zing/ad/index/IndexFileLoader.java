package com.zing.ad.index;

import com.alibaba.fastjson.JSON;
import com.zing.ad.dump.DConstant;
import com.zing.ad.dump.table.*;
import com.zing.ad.handler.AdLevelDataHandler;
import com.zing.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        List<String> planList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        planList.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        List<String> creativeList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        creativeList.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, CreativeTable.class), OpType.ADD));

        List<String> unitList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        unitList.forEach(p -> AdLevelDataHandler.handleLevel3(JSON.parseObject(p, AdUnitTable.class), OpType.ADD));

        List<String> creativeUnitList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        creativeUnitList.forEach(p -> AdLevelDataHandler.handleLevel3(JSON.parseObject(p, CreativeUnitTable.class), OpType.ADD));

        List<String> unitKeywordList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        unitKeywordList.forEach(p -> AdLevelDataHandler.handleLevel4(JSON.parseObject(p, AdUnitKeywordTable.class), OpType.ADD));

        List<String> unitItList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        unitItList.forEach(p -> AdLevelDataHandler.handleLevel4(JSON.parseObject(p, AdUnitItTable.class), OpType.ADD));

        List<String> unitDistrictList = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        unitDistrictList.forEach(p -> AdLevelDataHandler.handleLevel4(JSON.parseObject(p, AdUnitDistrictTable.class), OpType.ADD));
    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
