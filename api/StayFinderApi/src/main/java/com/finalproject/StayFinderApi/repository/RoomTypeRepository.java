package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{

}
