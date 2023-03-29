package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.utils.AppConstants;

@RestController
@RequestMapping("api/hostel")
public class HostelController {

	@Autowired
	private IHostelService hostelService;

	@GetMapping
	public PagedResponse<Hostel> getAll(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) 
	{
		return hostelService.getAllHostel(page, size);
	}

	@GetMapping("/{id}")
	public Hostel getOne(@PathVariable Long id) {
		Hostel hostel = hostelService.getHostelByPostId(id);
		if (hostel != null)
			return hostel;
		else {
			throw new RuntimeException("Hostel not found for the id " + id);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		hostelService.deleteHostel(id);
	}

	@PutMapping
	public HostelResp update(@RequestBody HostelRequest hostel) {
		return hostelService.updateHostel(hostel);
	}

	@PostMapping
	public HostelResp save(@RequestBody HostelRequest hostelReq) {
		return hostelService.saveHostel(hostelReq);
	}


	@GetMapping("/search")
	public List<Hostel> findByManyOption(@RequestParam(required = false, defaultValue = "") String address,
			@RequestParam(required = false, defaultValue = "0.0") double areaMin,
			@RequestParam(required = false, defaultValue = "100.0") double areMax,
			@RequestParam(required = false, defaultValue = "0.0") double minRent,
			@RequestParam(required = false, defaultValue = "10000000") double maxRent,
			@RequestParam(required = false, defaultValue = "10") int capacity,
			@RequestParam(required = false, defaultValue = "0") long idRoomType) {

//		return hostelService.findByManyOption(address, areaMin, areMax, minRent, maxRent, capacity, idRoomType);
		return null;
	}
	
	@PostMapping("status/{id}")
	public HostelResp updateStatusHostel(@PathVariable long id, @RequestParam(required = false, defaultValue = "0") int status) {
		return hostelService.updateStatusHostel(id, status);
	}
	
	
}
