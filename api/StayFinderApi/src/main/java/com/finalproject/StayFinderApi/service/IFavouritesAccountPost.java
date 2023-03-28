package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.entity.Post;

public interface IFavouritesAccountPost {
	public boolean addFavourites (String username, long postId);
	
	public boolean checkAccountFavouriedPost(String username, long postId);
	
	public boolean unFavourites(String username, long postId);
	
	public List<AccountRespone> accountsFavouriesPost(long postId);
	
	public List<Post> getListPostFavoritesByUsername(String username);
	
}
