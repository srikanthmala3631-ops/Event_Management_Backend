package com.eventapp.controller;

import com.eventapp.dto.RegistrationRequest;
import com.eventapp.model.Event;
import com.eventapp.model.Registration;
import com.eventapp.model.User;
import com.eventapp.repository.EventRepository;
import com.eventapp.repository.RegistrationRepository;
import com.eventapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST API for event registrations - this links a user to an event.
 */
@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    // POST /api/registrations - a logged-in user registers for an event
    @PostMapping
    public ResponseEntity<?> registerForEvent(@RequestBody RegistrationRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        Optional<Event> eventOpt = eventRepository.findById(request.getEventId());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found."));
        }
        if (eventOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found."));
        }

        // Prevent registering twice for the same event
        boolean alreadyRegistered = registrationRepository
                .existsByUserIdAndEventId(request.getUserId(), request.getEventId());
        if (alreadyRegistered) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "You are already registered for this event."));
        }

        // Prevent registering once the event is full
        long currentCount = registrationRepository.countByEventId(request.getEventId());
        if (currentCount >= eventOpt.get().getMaxParticipants()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Sorry, this event has reached its maximum participants."));
        }

        Registration registration = new Registration(userOpt.get(), eventOpt.get());
        Registration saved = registrationRepository.save(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/registrations/user/{userId} - "My Registrations" page
    @GetMapping("/user/{userId}")
    public List<Registration> getRegistrationsByUser(@PathVariable Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    // GET /api/registrations/event/{eventId} - admin: view users registered for an event
    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }
}
