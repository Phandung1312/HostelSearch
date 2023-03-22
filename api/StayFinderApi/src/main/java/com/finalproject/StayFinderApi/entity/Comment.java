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

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "Comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PostId", nullable=false)
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AccountId", nullable=false)
	private Account account;
	
	@Column(columnDefinition = "longtext")
	private String content;
	
	@Column(name="CommentTime",nullable = false,columnDefinition = "datetime")
	private Date commentTime;
	
	@Column(columnDefinition = "varchar(50)")
	private String image;

	
}
