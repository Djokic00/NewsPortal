package com.application.backend.repository.news;

import com.application.backend.entities.Comment;
import com.application.backend.entities.News;
import com.application.backend.entities.Tag;

import java.util.List;

public interface NewsRepository {
    List<News> allNews();
    List<News> allNewsByVisits();
    News addNews(News news);
    News updateNews(News news);
    News findNews(Integer id);
    void deleteNews(Integer id);
    List<News> allByCategory(String name);
    List<News> allByTag(Integer id);
    List<Tag> allTagByNews(Integer id);
    List<Comment> allCommentsByNews(Integer id);
}
