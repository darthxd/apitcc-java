package com.ds3c.tcc.ApiTcc.model;

import com.ds3c.tcc.ApiTcc.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("student")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    private String name;
    private Integer rm;
    private String ra;
    private String cpf;
    private String phone;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    private LocalDate birthdate;
    private String address;
    @Column(length = 512)
    private String photoUrl;

    private Boolean biometry;
    private Boolean inschool = false;
    private Boolean sendNotification = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enroll_id")
    private StudentEnroll enroll;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.INACTIVE;
}
