package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
