package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entityservice.models.Driver;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	Optional<Driver> findByAdhaarNumber(String adhaarNumber);
}
