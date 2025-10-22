package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectRequestDTO;
import com.ds3c.tcc.ApiTcc.exception.SchoolSubjectNotFoundException;
import com.ds3c.tcc.ApiTcc.mapper.SchoolSubjectMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.repository.SchoolSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SchoolSubjectService {
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final SchoolSubjectMapper schoolSubjectMapper;

    @Autowired
    @Lazy
    public SchoolSubjectService(
            SchoolSubjectRepository schoolSubjectRepository,
            SchoolSubjectMapper schoolSubjectMapper) {
        this.schoolSubjectRepository = schoolSubjectRepository;
        this.schoolSubjectMapper = schoolSubjectMapper;
    }

    public SchoolSubject create(SchoolSubjectRequestDTO dto) {
        return schoolSubjectRepository.save(
                schoolSubjectMapper.toEntity(dto)
        );
    }

    public SchoolSubject getById(Long id) {
        return schoolSubjectRepository.findById(id)
                .orElseThrow(() -> new SchoolSubjectNotFoundException(id));
    }

    public List<SchoolSubject> list() {
        return schoolSubjectRepository.findAll();
    }

    public SchoolSubject update(SchoolSubjectRequestDTO dto,
                                Long id) {
        return schoolSubjectRepository.save(
                schoolSubjectMapper.updateEntityFromDTO(dto, id));
    }

    public void delete(Long id) {
        SchoolSubject schoolSubject = getById(id);
        schoolSubjectRepository.delete(schoolSubject);
    }

    public List<SchoolSubject> listById(Set<Long> idSet) {
        return schoolSubjectRepository.findAllById(idSet);
    }
}
