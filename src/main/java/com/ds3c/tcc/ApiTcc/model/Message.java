package com.ds3c.tcc.ApiTcc.model;

import com.ds3c.tcc.ApiTcc.enums.MessageTargetEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private MessageTargetEnum target;
    private Long schoolClassId;
    private Long authorId;
    private Long targetId;
    private LocalDateTime createdAt;
}
