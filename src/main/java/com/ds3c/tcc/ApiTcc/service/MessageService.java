package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Message.MessageRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.MessageMapper;
import com.ds3c.tcc.ApiTcc.model.Message;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.repository.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService extends CRUDService<Message, Long> {

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Lazy
    public MessageService(
            MessageMapper messageMapper,
            MessageRepository messageRepository, UserService userService) {
        super(Message.class, messageRepository);
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message create(MessageRequestDTO dto) {
        return save(messageMapper.toEntity(dto));
    }

    public Message update(MessageRequestDTO dto, Long id) {
        return save(messageMapper.updateEntityFromDTO(dto, id));
    }

    public List<Message> findVisibleMessages(Long userId) {
        User user = userService.findById(userId);
        if (user instanceof Student s) {
            return messageRepository.findVisibleMessagesForUser(userId, s.getSchoolClass().getId());
        }
        return messageRepository.findVisibleMessagesForUser(userId, null);
    }
}
