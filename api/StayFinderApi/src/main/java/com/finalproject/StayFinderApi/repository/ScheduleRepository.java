package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.StayFinderApi.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

}
