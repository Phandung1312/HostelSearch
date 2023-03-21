package com.finalproject.StayFinderApi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.entity.Test;
import com.finalproject.StayFinderApi.service.impl.TestService;

@RestController
public class TestMyAPI {

	@Autowired
	private TestService testService;
	
	@GetMapping("test")
	public List<Test> getAll(){
		return testService.getAll();
	}
}
