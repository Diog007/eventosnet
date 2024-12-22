package com.eventosnet.api.controller;

import com.eventosnet.api.domain.event.Event;
import com.eventosnet.api.domain.event.EventRequestDTO;
import com.eventosnet.api.domain.event.EventResponseDTO;
import com.eventosnet.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<EventResponseDTO> create(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long date,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam Boolean remote,
            @RequestParam(required = false) String eventUrl,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        EventRequestDTO requestDTO = new EventRequestDTO(title, description, date, city, state, remote, eventUrl, image);
        Event newEvent = eventService.createEvent(requestDTO);
        EventResponseDTO responseDTO = new EventResponseDTO(
                newEvent.getId(),
                newEvent.getTitle(),
                newEvent.getDescription(),
                newEvent.getEventUrl(),
                newEvent.getRemote(),
                newEvent.getDate()
        );
        return ResponseEntity.ok(responseDTO);
    }

}
