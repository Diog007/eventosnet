package com.eventosnet.api.domain.coupon;

import com.eventosnet.api.domain.event.Event;

import java.util.Date;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
