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
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityMapper {
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final ActivityService activityService;

    @Lazy
    public ActivityMapper(TeacherService teacherService,
                          SchoolClassService schoolClassService,
                          ActivityService activityService) {
        this.teacherService = teacherService;
        this.schoolClassService = schoolClassService;
        this.activityService = activityService;
    }

    public Activity toModel(ActivityRequestDTO dto) {
        Activity activity = new Activity();
        Teacher teacher = teacherService.getTeacherById(dto.getTeacherId());
        SchoolClass schoolClass = schoolClassService.getSchoolClassById(dto.getSchoolClassId());
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
        ActivityResponseDTO dto = new ActivityResponseDTO();
        dto.setId(activity.getId());
        dto.setTitle(activity.getTitle());
        dto.setDescription(activity.getDescription());
        dto.setDeadline(activity.getDeadline()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setCreationDate(activity.getCreationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setMaxScore(activity.getMaxScore());
        dto.setTeacherId(activity.getTeacher().getId());
        dto.setSchoolClassId(activity.getSchoolClass().getId());
        return dto;
    }

    public List<ActivityResponseDTO> toListDTO(List<Activity> activityList) {
        List<ActivityResponseDTO> dtoList = new ArrayList<>();
        for (Activity activity : activityList) {
            dtoList.add(toDTO(activity));
        }
        return dtoList;
    }

    public Activity updateModelFromDTO(ActivityRequestDTO dto, Long id) {
        Activity activity = activityService.getActivityById(id);
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
                    teacherService.getTeacherById(dto.getTeacherId())
            );
        }
        if (dto.getSchoolClassId() != null) {
            activity.setSchoolClass(
                    schoolClassService.getSchoolClassById(dto.getSchoolClassId())
            );
        }
        return activity;
    }
}
