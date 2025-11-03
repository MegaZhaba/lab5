package com.example.Lab5.repository;

import com.example.Lab5.entities.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorsRepository extends JpaRepository<Operators, Long> {
}
