package com.arji.arji_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @PostMapping("/auth/comment")
    public ResponseEntity<String> addComment() {
        return null;
    }

    @PostMapping("/auth/{ticketId}/comment")
    public ResponseEntity<String> addCommentToTicket(@PathVariable("ticketId") Long ticketId) {
        return null;
    }

    @GetMapping("/auth/comment")
    public ResponseEntity<String> getAllComments() {
        return null;
    }

    @GetMapping("/auth/comment/{commentId}")
    public ResponseEntity<String> getComment() {
        return null;
    }

    @PutMapping("/auth/comment/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable("commentId") Long commentId) {
        return null;
    }

}
