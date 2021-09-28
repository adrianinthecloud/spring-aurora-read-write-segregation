package com.osfocus.springbootreadwritesegregation.repository;

import com.osfocus.springbootreadwritesegregation.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findTopByOrderByIdDesc();

    List<Comment> findTop25ByOrderByIdDesc();

    @Modifying
    @Transactional
    @Query(value = "update Comment set content = :content where id = :id")
    int updateLastComment(Long id, String content);
}
