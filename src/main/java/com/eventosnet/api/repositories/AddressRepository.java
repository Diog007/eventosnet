package com.eventosnet.api.repositories;

import com.eventosnet.api.domain.address.Address;
import com.eventosnet.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
