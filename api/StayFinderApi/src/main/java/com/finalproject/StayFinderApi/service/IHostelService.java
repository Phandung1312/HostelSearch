package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.entity.Hostel;

public interface IHostelService {
	
	public Hostel saveHostel(Hostel hostel);
	public Hostel updateHostel(Hostel hostel);
	public void deleteHostel(Long id);
	public List<Hostel> getAllHostel();
	public Hostel getOneHostel(Long id);
	
}
