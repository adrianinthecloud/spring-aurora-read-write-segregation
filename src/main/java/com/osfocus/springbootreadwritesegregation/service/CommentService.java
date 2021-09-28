package com.osfocus.springbootreadwritesegregation.service;

import com.osfocus.springbootreadwritesegregation.entity.Comment;
import com.osfocus.springbootreadwritesegregation.entity.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<Comment> findTop25();
    void addComment(CommentDTO commentDTO);
    void updateLastComment(CommentDTO commentDTO);
}
