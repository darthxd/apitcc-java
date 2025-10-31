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
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    private final ActivitySubmissionMapper activitySubmissionMapper;

    @GetMapping("/{id}")
    public ActivityResponseDTO findById(@PathVariable("id") Long id) {
        return activityMapper.toDTO(
                activityService.findById(id)
        );
    }

    @GetMapping("/schoolclass/{id}")
    public List<ActivityResponseDTO> findAllBySchoolClass(
            @PathVariable("id") Long id) {
        return activityService.findAllBySchoolClass(id)
                .stream().map(activityMapper::toDTO).toList();
    }

    @PostMapping
    public ActivityResponseDTO create(@RequestBody @Valid ActivityRequestDTO dto) {
        return activityMapper.toDTO(
                activityService.create(dto)
        );
    }

    @PutMapping("/{id}")
    public ActivityResponseDTO update(
            @RequestBody @Valid ActivityRequestDTO dto,
            @PathVariable Long id) {
        return activityMapper.toDTO(
                activityService.update(dto, id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        activityService.delete(activityService.findById(id));
    }

    @GetMapping("/submission/{id}")
    public ActivitySubmissionResponseDTO findSubmissionById(
            @PathVariable("id") Long id) {
        return activitySubmissionMapper.toDTO(
                activityService.findSubmissionById(id)
        );
    }

    @GetMapping("/submission/student/{id}")
    public List<ActivitySubmissionResponseDTO> findAllSubmissionByStudent(
            @PathVariable("id") Long studentId) {
        return activityService.findAllSubmissionByStudent(studentId)
                .stream().map(activitySubmissionMapper::toDTO).toList();
    }

    @PostMapping(value = "/submission/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActivitySubmissionResponseDTO submitActivity(
            @ModelAttribute @Valid ActivitySubmissionRequestDTO dto,
            @PathVariable("id") Long activityId) throws IOException {
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
    public List<ActivitySubmissionResponseDTO> findAllSubmissionByActivity(
            @PathVariable("id") Long activityId) {
        return activityService.findAllSubmissionByActivity(activityId)
                .stream().map(activitySubmissionMapper::toDTO).toList();
    }
}
