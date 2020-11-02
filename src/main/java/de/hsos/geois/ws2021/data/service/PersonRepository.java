package de.hsos.geois.ws2021.data.service;

import de.hsos.geois.ws2021.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}