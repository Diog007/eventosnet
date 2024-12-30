package com.eventosnet.api.domain.event;

import com.eventosnet.api.domain.coupon.Coupon;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(UUID id,
                              String title,
                              String description,
                              Date date,
                              String city,
                              String state,
                              String eventUrl,
                              List<CouponDTO> coupons) {
    public record CouponDTO(
            String code,
            Integer discount,
            Date valid) {
    }
}