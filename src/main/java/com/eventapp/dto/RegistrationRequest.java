package com.eventapp.dto;

/**
 * Used for POST /api/registrations
 * (a logged-in user registering for one event)
 */
public class RegistrationRequest {

    private Long userId;
    private Long eventId;

    public RegistrationRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
