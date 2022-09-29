package com.example.demobot.repository;

import com.example.demobot.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LectorDAO extends JpaRepository<Lector,Long>, CrudRepository<Lector,Long> {
}