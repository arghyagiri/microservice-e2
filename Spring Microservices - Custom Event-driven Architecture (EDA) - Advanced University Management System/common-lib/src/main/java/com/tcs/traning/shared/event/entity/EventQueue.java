package com.tcs.traning.shared.event.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_queue")
public class EventQueue {

    @Id
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_data")
    private String eventData;

    @Column(name = "status")
    private String status;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "creation_date")
    private LocalDate creationDate;
}
