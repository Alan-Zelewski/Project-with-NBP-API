package com.azelewski.exercise.repository;

import com.azelewski.exercise.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
    @Query("select c from Computer c where lower(c.name) like %:searchValue%")
    Page<Computer> searchComputers(@Param("searchValue") String searchValue, Pageable pageable);

    Page<Computer> findByDate(LocalDate searchValue, Pageable pageable);
}
