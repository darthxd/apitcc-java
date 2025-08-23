package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionResponseDTO;
import com.ds3c.tcc.ApiTcc.model.ActivitySubmission;
import com.ds3c.tcc.ApiTcc.service.ActivityService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivitySubmissionMapper {
    private final ActivityService activityService;
    private final StudentService studentService;

    @Lazy
    public ActivitySubmissionMapper(ActivityService activityService,
                                    StudentService studentService) {
        this.activityService = activityService;
        this.studentService = studentService;
    }

    public ActivitySubmission toModel(ActivitySubmissionRequestDTO dto, Long activityId) {
        ActivitySubmission activitySubmission = new ActivitySubmission();
        activitySubmission.setActivity(
                activityService.getActivityById(activityId)
        );
        activitySubmission.setStudent(
                studentService.getStudentById(dto.getStudentId())
        );
        activitySubmission.setSubmissionDate(
                LocalDate.now()
        );
        activitySubmission.setAnswerText(dto.getAnswerText());
        activitySubmission.setFileUrl(dto.getFileUrl());
        return activitySubmission;
    }

    public ActivitySubmissionResponseDTO toDTO(ActivitySubmission activitySubmission) {
        ActivitySubmissionResponseDTO dto = new ActivitySubmissionResponseDTO();
        dto.setId(activitySubmission.getId());
        dto.setActivityId(activitySubmission.getActivity().getId());
        dto.setStudentId(activitySubmission.getStudent().getId());
        dto.setSubmissionDate(activitySubmission.getSubmissionDate().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ));
        dto.setAnswerText(activitySubmission.getAnswerText());
        dto.setFileUrl(activitySubmission.getFileUrl());
        dto.setGrade(activitySubmission.getGrade());
        return dto;
    }

    public List<ActivitySubmissionResponseDTO> toListDTO(
            List<ActivitySubmission> activitySubmissionList) {
        List<ActivitySubmissionResponseDTO> dtoList = new ArrayList<>();
        for (ActivitySubmission activitySubmission : activitySubmissionList) {
            dtoList.add(toDTO(activitySubmission));
        }
        return dtoList;
    }

    public ActivitySubmission updateModelFromDTO(
            ActivitySubmissionRequestDTO dto, Long id) {
        ActivitySubmission activitySubmission = activityService.getActivitySubmissionById(id);
        if (dto.getStudentId() != null) {
            activitySubmission.setStudent(
                    studentService.getStudentById(dto.getStudentId())
            );
        }
        if (StringUtils.hasText(dto.getAnswerText())) {
            activitySubmission.setAnswerText(dto.getAnswerText());
        }
        if (StringUtils.hasText(dto.getFileUrl())) {
            activitySubmission.setFileUrl(dto.getFileUrl());
        }
        return activitySubmission;
    }
}
