package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.StudentPresenceLog;
import com.ds3c.tcc.ApiTcc.repository.PresenceLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceLogService extends CRUDService<StudentPresenceLog, Long> {

    private final StudentService studentService;
    private final PresenceLogRepository presenceLogRepository;

    public PresenceLogService(
            StudentService studentService,
            PresenceLogRepository presenceLogRepository) {
        super(StudentPresenceLog.class, presenceLogRepository);
        this.studentService = studentService;
        this.presenceLogRepository = presenceLogRepository;
    }

    public void togglePresence(Long studentId) {
        Student student = studentService.findById(studentId);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        if (!student.getInschool()) {

            StudentPresenceLog presenceLog = presenceLogRepository
                    .findByStudentIdAndDate(studentId, today)
                    .orElse(new StudentPresenceLog());

            presenceLog.setStudent(student);
            presenceLog.setDate(today);
            presenceLog.setEntryTime(now);

            save(presenceLog);
            studentService.setInSchool(student, true);
        } else {
            StudentPresenceLog presenceLog = presenceLogRepository
                    .findByStudentIdAndDate(studentId, today)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "The presence log for the student id: "+studentId+" and date: "+today+" was not found."
                    ));

            presenceLog.setExitTime(now);

            save(presenceLog);
            studentService.setInSchool(student, false);
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
