package com.application.backend.repository.tag;

import com.application.backend.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> allTags();
    Tag addTag(Tag tag);
    Tag findTag(String id);
    Tag findTagById(Integer id);
    void deleteTag(Integer id);
}
