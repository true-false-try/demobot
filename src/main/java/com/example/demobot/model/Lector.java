package com.example.demobot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "lectors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree", nullable = false)
    private LectorDegree degree;

    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Department> departmen;
}
