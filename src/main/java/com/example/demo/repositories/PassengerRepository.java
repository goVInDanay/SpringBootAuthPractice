package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
