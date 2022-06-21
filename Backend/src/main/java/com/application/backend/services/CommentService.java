package com.application.backend.services;

import com.application.backend.entities.Comment;
import com.application.backend.repository.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    public CommentService() {
        System.out.println(this);
    }

    @Inject
    private CommentRepository tagRepository;

    public List<Comment> allComments() {
        return this.tagRepository.allComments();
    }

    public Comment addComment(Comment comment) {
        return this.tagRepository.addComment(comment);
    }

    public Comment findComment(Integer id) {
        return this.tagRepository.findComment(id);
    }

    public void deleteComment(Integer id) {
        this.tagRepository.deleteComment(id);
    }

}
