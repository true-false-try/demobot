package com.example.demobot.repository;

import com.example.demobot.model.Department;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.LectorDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDAO extends JpaRepository<Department,Long>, CrudRepository<Department,Long> {
    @Query("select count(d) from Department d inner join d.lector l where d.departmentNames = :departmentName and l.degree = :degree")
    Long statistic(@Param("departmentName") DepartmentName name,
                   @Param("degree") LectorDegree degree);
}
