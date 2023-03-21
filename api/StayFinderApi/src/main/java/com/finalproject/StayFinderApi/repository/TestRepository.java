package com.finalproject.StayFinderApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer>{

}
