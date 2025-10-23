package com.ds3c.tcc.ApiTcc.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDTO {
    private String title;
    private String body;
    private String target;
    private Long schoolClassId;
    private Long authorId;
    private Long targetId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
