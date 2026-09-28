package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.SlotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slot")
@Getter
@Setter
@NoArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "size_capacity", nullable = false, length = 5)
    private String sizeCapacity;

    @Column(name = "supports_reefer", nullable = false)
    private boolean supportsReefer;

    @Column(name = "hazmat_approved", nullable = false)
    private boolean hazmatApproved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.FREE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    // This is the optimistic-locking field. Hibernate manages this
    // column automatically — you never set it yourself in code.
    // It increments on every UPDATE, and Hibernate checks it hasn't
    // changed since you read the row, before committing your update.

    @Version
    @Column(nullable = false)
    private Long version;
}