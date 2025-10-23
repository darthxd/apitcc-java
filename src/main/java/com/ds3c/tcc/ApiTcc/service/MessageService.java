package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.model.Message;
import com.ds3c.tcc.ApiTcc.repository.MessageRepository;

public class MessageService extends CRUDService<Message, Long> {
    public MessageService(MessageRepository messageRepository) {
        super(messageRepository);
    }

    // public Message create(MessageRequestDTO dto) {

    // }
}
