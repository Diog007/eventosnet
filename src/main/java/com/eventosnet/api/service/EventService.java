package com.eventosnet.api.service;

import com.eventosnet.api.domain.event.Event;
import com.eventosnet.api.domain.event.EventRequestDTO;
import com.eventosnet.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

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

        return eventRepository.save(newEvent);
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

}
