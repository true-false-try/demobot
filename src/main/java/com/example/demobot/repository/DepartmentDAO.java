package com.example.demobot.repository;

import com.example.demobot.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentDAO extends JpaRepository<Department,Long>, CrudRepository<Department,Long> {
}
