package com.example.demobot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "departments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "lector")
@EqualsAndHashCode(exclude = "lector")
public class Department {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private DepartmentName departmentNames;

    @Column(name = "head_departments", nullable = false)
    private Boolean headOfDepartments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_id", referencedColumnName = "id")
    @JsonIgnore
    private Lector lector;
}
