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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "salary", nullable = false)
    private Long salary;

    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Department> departments;

}
