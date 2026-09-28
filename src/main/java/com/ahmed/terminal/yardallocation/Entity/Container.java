package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.ContainerCategory;
import com.ahmed.terminal.yardallocation.Enums.ContainerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "container")
@Getter
@Setter
@NoArgsConstructor
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_number", nullable = false, unique = true, length = 20)
    private String containerNumber;

    @Column(name = "iso_type", nullable = false, length = 10)
    private String isoType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerCategory category;

    @Column(name = "weight_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal weightKg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerStatus status = ContainerStatus.EXPECTED;

    // nullable = true matches "vessel_call_id BIGINT NULL"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_call_id", nullable = true)
    private VesselCall vesselCall;

    // nullable = true matches "current_slot_id BIGINT NULL" —
    // a container in EXPECTED status has no slot yet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_slot_id", nullable = true)
    private Slot currentSlot;

    @Column(name = "yarded_at")
    private LocalDateTime yardedAt;
}