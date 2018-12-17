package com.byka.data;

import java.util.Date;

public class ResponseData {
    private String link;

    private String name;

    private Date date;

    public ResponseData(final String link, final String name) {
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return date == null ? "" : date + "   " + name + ": " + buildLink();
    }

    public String buildLink() {
        return "<a href=\"" + link + "\">" + link + "</a>";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
