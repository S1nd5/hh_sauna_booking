package com.sauna.booking.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="saunas")
public class Sauna {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	private String name;
	private String staircase;
	private String size;
	private int capacity;
	private int clubspace;
	
	public Sauna() {}
	
	public Sauna(String name, String staircase, String size, int capacity, int clubspace) {
		super();
		this.name = name;
		this.staircase = staircase;
		this.size = size;
		this.capacity = capacity;
		this.clubspace = clubspace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaircase() {
		return staircase;
	}

	public void setStaircase(String staircase) {
		this.staircase = staircase;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getClubspace() {
		return clubspace;
	}

	public void setClubspace(int clubspace) {
		this.clubspace = clubspace;
	}
	
}
