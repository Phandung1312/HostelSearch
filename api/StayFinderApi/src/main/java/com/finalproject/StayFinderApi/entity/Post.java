package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "Post")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AccountId", nullable=false)
	private Account account;
	
	@Column(nullable = false,columnDefinition = "mediumtext")
	private String title;
	
	@Column(columnDefinition = "longtext")
	private String content;
	
	@Column(name="NumberOfFavourites",nullable = false)
	private long numberOfFavourites;
	
	@Column(name="status",nullable = false,columnDefinition = "text")
	private String status;
	
	@Column(name="PostTime",nullable = false,columnDefinition = "datetime")
	private Date postTime;
	
	@OneToOne
    @JoinColumn(name = "HostelId", referencedColumnName = "id")
	private Hostel hostel;
	
	@OneToMany(mappedBy="post")
	private List<Comment> comments;
	
	@OneToMany(mappedBy="post")
	private List<Schedule> schedules;
	
	@ManyToMany(mappedBy = "listFavouritePosts")
	List<Account> listAccountLiked;

	
}
