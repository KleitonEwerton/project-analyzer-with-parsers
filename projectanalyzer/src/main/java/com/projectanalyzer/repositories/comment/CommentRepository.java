package com.projectanalyzer.repositories.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
