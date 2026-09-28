package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.HoldType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "hold")
@Getter
@Setter
@NoArgsConstructor
public class Hold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This one IS a real relationship (unlike ContainerHistory) —
    // because your business logic actually needs to navigate from
    // a Container to its active Holds, e.g. "does this container
    // have any active hold?" This is a real domain query, not just
    // a log lookup.


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @Enumerated(EnumType.STRING)
    @Column(name = "hold_type", nullable = false)
    private HoldType holdType;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "placed_at", nullable = false)
    private LocalDateTime placedAt = LocalDateTime.now();

    @Column(name = "released_at")
    private LocalDateTime releasedAt;
}