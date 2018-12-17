package com.byka.service;

import com.byka.data.ResponseData;

import java.util.List;

public interface SearchEngine {
    List<ResponseData> doSearch(final String text) throws Exception;
}
