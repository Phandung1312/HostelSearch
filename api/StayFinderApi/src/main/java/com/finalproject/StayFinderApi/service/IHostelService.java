package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.entity.Hostel;

public interface IHostelService {
	
	HostelResp saveHostel(HostelRequest hostel);
	
	HostelResp updateHostel(HostelRequest hostel);
	
	void deleteHostel(Long id);
	
	PagedResponse<Hostel> getAllHostel(int page, int size);
	
	PagedResponse<Hostel> getAll(int page, int size);
	
	PagedResponse<Hostel> findByManyOption(int page, int size,String address, double areaMin, double areMax, double minRent, double maxRent, int capacity, long idRoomType);
	
	public HostelResp getHostelRespById(Long id);
	
	Hostel getHostelByPostId(long id);
	
	HostelResp updateStatusHostel(long id, int status);
	
	PagedResponse<Hostel> getHostelByStatus(int page, int size, int status);
	
}
