package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.dto.PostResp;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.service.IPostService;

@Repository
public class PostServiceImpl implements IPostService{
	
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private HostelService hostelService;

	@Override
	public List<PostResp> getAll() {
		// TODO Auto-generated method stub
		return postRepo.findAll().stream().map(p->{
			PostResp postResp = new PostResp(p.getId(), p.getAccount().getName(), 
					p.getAccount().getId(), p.getTitle(), 
					p.getContent(), p.getNumberOfFavourites(), p.getStatus(), 
					p.getPostTime(), hostelService.getHostelRespById(p.getId()) );
			return postResp;
		}).collect(Collectors.toList());
	}

	@Override
	public Post getById(long id) {
		Optional<Post> optional = postRepo.findById(id);
		if(optional.isPresent())
			return optional.get();
		else {
			throw new RuntimeException("Find Post by id Fail!");
		}
	}

	@Override
	public boolean deletePost(long id) {
		postRepo.deleteById(id);
		return true;
	}

	@Override
	public Post addPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
