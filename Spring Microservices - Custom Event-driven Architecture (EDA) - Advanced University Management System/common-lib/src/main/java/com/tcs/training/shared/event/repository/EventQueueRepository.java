package com.tcs.training.shared.event.repository;


import com.tcs.training.shared.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventQueueRepository extends JpaRepository<Event, Long> {

    @Query("select eq from Event eq where eq.status = 'NEW' and eq.serviceName = :serviceName order by eq.creationDate")
    List<Event> findMyEventQueue(@Param("serviceName") String serviceName);
}
