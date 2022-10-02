package com.example.demobot.repository;

import com.example.demobot.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectorDAO extends JpaRepository<Lector,Long>, CrudRepository<Lector,Long> {
    List<Lector> findByNameLike(String str);
    List<Lector> findBySurnameLike(String str);


}
