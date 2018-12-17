package com.byka.service.impl;

import com.byka.data.ResponseData;
import com.byka.exception.IncorrectAdditionalInfoSizeException;
import com.byka.service.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TutBySearcher extends AbstractSearcher implements Searcher {
    private String url;

    private String sortBy;

    public List<ResponseData> doSearch(final String searchTerm) throws Exception {
        final String query = buildUrl(searchTerm);
        final String response = getSearchContent(query);
        return parseResponse(response);
    }

    private String buildUrl(final String searchTerm) {
        StringBuilder builder = new StringBuilder(url);
        builder.append(searchTerm);

        builder.append("&num=" + getMaxCount());

        if (sortBy != null) {
            builder.append("&sort=" + sortBy);
        }

        return builder.toString();
    }

    private List<ResponseData> parseResponse(final String response) {
        List<ResponseData> result = new ArrayList<>();
        final String pattern1 = "<div class=\"results-header\"><h3><a href=\"";
        final String pattern2 = "</a></h3></div>";
        final String linkEndPatter = "\" target=\"_self\">";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(response);

        while (m.find()) {
            String s = m.group(0).trim();

            final String link = s.substring(s.indexOf(pattern1) + pattern1.length(), s.indexOf(linkEndPatter));
            final String name = s.substring(s.indexOf(linkEndPatter) + linkEndPatter.length(), s.indexOf(pattern2));

            result.add(new ResponseData(link, name));
        }

        try {
            invokeDate(response, result);
        } catch (IncorrectAdditionalInfoSizeException e) {
        }
        return result;
    }

    private void invokeDate(final String response, final List<ResponseData> responseData) throws IncorrectAdditionalInfoSizeException {
        final String pattern1 = "<ul class=\"results__helpful\"><li class=\"results__helpful__li\"><a href=\"";
        final String pattern2 = ",</a></li>";
        final String linkEndPatter = "\" target=\"_self\">";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(response);

        List<String> additionalInfo = new ArrayList<>(responseData.size());

        while (m.find()) {
            if (additionalInfo.size() == responseData.size()) {
                throw new IncorrectAdditionalInfoSizeException();
            }
            String s = m.group(0).trim();
            String info = s.substring(s.indexOf(linkEndPatter) + linkEndPatter.length(), s.indexOf(pattern2));
            additionalInfo.add(info);
        }

        if (additionalInfo.size() == responseData.size()) {
            try {
                for (int i = 0; i < additionalInfo.size(); i++) {
                    responseData.get(i).setDate(convertToDate(additionalInfo.get(i)));
                }
            } catch (final ParseException e) {
                throw new IncorrectAdditionalInfoSizeException(e);
            }

        } else {
            throw new IncorrectAdditionalInfoSizeException();
        }
    }

    @Required
    public void setUrl(final String url) {
        this.url = url;
    }

    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    protected String getAgent() {
        return null;
    }
}
