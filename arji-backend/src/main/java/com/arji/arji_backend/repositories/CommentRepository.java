package com.arji.arji_backend.repositories;

import com.arji.arji_backend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
