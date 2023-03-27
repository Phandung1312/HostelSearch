package com.finalproject.StayFinderApi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "RoomType")
public class RoomType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="RoomTypeName",nullable = false,columnDefinition = "text")
	private String roomTypeName;
	
	@JsonIgnore
	@OneToMany(mappedBy="roomtype",  cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Hostel> hostels;
	
	
	public List<Hostel> getHostels(){
		return this.hostels == null ? null : new ArrayList<Hostel>(this.hostels);
	}
	
	public void setHostels(List<Hostel> hostels) {
		if (hostels == null) {
			this.hostels = null;
		} else {
			this.hostels = Collections.unmodifiableList(hostels);
		}
	}
	
}
