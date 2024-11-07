package com.arji.arji_backend.services;

import com.arji.arji_backend.models.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllComments();
}
