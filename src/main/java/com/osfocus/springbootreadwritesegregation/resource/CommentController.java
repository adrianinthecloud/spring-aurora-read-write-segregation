package com.osfocus.springbootreadwritesegregation.resource;

import com.osfocus.springbootreadwritesegregation.entity.dto.CommentDTO;
import com.osfocus.springbootreadwritesegregation.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity comments() {
        return ResponseEntity.ok(commentService.findTop25());
    }

    @PostMapping("/comments")
    public ResponseEntity addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return ResponseEntity.ok("Added comment");
    }

    @PutMapping("/comments/last")
    public ResponseEntity updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.updateLastComment(commentDTO);
        return ResponseEntity.ok("Updated comment");
    }
}
