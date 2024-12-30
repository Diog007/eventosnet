package com.eventosnet.api.repositories;

import com.eventosnet.api.domain.coupon.Coupon;
import com.eventosnet.api.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
}
