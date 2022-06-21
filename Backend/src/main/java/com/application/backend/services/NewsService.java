package com.application.backend.services;

import com.application.backend.entities.Comment;
import com.application.backend.entities.News;
import com.application.backend.entities.Tag;
import com.application.backend.repository.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    public NewsService() {}

    @Inject
    private NewsRepository newsRepository;

    public News addNews(News news) {
        return this.newsRepository.addNews(news);
    }

    public News updateNews(News news) {
        return this.newsRepository.updateNews(news);
    }

    public List<News> allNews() {
        return this.newsRepository.allNews();
    }

    public List<News> allNewsByVisits() {
        return this.newsRepository.allNewsByVisits();
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

    public void deleteNews(Integer id) {
        this.newsRepository.deleteNews(id);
    }

    public List<News> allByCategory(String name) {
        return this.newsRepository.allByCategory(name);
    }

    public List<News> allByTag(Integer id) {
        return this.newsRepository.allByTag(id);
    }

    public List<Tag> allTagByNews(Integer id) {
        return this.newsRepository.allTagByNews(id);
    }

    public List<Comment> allCommentsByNews(Integer id) {
        return this.newsRepository.allCommentsByNews(id);
    }
}
