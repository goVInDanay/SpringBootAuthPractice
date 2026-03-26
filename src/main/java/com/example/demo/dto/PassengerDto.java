package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.entityservice.models.Passenger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {
	private String id;
	private String email;
	private String password;
	private String phoneNumber;
	private String name;
	private LocalDateTime createdAt;

	public static PassengerDto from(Passenger passenger) {
		return PassengerDto.builder().id(passenger.getId().toString()).createdAt(passenger.getCreatedAt())
				.password(passenger.getPassword()).email(passenger.getEmail()).phoneNumber(passenger.getPhoneNumber())
				.name(passenger.getName()).build();
	}
}
