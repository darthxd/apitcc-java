package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.enums.MessageTargetEnum;
import com.ds3c.tcc.ApiTcc.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByTarget(MessageTargetEnum target);
    List<Message> findAllBySchoolClassId(Long schoolClassId);
    List<Message> findAllByTargetId(Long targetId);
}
