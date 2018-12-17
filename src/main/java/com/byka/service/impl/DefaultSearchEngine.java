package com.byka.service.impl;

import com.byka.data.ResponseData;
import com.byka.service.SearchEngine;
import com.byka.service.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultSearchEngine implements SearchEngine {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSearchEngine.class);

    @Autowired
    private List<Searcher> searchers;

    @Override
    public List<ResponseData> doSearch(final String text) {
        final List<ResponseData> result = new ArrayList<>();
        getSearchers().forEach(s -> {
            try {
                result.addAll(s.doSearch(text));
            } catch (final Exception e) {
                LOG.warn(s.getClass().getSimpleName() + " throw an error", e);
            }
        });

        result.sort(Comparator.comparing(ResponseData::getDate).reversed());
        return result;
    }

    protected List<Searcher> getSearchers() {
        return this.searchers;
    }

    public void setSearchers(final List<Searcher> searchers) {
        this.searchers = searchers;
    }
}
