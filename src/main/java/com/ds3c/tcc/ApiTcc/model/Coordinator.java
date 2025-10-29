package com.ds3c.tcc.ApiTcc.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("coordinator")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinator extends User{
    private String name;
    private String cpf;
    private String email;
    private String phone;
}
