package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseModel {

	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Long totalDistance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id")
	@JsonIgnoreProperties({ "bookings" })
	private Driver driver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passenger_id")
	@JsonIgnoreProperties({ "bookings" })
	private Passenger passenger;
}