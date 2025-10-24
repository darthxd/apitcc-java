package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Message.MessageRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Message.MessageResponseDTO;
import com.ds3c.tcc.ApiTcc.enums.MessageTargetEnum;
import com.ds3c.tcc.ApiTcc.model.Message;
import com.ds3c.tcc.ApiTcc.model.User;
import com.ds3c.tcc.ApiTcc.service.MessageService;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class MessageMapper {
    private final UserService userService;
    private final SchoolClassService schoolClassService;
    private final MessageService messageService;

    public Message toEntity(MessageRequestDTO dto) {
        User author = userService.findById(dto.getAuthorId());
        MessageTargetEnum target = MessageTargetEnum.valueOf(dto.getTarget().toUpperCase());

        Message message = new Message();

        message.setTitle(dto.getTitle());
        message.setBody(dto.getBody());
        message.setTarget(target);
        message.setAuthor(author);
        message.setCreatedAt(dto.getCreatedAt());

        switch (target) {
            case GLOBAL -> {}
            case CLASS -> {
                if (dto.getSchoolClassId() == null)
                    throw new IllegalArgumentException("School class id is required.");
                message.setSchoolClass(
                        schoolClassService.findById(dto.getSchoolClassId())
                );
            }
            case PRIVATE -> {
                if (dto.getTargetId() == null)
                    throw new IllegalArgumentException("Target id is required.");
                message.setTargetUser(
                        userService.findById(dto.getTargetId())
                );
            }
        }

        return message;
    }

    public MessageResponseDTO toDTO(Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();

        dto.setId(message.getId());
        dto.setTitle(message.getTitle());
        dto.setBody(message.getBody());
        dto.setTarget(message.getTarget().name());
        dto.setAuthorName(
                userService.findDisplayNameById(message.getAuthor().getId())
        );
        dto.setTargetName(
                message.getTargetUser() != null ?
                        userService.findDisplayNameById(message.getTargetUser().getId()) : null
        );
        dto.setSchoolClassId(
                message.getSchoolClass() != null ?
                        message.getSchoolClass().getId() : null
        );
        dto.setCreatedAt(message.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return dto;
    }

    public Message updateEntityFromDTO(MessageRequestDTO dto, Long id) {
        Message message = messageService.findById(id);

        if (StringUtils.hasText(dto.getTitle())) {
            message.setTitle(dto.getTitle());
        }
        if (StringUtils.hasText(dto.getBody())) {
            message.setBody(dto.getBody());
        }

        return message;
    }
}
