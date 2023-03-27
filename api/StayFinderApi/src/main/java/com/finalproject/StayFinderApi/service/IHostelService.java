package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.entity.Hostel;

public interface IHostelService {
	
	public HostelResp saveHostel(HostelRequest hostel);
	
	public HostelResp updateHostel(HostelRequest hostel);
	
	public void deleteHostel(Long id);
	
	public List<HostelResp> getAllHostel();
	
	public List<Hostel> getAll();
	
	public List<Hostel> findByRentPriceBetween(double minRent, double maxRent);
	
	List<Hostel> findByManyOption(String address, double areaMin, double areMax, double minRent, double maxRent, int capacity, long idRoomType);
	
	public HostelResp getHostelRespById(Long id);
	
	Hostel getHostelByPostId(long id);
	
	HostelResp updateStatusHostel(long id, String status);
	
}
