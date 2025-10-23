package com.ds3c.tcc.ApiTcc.model;

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
    private String ra;
    private String rm;
    private String cpf;
    private String phone;
    private String email;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;
    private LocalDate birthdate;
    private Boolean biometry;
    private String photo;
    private Boolean inschool = false;
    private Boolean sendNotification = false;
}
