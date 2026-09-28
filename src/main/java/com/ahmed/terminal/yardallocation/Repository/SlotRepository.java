package com.ahmed.terminal.yardallocation.Repository;


import com.ahmed.terminal.yardallocation.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository <Slot , Long> {
}
