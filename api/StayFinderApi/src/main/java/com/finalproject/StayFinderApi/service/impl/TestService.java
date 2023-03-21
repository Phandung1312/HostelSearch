package com.finalproject.StayFinderApi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.Test;
import com.finalproject.StayFinderApi.repository.TestRepository;
import com.finalproject.StayFinderApi.service.ITestService;

@Service
public class TestService implements ITestService {
	
	@Autowired
	private TestRepository testRepository;
	
	@Override
	public List<Test> getAll() {
		
		return testRepository.findAll();
	}

}
