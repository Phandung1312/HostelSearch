package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResp implements Serializable {
private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String accountName;
	 
	private	long accountId; 
	
	private String title;
	
	private String content;
	
	private long numberOfFavourites;
	
	private String status;
	
	private Date postTime;
	
	@JsonIgnore
	private HostelResp hostel;
	
}
