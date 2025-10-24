package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionResponseDTO;
import com.ds3c.tcc.ApiTcc.model.ActivitySubmission;
import com.ds3c.tcc.ApiTcc.service.ActivityService;
import com.ds3c.tcc.ApiTcc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class ActivitySubmissionMapper {
    private final ActivityService activityService;
    private final StudentService studentService;

    public ActivitySubmission toEntity(ActivitySubmissionRequestDTO dto, Long activityId) {
        ActivitySubmission activitySubmission = new ActivitySubmission();

        activitySubmission.setActivity(
                activityService.findById(activityId)
        );
        activitySubmission.setStudent(
                studentService.findById(dto.getStudentId())
        );
        activitySubmission.setSubmissionDate(
                LocalDate.now(ZoneId.of("America/Sao_Paulo"))
        );
        activitySubmission.setAnswerText(dto.getAnswerText());
        activitySubmission.setFileUrl(dto.getFileUrl());

        return activitySubmission;
    }

    public ActivitySubmissionResponseDTO toDTO(ActivitySubmission activitySubmission) {
        return new ActivitySubmissionResponseDTO(
                activitySubmission.getId(),
                activitySubmission.getActivity().getId(),
                activitySubmission.getStudent().getId(),
                activitySubmission.getAnswerText(),
                activitySubmission.getFileUrl(),
                activitySubmission.getSubmissionDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activitySubmission.getGrade()
        );
    }

    public ActivitySubmission updateEntityFromDTO(
            ActivitySubmissionRequestDTO dto, Long id) {
        ActivitySubmission activitySubmission = activityService.findSubmissionById(id);
        if (dto.getStudentId() != null) {
            activitySubmission.setStudent(
                    studentService.findById(dto.getStudentId())
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
