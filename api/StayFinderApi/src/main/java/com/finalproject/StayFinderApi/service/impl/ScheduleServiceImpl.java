package com.finalproject.StayFinderApi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.Schedule;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.repository.ScheduleRepository;
import com.finalproject.StayFinderApi.service.IScheduleService;

@Service
public class ScheduleServiceImpl implements IScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	public List<Schedule> getSchedulesByPostId(long postId) {
		List<Schedule> schedules =  scheduleRepo.findByPostId(postId);
		Collections.sort(schedules, new Comparator<Schedule>() {
			@Override
			public int compare(Schedule o1, Schedule o2) {
				return o2.getMeetingTime().compareTo(o1.getMeetingTime());
			}
		});
		return schedules;
	}

	@Override
	public List<Schedule> getSchedulesByRenterUsername(String username) {
		if (username != null) {
			List<Schedule> schedules =  scheduleRepo.findByRenterUsername(username);
			Collections.sort(schedules, new Comparator<Schedule>() {
				@Override
				public int compare(Schedule o1, Schedule o2) {
					return o2.getMeetingTime().compareTo(o1.getMeetingTime());
				}
			});
			return schedules;
		}
		return null;
	}

	@Override
	public Schedule addSchedule(ScheduleRequest scheduleRequest) {
		Optional<Post> optional = postRepo.findById(scheduleRequest.getPostId());
		if(optional.isPresent()) {
			Post post = optional.get();
			return scheduleRepo.save(new Schedule(0, scheduleRequest.getRenterUsername(), scheduleRequest.getRenterName(), scheduleRequest.getRenterPhoneNumber(), scheduleRequest.getContent(), scheduleRequest.getMeetingTime(), post));
		}
		throw new RuntimeException("Can't add Schedule, can't find Post by post id: " + scheduleRequest.getPostId());
	}

}
