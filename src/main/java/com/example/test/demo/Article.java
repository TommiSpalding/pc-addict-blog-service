package com.example.test.demo;

import java.time.LocalDate;
import java.time.ZoneId;

public class Article {

    private long id;

    private String headline;
    private String content;
    private String publisher;

    private LocalDate date;

    private static long num;

    public Article() {

        this.id = num++;
        this.date = LocalDate.now(ZoneId.of("GMT+2"));
    }

    public Article(String headline, String content, String publisher) {

        this.id = num++;
        this.date = LocalDate.now(ZoneId.of("GMT+2"));

        this.headline = headline;
        this.content = content;
        this.publisher = publisher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
