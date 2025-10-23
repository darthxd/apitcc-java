package com.ds3c.tcc.ApiTcc.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private Long id;
    private String title;
    private String body;
    private String target;
    private Long schoolClassId;
    private String authorName;
    private String targetName;
    private String createdAt;
}
