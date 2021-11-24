package com.sauna.booking.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "reservationslot_id")
	private ReservationSlot slot;
	
	@ManyToOne
	@JoinColumn(name = "subscriber_id")
	private User subscriber;
	
	public Reservation() {}
	
	public Reservation(ReservationSlot slot, User subscriber) {
		super();
		this.slot = slot;
		this.subscriber = subscriber;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(User subscriber) {
		this.subscriber = subscriber;
	}
	
	public ReservationSlot getSlot() {
		return slot;
	}
	public void setSlot(ReservationSlot slot) {
		this.slot = slot;
	}
}
