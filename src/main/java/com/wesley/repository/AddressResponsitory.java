package com.wesley.repository;

import com.wesley.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressResponsitory extends JpaRepository<Address, Long> {
}
