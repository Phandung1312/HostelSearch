package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.service.IHostelService;

@Service
public class HostelService implements IHostelService{
	
	@Autowired
	private HostelRepository hostelRepo;

	@Override
	public Hostel saveHostel(Hostel hostel) {
		return hostelRepo.save(hostel);
	}

	@Override
	public Hostel updateHostel(Hostel hostel) {
		return hostelRepo.save(hostel);
	}

	@Override
	public void deleteHostel(Long id) {
		hostelRepo.deleteById(id);
	}

	@Override
	public List<Hostel> getAllHostel() {
		
		return hostelRepo.findAll();
	}

	@Override
	public Hostel getOneHostel(Long id) {
		Optional<Hostel> hostel = hostelRepo.findById(id);
		if(hostel.isPresent())
		{
			return hostel.get();
		}
		else
			return null;
	}
	
	
	
}
