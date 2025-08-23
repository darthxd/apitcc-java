package com.ds3c.tcc.ApiTcc.dto.Activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String deadline;
    private Double maxScore;
    private Long teacherId;
    private Long schoolClassId;
}
