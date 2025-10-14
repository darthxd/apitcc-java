package com.ds3c.tcc.ApiTcc.dto.WebSocket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketMessage {
    private String type;
    private String source;
    private String target;
    private Object payload;
}
