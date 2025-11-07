package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivityCorrectionRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.ActivityMapper;
import com.ds3c.tcc.ApiTcc.mapper.ActivitySubmissionMapper;
import com.ds3c.tcc.ApiTcc.model.Activity;
import com.ds3c.tcc.ApiTcc.model.ActivitySubmission;
import com.ds3c.tcc.ApiTcc.repository.ActivityRepository;
import com.ds3c.tcc.ApiTcc.repository.ActivitySubmissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService extends CRUDService<Activity, Long> {
    private final ActivityRepository activityRepository;
    private final ActivitySubmissionRepository activitySubmissionRepository;
    private final ActivityMapper activityMapper;
    private final ActivitySubmissionMapper activitySubmissionMapper;
    private final LocalStorageService localStorageService;

    @Lazy
    public ActivityService(
            ActivityRepository activityRepository,
            ActivitySubmissionRepository activitySubmissionRepository,
            ActivityMapper activityMapper,
            ActivitySubmissionMapper activitySubmissionMapper, LocalStorageService localStorageService) {
        super(Activity.class, activityRepository);
        this.activityRepository = activityRepository;
        this.activitySubmissionRepository = activitySubmissionRepository;
        this.activityMapper = activityMapper;
        this.activitySubmissionMapper = activitySubmissionMapper;
        this.localStorageService = localStorageService;
    }

    public ActivitySubmission findSubmissionById(Long id) {
        return activitySubmissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The activity submission with id: "+id+" was not found."));
    }

    public List<Activity> findAllBySchoolClass(Long schoolClassId) {
        return activityRepository.findAllBySchoolClassId(schoolClassId);
    }

    public List<ActivitySubmission> findAllSubmissionByStudent(Long studentId) {
        return activitySubmissionRepository.findAllByStudentId(studentId);
    }

    public List<ActivitySubmission> findAllSubmissionByActivity(Long activityId) {
        return activitySubmissionRepository.findAllByActivityId(activityId);
    }

    public Activity create(ActivityRequestDTO dto) {
        return save(activityMapper.toEntity(dto));
    }

    public Activity update(ActivityRequestDTO dto, Long id) {
        return save(activityMapper.updateEntityFromDTO(dto, id));
    }

    public ActivitySubmission submitActivity(
            ActivitySubmissionRequestDTO dto,
            Long activityId) {
        Activity activity = findById(activityId);

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

        ActivitySubmission submission = activitySubmissionMapper.toEntity(dto, activityId);

        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            submission.setFileUrl(localStorageService.saveFile(
                    dto.getFile(), "/activity/"+activityId+"/submission/student/"+dto.getStudentId())+"/"+dto.getFile().getOriginalFilename());
        }

        return activitySubmissionRepository.save(submission);
    }
    public ActivitySubmission submitCorrection(
            ActivityCorrectionRequestDTO dto, Long submissionId) {
        ActivitySubmission activitySubmission = findSubmissionById(submissionId);
        if (dto.getGrade() > activitySubmission.getActivity().getMaxScore()) {
            throw new RuntimeException(
                    "The grade submited was bigger than the max grade for this activity.");
        }

        activitySubmission.setGrade(dto.getGrade());
        activitySubmission.setComment(dto.getComment());
        activitySubmission.setTeacherId(dto.getTeacherId());
        activitySubmission.setCorrectedAt(LocalDateTime.now());

        return activitySubmissionRepository.save(activitySubmission);
    }
}
