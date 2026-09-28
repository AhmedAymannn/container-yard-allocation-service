package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.ContainerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "container_history")
@Getter
@Setter
@NoArgsConstructor
public class ContainerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plain Long on purpose — no @ManyToOne here.
    // We don't want Hibernate lazily loading a full Container
    // just to write an audit log line, and we never want a
    // cascading delete to touch this table.

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status")
    private ContainerStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false)
    private ContainerStatus toStatus;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}