package rest.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.service.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
