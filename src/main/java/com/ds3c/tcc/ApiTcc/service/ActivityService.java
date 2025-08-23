package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.GradeSubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.exception.ActivityNotFoundException;
import com.ds3c.tcc.ApiTcc.exception.ActivitySubmissionNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.ActivityMapper;
import com.ds3c.tcc.ApiTcc.mapper.ActivitySubmissionMapper;
import com.ds3c.tcc.ApiTcc.model.Activity;
import com.ds3c.tcc.ApiTcc.model.ActivitySubmission;
import com.ds3c.tcc.ApiTcc.repository.ActivityRepository;
import com.ds3c.tcc.ApiTcc.repository.ActivitySubmissionRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivitySubmissionRepository activitySubmissionRepository;
    private final ActivityMapper activityMapper;
    private final ActivitySubmissionMapper activitySubmissionMapper;

    @Lazy
    public ActivityService(ActivityRepository activityRepository,
                           ActivitySubmissionRepository activitySubmissionRepository,
                           ActivityMapper activityMapper,
                           ActivitySubmissionMapper activitySubmissionMapper) {
        this.activityRepository = activityRepository;
        this.activitySubmissionRepository = activitySubmissionRepository;
        this.activityMapper = activityMapper;
        this.activitySubmissionMapper = activitySubmissionMapper;
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException(id));
    }

    public ActivitySubmission getActivitySubmissionById(Long id) {
        return activitySubmissionRepository.findById(id)
                .orElseThrow(() -> new ActivitySubmissionNotFoundException(id));
    }

    public List<Activity> listActivityBySchoolClassId(Long schoolClassId) {
        return activityRepository.findAllBySchoolClassId(schoolClassId);
    }

    public List<ActivitySubmission> listActivitySubmissionByStudentId(Long studentId) {
        return activitySubmissionRepository.findAllByStudentId(studentId);
    }

    public List<ActivitySubmission> listActivitySubmissionByActivityId(Long activityId) {
        return activitySubmissionRepository.findAllByActivityId(activityId);
    }

    public Activity createActivity(ActivityRequestDTO dto) {
        return activityRepository.save(
                activityMapper.toModel(dto)
        );
    }

    public Activity updateActivity(ActivityRequestDTO dto, Long id) {
        return activityRepository.save(
                activityMapper.updateModelFromDTO(dto, id)
        );
    }

    public void deleteActivity(Long id) {
        Activity activity = getActivityById(id);
        activityRepository.delete(activity);
    }

    public ActivitySubmission submitActivity(ActivitySubmissionRequestDTO dto, Long activityId) {
        Activity activity = getActivityById(activityId);
        try {
            activitySubmissionRepository.findByActivityIdAndStudentId(
                    activityId, dto.getStudentId()
            );
        } catch (Exception e) {
            throw new RuntimeException("The activity for this student was already submited.");
        }
        if (LocalDate.now().isAfter(activity.getDeadline())) {
            throw new RuntimeException("The deadline for this activity was reached.");
        }
        return activitySubmissionRepository.save(
                activitySubmissionMapper.toModel(dto, activityId)
        );
    }
    public ActivitySubmission submitGrade(GradeSubmissionRequestDTO dto, Long submissionId) {
        ActivitySubmission activitySubmission = getActivitySubmissionById(submissionId);
        if (dto.getGrade() > activitySubmission.getActivity().getMaxScore()) {
            throw new RuntimeException("The grade submited was bigger than the max grade for this activity.");
        }
        activitySubmission.setGrade(dto.getGrade());
        return activitySubmissionRepository.save(activitySubmission);
    }
}
