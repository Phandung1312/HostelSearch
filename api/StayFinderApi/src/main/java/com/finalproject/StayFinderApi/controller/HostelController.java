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
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.service.IHostelService;


@RestController
@RequestMapping("api/hostel")
public class HostelController {
	
	@Autowired
	private IHostelService hostelService;
	
	@GetMapping
	public List<Hostel> getAll() {
		return hostelService.getAllHostel();
	}

	@GetMapping("/{id}")
	public Hostel getOne(@PathVariable Long id) {
		
		Hostel hostel = hostelService.getOneHostel(id);
		if (hostel != null)
			return hostel;
		else {
			throw new RuntimeException("Hostel not found for the id "+ id);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		hostelService.deleteHostel(id);
	}
	
	@PutMapping
	public Hostel update(@RequestBody Hostel hostel) {
		return hostelService.updateHostel(hostel);
	}
	
	@PostMapping
	public Hostel save(@RequestBody Hostel hostel) {
		return hostelService.saveHostel(hostel);
	}
	
}
