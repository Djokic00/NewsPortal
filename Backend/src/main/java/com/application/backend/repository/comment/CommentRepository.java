package com.application.backend.repository.comment;

import com.application.backend.entities.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> allComments();
    Comment addComment(Comment comment);
    Comment findComment(Integer id);
    void deleteComment(Integer id);
}
