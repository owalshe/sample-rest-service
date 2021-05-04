package rest.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.service.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
