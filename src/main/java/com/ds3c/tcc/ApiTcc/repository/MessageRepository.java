package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.enums.MessageTargetEnum;
import com.ds3c.tcc.ApiTcc.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByTarget(MessageTargetEnum target);
    List<Message> findAllBySchoolClass_Id(Long schoolClassId);
    List<Message> findAllByTargetUser_Id(Long targetId);

    @Query("SELECT m FROM Message m WHERE m.target = 'GLOBAL' " +
            "OR (m.target = 'CLASS' AND m.schoolClass.id = :classId) " +
            "OR (m.target = 'PRIVATE' AND (m.targetUser.id = :userId OR m.author.id = :userId))")
    List<Message> findVisibleMessagesForUser(@Param("userId") Long userId, @Param("classId") Long classId);
}
