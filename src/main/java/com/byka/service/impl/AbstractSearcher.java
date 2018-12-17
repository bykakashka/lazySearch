package com.byka.service.impl;

import com.byka.service.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractSearcher implements Searcher {
    private DateFormat formatter;

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSearcher.class);

    private static final Integer DEFAULT_MAX_SIZE = 10;

    private Integer maxCount;

    protected String readStream(final InputStream is) {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (final IOException e) {
            LOG.warn(e.getMessage(), e);
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                LOG.warn(e.getMessage(), e);
            }
        }
        return sb.toString();
    }

    protected String getSearchContent(final String path) throws IOException {
        final String agent = "Mozilla/5.0 (compatible;)";

        final URLConnection connection = getConnection(path);
        final InputStream stream = connection.getInputStream();
        final String stringResponse = readStream(stream);
        stream.close();
        return stringResponse;
    }

    protected URLConnection getConnection(final String path) throws IOException {
        final URL url = new URL(path);
        final URLConnection connection = url.openConnection();

        final String agent = getAgent();
        if (agent != null) {
            connection.setRequestProperty("User-Agent", agent);
        }

        return connection;
    }

    public void setMaxCount(final Integer count) {
        this.maxCount = count;
    }

    public Integer getMaxCount() {
        return maxCount == null || maxCount > 50 || maxCount < 0 ? DEFAULT_MAX_SIZE : maxCount;
    }

    protected Date convertToDate(final String stringDate) throws ParseException {
        return formatter.parse(stringDate);
    }

    @Required
    public void setFormat(final SimpleDateFormat format) {
        this.formatter = format;
    }

    protected abstract String getAgent();
}
