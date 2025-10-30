package com.ds3c.tcc.ApiTcc.model;

import com.ds3c.tcc.ApiTcc.enums.CoursesEnum;
import com.ds3c.tcc.ApiTcc.enums.YearsEnum;
import com.ds3c.tcc.ApiTcc.enums.ShiftsEnum;
import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnroll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private Integer rm;

    private String ra;
    private String cpf;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private YearsEnum gradeYear;

    @Enumerated(EnumType.STRING)
    private CoursesEnum course;

    @Enumerated(EnumType.STRING)
    private ShiftsEnum shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    private LocalDate birthdate;
    private String address;
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_unit_id")
    private SchoolUnit schoolUnit;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDate createdAt;
}
