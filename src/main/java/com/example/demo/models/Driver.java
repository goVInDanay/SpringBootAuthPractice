package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "bookings" })
public class Driver extends BaseModel {

	private String name;

	@Column(nullable = false, unique = true)
	private String licenseNumber;

	private String phoneNumber;

	@OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private Set<Booking> bookings;
}