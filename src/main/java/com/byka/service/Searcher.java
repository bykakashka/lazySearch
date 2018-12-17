package com.byka.service;

import com.byka.data.ResponseData;

import java.util.List;

public interface Searcher {
    List<ResponseData> doSearch(final String searchTerm) throws Exception;
}
