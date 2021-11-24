package com.sauna.booking.domain;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name ="reservation_slots")
public class ReservationSlot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime datetime;
	
	private int type;
	private int status;
	private double price;
	
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations;
	
	@ManyToOne
	@JoinColumn(name = "sauna_id")
	private Sauna sauna;
	
	public ReservationSlot () {}
	
	public ReservationSlot(LocalDateTime datetime, int type, Sauna sauna) {
		super();
		this.datetime = datetime;
		this.type = type;
		this.status = 0;
		this.price = 9.95;
		this.sauna = sauna;
	}
	
	
	public ReservationSlot(LocalDateTime datetime, int type, int status, Sauna sauna) {
		super();
		this.datetime = datetime;
		this.type = type;
		this.status = status;
		this.price = 9.95;
		this.sauna = sauna;
	}

	public Sauna getSauna() {
		return sauna;
	}

	public void setSauna(Sauna sauna) {
		this.sauna = sauna;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
