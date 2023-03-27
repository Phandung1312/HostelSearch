package com.finalproject.StayFinderApi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.service.IImageService;


@Service
public class ImageServiceImpl implements IImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> getImagesByHostelId(Long id) {
		
		return imageRepository.findByHostelId(id);
	}

	@Override
	public Boolean deleteImageById(Long id) {
		imageRepository.deleteById(id);
		return true;
	}

	@Override
	public Image addImage(Image image) {
		
		return imageRepository.save(image);
	}

	@Override
	public List<Image> addListImage(List<Image> images) {
		
		return imageRepository.saveAll(images);
	}

}
