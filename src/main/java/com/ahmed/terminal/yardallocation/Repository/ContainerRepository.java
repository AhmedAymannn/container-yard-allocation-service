package com.ahmed.terminal.yardallocation.Repository;

import com.ahmed.terminal.yardallocation.Entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContainerRepository extends JpaRepository <Container , Long>  {
}
