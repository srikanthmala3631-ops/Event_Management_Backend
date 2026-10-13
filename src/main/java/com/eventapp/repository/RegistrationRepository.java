package com.eventapp.repository;

import com.eventapp.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Used for "My Registrations" page
    List<Registration> findByUserId(Long userId);

    // Used by admin to see who registered for a specific event
    List<Registration> findByEventId(Long eventId);

    // Used to stop a user registering twice for the same event,
    // and to count how many people are registered (for the max-participants limit)
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    long countByEventId(Long eventId);
}
