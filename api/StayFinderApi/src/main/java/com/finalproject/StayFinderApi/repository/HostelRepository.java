package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {

}
