package com.sauna.booking.domain;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name ="reservation_slots")
public class ReservationSlot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	private int type;
	private int status;
	private double price;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "sauna_id")
	private Sauna sauna;
	
	public ReservationSlot () {}
	
	public ReservationSlot(Date datetime, int type, Sauna sauna) {
		super();
		this.datetime = datetime;
		this.type = type;
		this.status = 0;
		this.price = 9.95;
		this.sauna = sauna;
	}
	
	
	public ReservationSlot(Date datetime, int type, int status, Sauna sauna) {
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
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
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
