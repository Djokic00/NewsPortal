package com.application.backend.resources;

import com.application.backend.entities.Tag;
import com.application.backend.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> all()
    {
        return this.tagService.allTags();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tag create(@Valid Tag tag) {
        return this.tagService.addTag(tag);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag find(@PathParam("name") String id) {
        return this.tagService.findTag(id);
    }

    @GET
    @Path("by/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findTagById(@PathParam("id") Integer id) {
        return this.tagService.findTagById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        this.tagService.deleteTag(id);
    }

}
