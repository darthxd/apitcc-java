package com.ds3c.tcc.ApiTcc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("teacher")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User{
    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String cpf;

    private String email;
    private String phone;

    private Set<Long> subjectIds;
    private Set<Long> schoolClassIds;
}
