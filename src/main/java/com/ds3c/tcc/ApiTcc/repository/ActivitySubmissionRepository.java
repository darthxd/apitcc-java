package com.ds3c.tcc.ApiTcc.repository;

import com.ds3c.tcc.ApiTcc.model.ActivitySubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivitySubmissionRepository extends JpaRepository<ActivitySubmission, Long> {
    Optional<ActivitySubmission> findByActivityIdAndStudentId(Long activityId, Long studentId);
    List<ActivitySubmission> findAllByStudentId(Long studentId);
    List<ActivitySubmission> findAllByActivityId(Long activityId);
}
