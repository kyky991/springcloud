package com.zing.ad.search;

import com.zing.ad.search.vo.SearchRequest;
import com.zing.ad.search.vo.SearchResponse;

/**
 * @author Zing
 * @date 2019-11-26
 */
public interface ISearch {

    SearchResponse fetch(SearchRequest request);

}
