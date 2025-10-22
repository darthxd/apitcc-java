package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Grade.GradeRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Grade.GradeResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Grade;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.model.Student;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.GradeService;
import com.ds3c.tcc.ApiTcc.service.SchoolSubjectService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GradeMapper {
    private final StudentService studentService;
    private final SchoolSubjectService schoolSubjectService;
    private final TeacherService teacherService;
    private final GradeService gradeService;

    @Lazy
    public GradeMapper(
            StudentService studentService,
            SchoolSubjectService schoolSubjectService,
            TeacherService teacherService,
            GradeService gradeService) {
        this.studentService = studentService;
        this.schoolSubjectService = schoolSubjectService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
    }

    public Grade toEntity(GradeRequestDTO dto) {
        Student student = studentService.getStudentById(dto.getStudentId());
        SchoolSubject subject = schoolSubjectService.getSchoolSubjectById(dto.getSubjectId());
        Teacher teacher = teacherService.getTeacherById(dto.getTeacherId());
        Grade grade = new Grade();

        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setTeacher(teacher);
        grade.setBimester(dto.getBimester());
        grade.setYear(dto.getYear());
        grade.setGrade(dto.getGrade());
        grade.setComment(dto.getComment());
        grade.setCreatedAt(dto.getCreatedAt());

        return grade;
    }

    public GradeResponseDTO toDTO(Grade grade) {
        return new GradeResponseDTO(
                grade.getId(),
                grade.getStudent().getId(),
                grade.getSubject().getId(),
                grade.getTeacher().getId(),
                grade.getBimester(),
                grade.getYear(),
                grade.getGrade(),
                grade.getComment(),
                grade.getCreatedAt().toString()
        );
    }

    public Grade updateEntityFromDTO(GradeRequestDTO dto, Long id) {
        Grade grade = gradeService.getGradeById(id);
        if (dto.getStudentId() != null) {
            grade.setStudent(
                    studentService.getStudentById(dto.getStudentId())
            );
        }
        if (dto.getSubjectId() != null) {
            grade.setSubject(
                    schoolSubjectService.getSchoolSubjectById(dto.getSubjectId())
            );
        }
        if (dto.getTeacherId() != null) {
            grade.setTeacher(
                    teacherService.getTeacherById(dto.getTeacherId())
            );
        }
        if (dto.getBimester() != null) {
            grade.setBimester(dto.getBimester());
        }
        if (dto.getYear() != null) {
            grade.setYear(dto.getYear());
        }
        if (dto.getGrade() != null) {
            grade.setGrade(dto.getGrade());
        }
        if (StringUtils.hasText(dto.getComment())) {
            grade.setComment(dto.getComment());
        }
        return grade;
    }
}
