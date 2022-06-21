package com.application.backend.resources;

import com.application.backend.entities.Comment;
import com.application.backend.entities.News;
import com.application.backend.entities.Tag;
import com.application.backend.services.NewsService;
import com.application.backend.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {
    @Inject
    private NewsService newsService;
    @Inject
    private TagService tagServiceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all()
    {
        return this.newsService.allNews();
    }

    @GET
    @Path("/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByVisits()
    {
        return this.newsService.allNewsByVisits();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news) {
        return this.newsService.addNews(news);
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public News updateNews(@Valid News news) {
        return this.newsService.updateNews(news);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.findNews(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        this.newsService.deleteNews(id);
    }

    @GET
    @Path("/category/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allByCategory(@PathParam("name") String name) {
        return this.newsService.allByCategory(name);
    }

    @GET
    @Path("/tag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allByTag(@PathParam("id") Integer id) {
        return this.newsService.allByTag(id);
    }

    @GET
    @Path("/newsTag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTagByNews(@PathParam("id") Integer id) {
        return this.newsService.allTagByNews(id);
    }

    @GET
    @Path("/comments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allCommentsByNews(@PathParam("id") Integer id) {
        return this.newsService.allCommentsByNews(id);
    }
}