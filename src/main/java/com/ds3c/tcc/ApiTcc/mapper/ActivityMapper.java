package com.ds3c.tcc.ApiTcc.mapper;

import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Activity;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.model.Teacher;
import com.ds3c.tcc.ApiTcc.service.ActivityService;
import com.ds3c.tcc.ApiTcc.service.SchoolClassService;
import com.ds3c.tcc.ApiTcc.service.TeacherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ActivityMapper {
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final ActivityService activityService;

    @Lazy
    public ActivityMapper(
            TeacherService teacherService,
            SchoolClassService schoolClassService,
            ActivityService activityService) {
        this.teacherService = teacherService;
        this.schoolClassService = schoolClassService;
        this.activityService = activityService;
    }

    public Activity toEntity(ActivityRequestDTO dto) {
        Activity activity = new Activity();
        Teacher teacher = teacherService.findById(dto.getTeacherId());
        SchoolClass schoolClass = schoolClassService.findById(dto.getSchoolClassId());

        activity.setTitle(dto.getTitle());
        activity.setDescription(dto.getDescription());
        activity.setDeadline(LocalDate.parse(
                dto.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        activity.setCreationDate(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        activity.setMaxScore(dto.getMaxScore());
        activity.setTeacher(teacher);
        activity.setSchoolClass(schoolClass);

        return activity;
    }

    public ActivityResponseDTO toDTO(Activity activity) {
        return new ActivityResponseDTO(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getDeadline()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getCreationDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                activity.getMaxScore(),
                activity.getTeacher().getId(),
                activity.getSchoolClass().getId()
        );
    }

    public Activity updateEntityFromDTO(ActivityRequestDTO dto, Long id) {
        Activity activity = activityService.findById(id);
        if (StringUtils.hasText(dto.getTitle())) {
            activity.setTitle(dto.getTitle());
        }
        if (StringUtils.hasText(dto.getDescription())) {
            activity.setDescription(dto.getDescription());
        }
        if (StringUtils.hasText(dto.getDeadline())) {
            activity.setDeadline(LocalDate.parse(
                    dto.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (dto.getMaxScore() != null && dto.getMaxScore() > 0) {
            activity.setMaxScore(dto.getMaxScore());
        }
        if (dto.getTeacherId() != null) {
            activity.setTeacher(
                    teacherService.findById(dto.getTeacherId())
            );
        }
        if (dto.getSchoolClassId() != null) {
            activity.setSchoolClass(
                    schoolClassService.findById(dto.getSchoolClassId())
            );
        }
        return activity;
    }
}
