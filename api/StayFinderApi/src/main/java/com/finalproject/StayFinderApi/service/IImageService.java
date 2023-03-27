package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.entity.Image;

public interface IImageService  {
	
	List<Image> addListImage(List<Image> images);
	
	List<Image> getImagesByHostelId(Long id);
	
	Boolean deleteImageById(Long id);
	
	Image addImage(Image image);
	
}
