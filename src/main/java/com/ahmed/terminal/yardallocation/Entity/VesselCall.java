package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.VesselCallStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vessel_call")
@Getter
@Setter
@NoArgsConstructor
public class VesselCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vessel_name", nullable = false, length = 100)
    private String vesselName;

    @Column(name = "voyage_number", nullable = false, length = 50)
    private String voyageNumber;

    private LocalDateTime eta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VesselCallStatus status = VesselCallStatus.PLANNED;
}