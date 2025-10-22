package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.Activity.ActivityResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivitySubmissionResponseDTO;
import com.ds3c.tcc.ApiTcc.dto.ActivitySubmission.ActivityCorrectionRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.ActivityMapper;
import com.ds3c.tcc.ApiTcc.mapper.ActivitySubmissionMapper;
import com.ds3c.tcc.ApiTcc.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    private final ActivitySubmissionMapper activitySubmissionMapper;

    public ActivityController(
            ActivityService activityService,
            ActivityMapper activityMapper,
            ActivitySubmissionMapper activitySubmissionMapper) {
        this.activityService = activityService;
        this.activityMapper = activityMapper;
        this.activitySubmissionMapper = activitySubmissionMapper;
    }

    @GetMapping("/{id}")
    public ActivityResponseDTO getActivityById(@PathVariable("id") Long id) {
        return activityMapper.toDTO(
                activityService.getById(id)
        );
    }

    @GetMapping("/schoolclass/{id}")
    public List<ActivityResponseDTO> listActivityBySchoolClassId(
            @PathVariable("id") Long id) {
        return activityService.listBySchoolClass(id)
                .stream().map(activityMapper::toDTO).toList();
    }

    @PostMapping
    public ActivityResponseDTO createActivity(@RequestBody @Valid ActivityRequestDTO dto) {
        return activityMapper.toDTO(
                activityService.create(dto)
        );
    }

    @PutMapping("/{id}")
    public ActivityResponseDTO updateActivity(
            @RequestBody @Valid ActivityRequestDTO dto,
            @PathVariable Long id) {
        return activityMapper.toDTO(
                activityService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable("id") Long id) {
        activityService.delete(id);
    }

    @GetMapping("/submission/{id}")
    public ActivitySubmissionResponseDTO getActivitySubmissionById(
            @PathVariable("id") Long id) {
        return activitySubmissionMapper.toDTO(
                activityService.getSubmissionById(id)
        );
    }

    @GetMapping("/submission/student/{id}")
    public List<ActivitySubmissionResponseDTO> listActivitySubmissionByStudentId(
            @PathVariable("id") Long studentId) {
        return activityService.listSubmissionByStudent(studentId)
                .stream().map(activitySubmissionMapper::toDTO).toList();
    }

    @PostMapping("/submission/{id}")
    public ActivitySubmissionResponseDTO submitActivity(
            @RequestBody @Valid ActivitySubmissionRequestDTO dto,
            @PathVariable("id") Long activityId) {
        return activitySubmissionMapper.toDTO(
                activityService.submitActivity(dto, activityId)
        );
    }

    @PostMapping("/submission/{id}/grade")
    public ActivitySubmissionResponseDTO submitGrade(
            @RequestBody @Valid ActivityCorrectionRequestDTO dto,
            @PathVariable("id") Long submissionId) {
        return activitySubmissionMapper.toDTO(
                activityService.submitCorrection(dto, submissionId)
        );
    }

    @GetMapping("/{id}/submission")
    public List<ActivitySubmissionResponseDTO> listActivitySubmissionByActivityId(
            @PathVariable("id") Long activityId) {
        return activityService.listSubmissionByActivity(activityId)
                .stream().map(activitySubmissionMapper::toDTO).toList();
    }
}
