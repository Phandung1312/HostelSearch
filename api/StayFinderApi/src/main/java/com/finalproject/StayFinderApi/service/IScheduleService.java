package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.entity.Schedule;

public interface IScheduleService {
	
	
	List<Schedule> getSchedulesByPostId(long postId);
	
	List<Schedule> getSchedulesByRenterUsername(String username);
	
	Schedule addSchedule(ScheduleRequest scheduleRequest);
}
