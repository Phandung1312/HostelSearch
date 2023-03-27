package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;
import com.finalproject.StayFinderApi.service.ICommentService;

@RestController
@RequestMapping("api/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;
	
	@GetMapping("/post/{postId}")
	public List<CommentResponse> getCommentByPostId(@PathVariable long postId){
		return commentService.getCommentByPostId(postId);
	}
	
	@DeleteMapping("/{id}")
	public boolean deletePostbyId(@PathVariable long id){
		return commentService.deleteCommentById(id);
	}
	
	@PostMapping
	public CommentResponse addComment(@RequestBody CommentRequest commentRequest){
		return commentService.addComment(commentRequest);
	}
	
}
