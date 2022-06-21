package com.application.backend.services;

import com.application.backend.entities.Tag;
import com.application.backend.repository.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    public TagService() {}
    @Inject
    private TagRepository tagRepository;
    public List<Tag> allTags() {
        return this.tagRepository.allTags();
    }
    public Tag addTag(Tag tag) {
        return this.tagRepository.addTag(tag);
    }
    public Tag findTag(String id) {
        return this.tagRepository.findTag(id);
    }
    public Tag findTagById(Integer id) {
        return this.tagRepository.findTagById(id);
    }
    public void deleteTag(Integer id) {this.tagRepository.deleteTag(id);}
}
