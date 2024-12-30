package com.eventosnet.api.service;

import com.eventosnet.api.domain.address.Address;
import com.eventosnet.api.domain.coupon.Coupon;
import com.eventosnet.api.domain.event.Event;
import com.eventosnet.api.domain.event.EventDetailsDTO;
import com.eventosnet.api.domain.event.EventRequestDTO;
import com.eventosnet.api.domain.event.EventResponsesDTO;
import com.eventosnet.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CouponService couponService;


    public Event createEvent(EventRequestDTO data) {
        byte[] imgData = null;

        if (data.image() != null) {
            imgData = this.convertImageToBytes(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImage(imgData);
        newEvent.setRemote(data.remote());

        eventRepository.save(newEvent);

        if(!data.remote()) {
            addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    private byte[] convertImageToBytes(MultipartFile image) {
        String imgName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter a imagem", e);
        }
    }

    public Event findById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
    }


    public List<EventResponsesDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = eventRepository.findUpcomingEvents(new Date(), pageable);
        return eventsPage.map(event ->  new EventResponsesDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity() : "",
                        event.getAddress() != null ? event.getAddress().getUf() : "",
                        event.getRemote(),
                        event.getEventUrl()))
                        .stream().toList();
    }



    public List<EventResponsesDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
        title = (title != null) ? title : "";
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = this.eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);
        return eventsPage.map(event ->  new EventResponsesDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEventUrl()))
                .stream().toList();
    }

    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = findById(eventId);
        Optional<Address> address = addressService.findByEventId(eventId);
        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                address.isPresent() ? address.get().getCity() : "",
                address.isPresent() ? address.get().getUf() : "",
                event.getEventUrl(),
                couponDTOs);
    }
}