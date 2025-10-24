package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.ClassSchedule.ClassScheduleRequestDTO;
import com.ds3c.tcc.ApiTcc.enums.DayOfWeekEnum;
import com.ds3c.tcc.ApiTcc.mapper.ClassScheduleMapper;
import com.ds3c.tcc.ApiTcc.model.ClassSchedule;
import com.ds3c.tcc.ApiTcc.repository.ClassScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleService extends CRUDService<ClassSchedule, Long> {
    private final ClassScheduleRepository classScheduleRepository;
    private final ClassScheduleMapper classScheduleMapper;

    public ClassScheduleService(
            ClassScheduleRepository classScheduleRepository, ClassScheduleMapper classScheduleMapper) {
        super(ClassSchedule.class, classScheduleRepository);
        this.classScheduleRepository = classScheduleRepository;
        this.classScheduleMapper = classScheduleMapper;
    }

    public ClassSchedule create(ClassScheduleRequestDTO dto) {
        return save(classScheduleMapper.toEntity(dto));
    }

    public ClassSchedule update(ClassScheduleRequestDTO dto, Long id) {
        return save(classScheduleMapper.updateEntityFromDTO(dto, id));
    }

    public List<ClassSchedule> findAllBySchoolClass(Long classId) {
        return classScheduleRepository.findAllBySchoolClassId(classId);
    }

    public List<ClassSchedule> findAllBySchoolClassAndDay(Long classId, String dayOfWeek) {
        return classScheduleRepository.findAllBySchoolClassIdAndDayOfWeek(
                classId, DayOfWeekEnum.valueOf(dayOfWeek.toUpperCase()) );
    }

    public List<ClassSchedule> findAllByTeacher(Long teacherId) {
        return classScheduleRepository.findAllByTeacherId(teacherId);
    }
}
