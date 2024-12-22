package com.eventosnet.api.service;

import com.eventosnet.api.domain.coupon.Coupon;
import com.eventosnet.api.domain.coupon.CouponRequestDTO;
import com.eventosnet.api.domain.event.Event;
import com.eventosnet.api.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventService eventService;


    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data) {
        Event event = eventService.findById(eventId);

        Coupon coupon = new Coupon();
        coupon.setCode(data.code());
        coupon.setDiscount(data.discount());
        coupon.setValid(new Date(data.valid()));
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }
}
