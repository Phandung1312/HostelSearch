package com.finalproject.StayFinderApi.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

	private long postId;
	
	private long accountId;
	
	private String content;

	private String image;
}
