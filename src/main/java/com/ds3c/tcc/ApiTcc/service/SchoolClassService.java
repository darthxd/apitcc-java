package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolClass.SchoolClassResponseDTO;
import com.ds3c.tcc.ApiTcc.exception.SchoolClassNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.SchoolClassMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolClass;
import com.ds3c.tcc.ApiTcc.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;

    @Autowired
    public SchoolClassService(
            SchoolClassRepository schoolClassRepository,
            SchoolClassMapper schoolClassMapper) {
        this.schoolClassRepository = schoolClassRepository;
        this.schoolClassMapper = schoolClassMapper;
    }

    public List<SchoolClassResponseDTO> listSchoolClassDTO() {
        return schoolClassMapper.toListDTO(schoolClassRepository.findAll());
    }

    public SchoolClass getSchoolClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
    }

    public SchoolClassResponseDTO getSchoolClassDTOById(Long id) {
        return schoolClassMapper.toDTO(schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id)));
    }
}
