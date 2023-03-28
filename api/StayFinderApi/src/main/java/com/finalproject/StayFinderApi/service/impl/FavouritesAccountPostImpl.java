package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.service.IFavouritesAccountPost;

@Service
public class FavouritesAccountPostImpl implements IFavouritesAccountPost{

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Override
	public boolean addFavourites(String username, long postId) {
		Optional<Account> accountOptional = accountRepo.findByUsername(username);
		Optional<Post> postOptional = postRepo.findById(postId);
		if(accountOptional.isPresent() && postOptional.isPresent())
		{
			if(!checkAccountFavouriedPost(username, postId)) {
				Account account = accountOptional.get();
				Post post = postOptional.get();

				
				List<Post> postsFavourites = account.getListFavouritePosts();
				postsFavourites.add(post);
				account.setListFavouritePosts(postsFavourites);
				
				accountRepo.save(account);
				
				List<Account> accountsLike = post.getListAccountLiked();
				accountsLike.add(account);
				post.setListAccountLiked(accountsLike);
				
				post.setNumberOfFavourites(post.getListAccountLiked().size());
				
				postRepo.save(post);
				
				return true;
			}
			throw new RuntimeException("username: " + username +" alreadgy favourites Post id: " + postId);
		}
		throw new RuntimeException("username: " + username +" can't favourites Post id: " + postId);
	}

	@Override
	public boolean checkAccountFavouriedPost(String username, long postId) {
		Optional<Post> postOptional = postRepo.findById(postId);
		if( postOptional.isPresent())
		{
		
			Post post = postOptional.get();
			for(Account acc : post.getListAccountLiked()) {
				if(acc.getUsername().toLowerCase().equals(username.toLowerCase())){
					return true;
				}
			}
			return false;
		}
		throw new RuntimeException(" can't find Post by Post id: " + postId);
	}
	

	@Override
	public boolean unFavourites(String username, long postId) {
		if(checkAccountFavouriedPost(username, postId))
		{
			Optional<Account> accountOptional = accountRepo.findByUsername(username);
			Optional<Post> postOptional = postRepo.findById(postId);
			if(accountOptional.isPresent() && postOptional.isPresent())
			{
				Account account = accountOptional.get();
				Post post = postOptional.get();
				
				List<Post> postsFavourites = account.getListFavouritePosts();
				postsFavourites.remove(post);
				account.setListFavouritePosts(postsFavourites);
				
				accountRepo.save(account);
				
				List<Account> accountsLike = post.getListAccountLiked();
				accountsLike.remove(account);
				post.setListAccountLiked(accountsLike);
				
				post.setNumberOfFavourites(post.getListAccountLiked().size());
				
				postRepo.save(post);
				return true;
			}
		}
	
		throw new RuntimeException("username: " + username +" can't Unfavourites Post id: " + postId);
	}

	@Override
	public List<AccountRespone> accountsFavouriesPost(long postId) {
		Optional<Post> postOptional = postRepo.findById(postId);
		if( postOptional.isPresent())
		{
	
			Post post = postOptional.get();
			return post.getListAccountLiked().stream().map(acc -> {
				return new AccountRespone(acc.getUsername(), acc.getName(), acc.getAvatar());
				
			}).collect(Collectors.toList());
		}
		throw new RuntimeException(" can't find Account favourites by Post id: " + postId);
	}

	@Override
	public List<Post> getListPostFavoritesByUsername(String username) {
		Optional<Account> accountOptional = accountRepo.findByUsername(username);
		if( accountOptional.isPresent())
		{
	

//			return accountOptional.get().getListFavouritePosts().stream().map(post -> {
//				return new PostResp(post.getId(), post.getAccount().getUsername(), post.getAccount().getId(), post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(), post.getPostTime(), null);
//			}).collect(Collectors.toList());
			return accountOptional.get().getListFavouritePosts();
		}
		throw new RuntimeException(" can't find Post by account username: " + username);
	}

}
