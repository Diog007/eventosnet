package com.eventosnet.api.domain.event;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "event")
@Entity
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    private String eventUrl;
    private Boolean remote;
    private Date date;

    @Lob
    private byte[] image;

    public Event() {
    }

    public Event(UUID id, String title, String description, String eventUrl, Boolean remote, Date date, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventUrl = eventUrl;
        this.remote = remote;
        this.date = date;
        this.image = image;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
