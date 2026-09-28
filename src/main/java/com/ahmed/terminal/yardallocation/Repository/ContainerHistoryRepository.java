package com.ahmed.terminal.yardallocation.Repository;

import com.ahmed.terminal.yardallocation.Entity.ContainerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContainerHistoryRepository extends JpaRepository <ContainerHistory  , Long> {
}
