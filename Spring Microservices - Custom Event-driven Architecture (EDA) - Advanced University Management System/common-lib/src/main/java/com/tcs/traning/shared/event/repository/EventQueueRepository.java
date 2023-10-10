package com.tcs.traning.shared.event.repository;


import com.tcs.traning.shared.event.entity.EventQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventQueueRepository extends JpaRepository<EventQueue, Long> {

    EventQueue findTopByOrderByCreationDateAndServiceNameAndStatus(String serviceName, String status);
}
