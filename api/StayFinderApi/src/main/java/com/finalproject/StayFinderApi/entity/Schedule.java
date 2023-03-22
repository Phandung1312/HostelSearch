package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "Schedule")
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="RenterId")
	private long renterId;
	
	@Column(name="RenterName",nullable = false,columnDefinition = "text")
	private String renterName;
	
	@Column(name="RenterPhoneNumber",columnDefinition = "varchar(10)")
	private String renterPhoneNumber;
	
	@Column(columnDefinition = "longtext")
	private String content;
	
	@Column(name="MeetingTime",nullable = false,columnDefinition = "datetime")
	private Date meetingTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PostId", nullable=false)
	private Post post;
		
	
}
