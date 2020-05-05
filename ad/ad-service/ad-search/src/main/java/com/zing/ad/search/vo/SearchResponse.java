package com.zing.ad.search.vo;

import com.zing.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long adId;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        // 展示监测 url
        private List<String> showMonitorUrl = Arrays.asList("www.google.com", "www.google.com");
        // 点击监测 url
        private List<String> clickMonitorUrl = Arrays.asList("www.google.com", "www.google.com");
    }

    public static Creative convert(CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setUrl(object.getUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
