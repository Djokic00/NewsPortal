package com.application.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {
    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;
    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;
    @JsonProperty("creation_date")
    private Date creationDate;
    private User author;
    private Integer visits;
    private Category category;

    //private List<Tag> listOfTags = new ArrayList<>();
    private List<String> tag;

    public News() {}

    public News(Integer id, String title, String content, Date creationDate) {
        this(id, title, content, creationDate, 0);
    }

    public News(Integer id, String title, String content, Date creationDate, Integer visits) {
        //this(id, title, content, creationDate, visits);
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.visits = visits;
    }

//    public News(Integer id, String title, String content, Date creationDate, Integer visits) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.creationDate = creationDate;
//        this.visits = visits;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public List<Tag> getListOfTags() {
//        return listOfTags;
//    }
//
//    public void setListOfTags(List<Tag> listOfTags) {
//        this.listOfTags = listOfTags;
//    }

//    public Tag getTag() {
//        return tag;
//    }
//
//    public void setTag(Tag tag) {
//        this.tag = tag;
//    }


    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
