package com.finalproject.StayFinderApi.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "Hostel")
public class Hostel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,columnDefinition = "text")
	private String name;
	
	@Column(columnDefinition = "text")
	private String capacity;
	
	@Column(columnDefinition = "text")
	private String area;
	
	@Column(nullable = false,columnDefinition = "text")
	private String address;
	
	@Column(name="RentPrice",nullable = false)
	private double rentPrice;
	
	@Column(name="DepositPrice")
	private double depositPrice;
	
	@Column(nullable = false,columnDefinition = "text")
	private String status;
	
	@Column(columnDefinition = "longtext")
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RoomTypeId", nullable=false)
	private RoomType roomtype;
	
	@Column(name="ElectricPrice")
	private double electricPrice;
	
	@Column(name="WaterPrice")
	private double waterPrice;
	
	@OneToOne(mappedBy = "hostel")
	private Post post;
	
	@OneToMany(mappedBy="hostel")
	private List<Image> images;

	
	
}
