package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.SchoolSubject.SchoolSubjectRequestDTO;
import com.ds3c.tcc.ApiTcc.mapper.SchoolSubjectMapper;
import com.ds3c.tcc.ApiTcc.model.SchoolSubject;
import com.ds3c.tcc.ApiTcc.repository.SchoolSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SchoolSubjectService extends CRUDService<SchoolSubject, Long> {
    private final SchoolSubjectMapper schoolSubjectMapper;

    @Autowired
    @Lazy
    public SchoolSubjectService(
            SchoolSubjectRepository schoolSubjectRepository,
            SchoolSubjectMapper schoolSubjectMapper) {
        super(schoolSubjectRepository);
        this.schoolSubjectMapper = schoolSubjectMapper;
    }

    public SchoolSubject create(SchoolSubjectRequestDTO dto) {
        return save(schoolSubjectMapper.toEntity(dto));
    }

    public SchoolSubject update(SchoolSubjectRequestDTO dto, Long id) {
        return save(schoolSubjectMapper.updateEntityFromDTO(dto, id));
    }
}
