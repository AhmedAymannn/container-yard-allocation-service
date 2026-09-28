package com.ahmed.terminal.yardallocation.Entity;

import com.ahmed.terminal.yardallocation.Enums.ContainerCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "block")
@Getter
@Setter
@NoArgsConstructor
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    // nullable = true (default) matches "ENUM(...) NULL" in the table —
    // a null here means this block accepts any category

    @Enumerated(EnumType.STRING)
    @Column(name = "allowed_category")
    private ContainerCategory allowedCategory;


}