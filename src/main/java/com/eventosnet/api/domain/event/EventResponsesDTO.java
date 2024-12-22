package com.eventosnet.api.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponsesDTO(
        UUID id,
        String title,
        String description,
        Date date,
        String city,
        String state,
        Boolean remote,
        String eventUrl
) {}
