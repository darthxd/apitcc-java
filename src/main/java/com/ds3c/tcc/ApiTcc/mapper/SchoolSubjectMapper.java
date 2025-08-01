package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectCreateDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SchoolSubjectMapper {
    private final TeacherService teacherService;

    public SchoolSubjectMapper(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public SchoolSubject toModel(SchoolSubjectCreateDTO schoolSubjectCreateDTO) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(schoolSubjectCreateDTO.getName());
        Set<Teacher> teacherSet = schoolSubjectCreateDTO
                .getTeachersIds().stream()
                .map(id -> teacherService.getTeacherById(id))
                .collect(Collectors.toSet());
        schoolSubject.setTeachers(teacherSet);
        return schoolSubject;
    }
}
