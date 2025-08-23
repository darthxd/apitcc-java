package com.ds3c.tcc.ApiTcc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Activity activity;
    @ManyToOne
    private Student student;
    private String answerText;
    private String fileUrl;
    private LocalDate submissionDate;
    private Double grade;
}
