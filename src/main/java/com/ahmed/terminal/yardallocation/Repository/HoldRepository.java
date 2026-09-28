package com.ahmed.terminal.yardallocation.Repository;


import com.ahmed.terminal.yardallocation.Entity.Hold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldRepository extends JpaRepository <Hold ,Long> {
}
