package com.eventosnet.api.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String title,
        String description,
        String eventUrl,
        Boolean remote,
        Date date
) {}
