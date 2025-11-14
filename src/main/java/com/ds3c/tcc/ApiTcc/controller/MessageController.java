package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Message.MessageRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Message.MessageResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.MessageMapper;
import com.ds3c.tcc.ApiTcc.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> create(@RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(
                messageMapper.toDTO(messageService.create(dto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> update(
            @RequestBody MessageRequestDTO dto,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                messageMapper.toDTO(messageService.update(dto, id))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        messageService.delete(messageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> findAll() {
        return ResponseEntity.ok(
                messageService.findAll()
                        .stream().map(messageMapper::toDTO).toList()
        );
    }

    @GetMapping("/visible/{userId}")
    public ResponseEntity<List<MessageResponseDTO>> findVisibleMessages(
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                messageService.findVisibleMessages(userId)
                        .stream().map(messageMapper::toDTO).toList()
        );
    }
}
