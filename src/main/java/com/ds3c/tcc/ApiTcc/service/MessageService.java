package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Message.MessageRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.MessageMapper;
import com.ds3c.tcc.ApiTcc.model.Message;
import com.ds3c.tcc.ApiTcc.repository.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService extends CRUDService<Message, Long> {

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    @Lazy
    public MessageService(
            MessageMapper messageMapper,
            MessageRepository messageRepository) {
        super(Message.class, messageRepository);
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    public Message create(MessageRequestDTO dto) {
        return save(messageMapper.toEntity(dto));
    }

    public Message update(MessageRequestDTO dto, Long id) {
        return save(messageMapper.updateEntityFromDTO(dto, id));
    }

    public List<Message> findVisibleMessages(Long userId, Long classId) {
        return messageRepository.findVisibleMessagesForUser(userId, classId);
    }
}
