package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectResponseDTO;
import com.ds3c.tcc.ApiTcc.exception.SchoolSubjectNotFoundException;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.repository.SchoolSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolSubjectService {
    private final SchoolSubjectRepository schoolSubjectRepository;

    @Autowired
    public SchoolSubjectService(
            SchoolSubjectRepository schoolSubjectRepository) {
        this.schoolSubjectRepository = schoolSubjectRepository;
    }

    public SchoolSubject getSchoolSubjectById(Long id) {
        return schoolSubjectRepository.findById(id)
                .orElseThrow(() -> new SchoolSubjectNotFoundException(id));
    }
}
