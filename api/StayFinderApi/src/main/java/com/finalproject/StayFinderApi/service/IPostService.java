package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.PostResp;
import com.finalproject.StayFinderApi.entity.Post;

public interface IPostService {
	
	List<PostResp> getAll();
	
	Post getById(long id);
	
	Post addPost(Post post);
	
	List<Post> findByAccountUsernameAndStatus(String username, String status);
	
	boolean deletePost(long id);
	
	
}
