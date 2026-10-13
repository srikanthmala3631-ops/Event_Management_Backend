package com.eventapp.repository;

import com.eventapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // findAll(), findById(), save(), deleteById() already come from JpaRepository
}
