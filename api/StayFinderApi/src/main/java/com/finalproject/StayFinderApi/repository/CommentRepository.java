package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
