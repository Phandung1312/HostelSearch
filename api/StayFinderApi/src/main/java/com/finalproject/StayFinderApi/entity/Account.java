package com.finalproject.StayFinderApi.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "Account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "text")
	private String name;
	
	@Column(nullable = false,columnDefinition = "varchar(50)")
	private String username;
	
	@Column(nullable = false,columnDefinition = "varchar(50)")
	private String password;
	
	@Column
	private boolean gender;
	
	@Column(nullable = false,columnDefinition = "text")
	private String status;
	
	@Column(columnDefinition = "varchar(50)")
	private String avatar;
	
	@Column(columnDefinition = "char(10)")
	private String phonenumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PositionId", nullable=false)
	private Position position;
	
	@OneToMany(mappedBy="account")
	private List<Post> posts;
	
	@OneToMany(mappedBy="account")
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(
	  name = "favourites_account_post", 
	  joinColumns = @JoinColumn(name = "AccountId"), 
	  inverseJoinColumns = @JoinColumn(name = "PostId"))
	List<Post> listFavouritePosts;

	
	
}
