package com.ds3c.tcc.ApiTcc.dto.PresenceLog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenceLogResponseDTO {
    private Long id;
    private Long studentId;
    private String date;
    private String entryTime;
    private String exitTime;
}
