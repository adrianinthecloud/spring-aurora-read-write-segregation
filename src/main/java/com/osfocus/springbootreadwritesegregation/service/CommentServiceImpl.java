package com.osfocus.springbootreadwritesegregation.service;

import com.osfocus.springbootreadwritesegregation.annotation.DataSource;
import com.osfocus.springbootreadwritesegregation.entity.Comment;
import com.osfocus.springbootreadwritesegregation.entity.dto.CommentDTO;
import com.osfocus.springbootreadwritesegregation.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@DataSource
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findTop25() {
        return commentRepository.findTop25ByOrderByIdDesc();
    }

    public void addComment(CommentDTO commentDTO) {
        commentRepository.save(Comment.builder()
                                      .content(commentDTO.getContent())
                                      .build());
    }

    public void updateLastComment(CommentDTO commentDTO) {
        Optional<Comment> lastCommentOpt = commentRepository.findTopByOrderByIdDesc();
        if (lastCommentOpt.isPresent()) {
            commentRepository.updateLastComment(lastCommentOpt.get().getId(), commentDTO.getContent());
        }
    }
}
