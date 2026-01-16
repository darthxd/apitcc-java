package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.StudentEnroll;
import com.ds3c.tcc.ApiTcc.model.StudentPresenceLog;
import com.ds3c.tcc.ApiTcc.repository.PresenceLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceLogService extends CRUDService<StudentPresenceLog, Long> {

    private final PresenceLogRepository presenceLogRepository;

    public PresenceLogService(
            PresenceLogRepository presenceLogRepository) {
        super(StudentPresenceLog.class, presenceLogRepository);
        this.presenceLogRepository = presenceLogRepository;
    }

    public boolean togglePresence(Student student) {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        if (!student.getInschool()) {

            StudentPresenceLog presenceLog = presenceLogRepository
                    .findByStudentIdAndDate(student.getId(), today)
                    .orElse(new StudentPresenceLog());

            presenceLog.setStudent(student);
            presenceLog.setDate(today);
            presenceLog.setEntryTime(now);

            save(presenceLog);
            return true;
        } else {
            StudentPresenceLog presenceLog = presenceLogRepository
                    .findByStudentIdAndDate(student.getId(), today)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "The presence log for the student id: "+student.getId()+" and date: "+today+" was not found."
                    ));

            presenceLog.setExitTime(now);

            save(presenceLog);
            return false;
        }
    }

    public StudentPresenceLog findByStudentAndDate(
            Long studentId, String date) {
        return presenceLogRepository.findByStudentIdAndDate(
                studentId, LocalDate.parse(date)
        ).orElseThrow(() -> (new EntityNotFoundException(
                "The presence log for the student id: "+studentId+" and date: "+date+" was not found."
        )));
    }

    public List<StudentPresenceLog> findAllByStudent(Long studentId) {
        return presenceLogRepository.findAllByStudentId(studentId);
    }

    public List<StudentPresenceLog> findAllByDate(String date) {
        return presenceLogRepository.findAllByDateEquals(
                LocalDate.parse(date)
        );
    }
}
