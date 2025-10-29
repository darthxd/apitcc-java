package com.ds3c.tcc.ApiTcc.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("secretary")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Secretary extends User{
    private String email;
    private String phone;
}
