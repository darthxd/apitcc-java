package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResumeDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherCreateDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.Teacher.TeacherResumeDTO;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.SchoolSubjectService;
import com.ds3c.tcc.ApiTcc.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TeacherMapper {
    private final UserService userService;
    private final SchoolClassService schoolClassService;
    private final SchoolSubjectService schoolSubjectService;

    public TeacherMapper(
            UserService userService,
            SchoolClassService schoolClassService, SchoolSubjectService schoolSubjectService) {
        this.userService = userService;
        this.schoolClassService = schoolClassService;
        this.schoolSubjectService = schoolSubjectService;
    }

    public Teacher toModel(TeacherCreateDTO teacherCreateDTO, Long userId) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherCreateDTO.getName());
        teacher.setPhone(teacherCreateDTO.getPhone());
        teacher.setCpf(teacherCreateDTO.getCpf());
        teacher.setEmail(teacherCreateDTO.getEmail());
        Set<SchoolClass> schoolClassSet = teacherCreateDTO
                .getClassesIds().stream()
                .map(id -> schoolClassService.getSchoolClassById(id))
                .collect(Collectors.toSet());
        teacher.setClasses(schoolClassSet);
        Set<SchoolSubject> schoolSubjectSet = teacherCreateDTO
                .getSubjectsIds().stream()
                .map(id -> schoolSubjectService.getSchoolSubjectById(id))
                .collect(Collectors.toSet());
        teacher.setSubjects(schoolSubjectSet);
        teacher.setUserId(userId);
        return teacher;
    }

    public TeacherResponseDTO toDTO(Teacher teacher) {
        TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setId(teacher.getId());
        teacherResponseDTO.setCpf(teacher.getCpf());
        teacherResponseDTO.setName(teacher.getName());
        teacherResponseDTO.setPhone(teacher.getPhone());
        teacherResponseDTO.setEmail(teacher.getEmail());
        // Não terminado
        // Falta fazer um mapper para o DTO de turmas e matérias
    }

    public TeacherResumeDTO toResumeDTO(Teacher teacher) {
        TeacherResumeDTO teacherResumeDTO = new TeacherResumeDTO();
        teacherResumeDTO.setId(teacher.getId());
        teacherResumeDTO.setCpf(teacher.getCpf());
        teacherResumeDTO.setName(teacher.getName());
        teacherResumeDTO.setPhone(teacher.getPhone());
        teacherResumeDTO.setEmail(teacher.getEmail());
        return teacherResumeDTO;
    }

    public List<TeacherResumeDTO> toListResumeDTO(List<Teacher> teacherList) {
        List<TeacherResumeDTO> teacherResumeDTOList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherResumeDTOList.add(toResumeDTO(teacher));
        }
        return teacherResumeDTOList;
    }
}
