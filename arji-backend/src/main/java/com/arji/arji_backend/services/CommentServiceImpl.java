package com.arji.arji_backend.services;

import com.arji.arji_backend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
