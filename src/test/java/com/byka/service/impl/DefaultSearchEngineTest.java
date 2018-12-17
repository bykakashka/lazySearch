package com.byka.service.impl;

import com.byka.data.ResponseData;
import com.byka.service.Searcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.anyString;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultSearchEngineTest {
    @InjectMocks
    private DefaultSearchEngine classUnderTest;

    public void init() {
        Searcher mockedSearcher = Mockito.mock(Searcher.class);

        List<ResponseData> responseData = new ArrayList<>(5);

        mockDate(responseData, 10, 2, "april 10");

        mockDate(responseData, 9, 2, "feb 9");

        mockDate(responseData, 11, 2, "feb 11");

        mockDate(responseData, 10, 1, "jan 10");

        mockDate(responseData, 10, 3, "march 10");

        try {
            Mockito.when(mockedSearcher.doSearch(anyString())).thenReturn(responseData);
        } catch (Exception e) {

        }
        final List<Searcher> searcherList = new ArrayList<>(1);
        searcherList.add(mockedSearcher);
        classUnderTest = new DefaultSearchEngine() {
            @Override
            public List<Searcher> getSearchers() {
                return searcherList;
            }
        };
    }

    private void mockDate(List<ResponseData> responseData, int i, int i2, String s) {
        ResponseData data = Mockito.mock(ResponseData.class);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_YEAR, i);
        calendar.set(Calendar.MONTH, i2);

        Mockito.when(data.getDate()).thenReturn(calendar.getTime());
        Mockito.when(data.getName()).thenReturn(s);
        responseData.add(data);
    }

    @Test
    public void doSearch_order_test() {
        init();

        List<ResponseData> response = new ArrayList<>();
        try {
            response = classUnderTest.doSearch("123");
        } catch (Exception e) {
            Assert.fail("Unexpected exception");
        }

        Assert.assertEquals("Incorrect size of response", 5, response.size());

        for (int i=0; i<4; i++) {
            Assert.assertTrue("Incorrect order ", response.get(i).getDate().after(response.get(i+1).getDate()));
        }
    }
}
