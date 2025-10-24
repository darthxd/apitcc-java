package com.ds3c.tcc.ApiTcc.controller;

import com.ds3c.tcc.ApiTcc.dto.ClassSchedule.ClassScheduleRequestDTO;
import com.ds3c.tcc.ApiTcc.dto.ClassSchedule.ClassScheduleResponseDTO;
import com.ds3c.tcc.ApiTcc.mapper.ClassScheduleMapper;
import com.ds3c.tcc.ApiTcc.service.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classschedule")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;
    private final ClassScheduleMapper classScheduleMapper;

    @PostMapping
    public ClassScheduleResponseDTO create(@RequestBody ClassScheduleRequestDTO dto) {
        return classScheduleMapper.toDTO(classScheduleService.create(dto));
    }

    @PutMapping("/{id}")
    public ClassScheduleResponseDTO update(
            @RequestBody ClassScheduleRequestDTO dto,
            @PathVariable("id") Long id) {
        return classScheduleMapper.toDTO(classScheduleService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        classScheduleService.delete(classScheduleService.findById(id));
    }

    @GetMapping
    public List<ClassScheduleResponseDTO> findAll() {
        return classScheduleService.findAll()
                .stream().map(classScheduleMapper::toDTO).toList();
    }

    @GetMapping("/class/{id}")
    public List<ClassScheduleResponseDTO> findAllBySchoolClass(@PathVariable("id") Long classId) {
        return classScheduleService.findAllBySchoolClass(classId)
                .stream().map(classScheduleMapper::toDTO).toList();
    }

    @GetMapping("/class/{id}/day/{day}")
    public List<ClassScheduleResponseDTO> findAllBySchoolClassAndDay(
            @PathVariable("id") Long classId,
            @PathVariable("day") String day) {
        return classScheduleService.findAllBySchoolClassAndDay(classId, day)
                .stream().map(classScheduleMapper::toDTO).toList();
    }

    @GetMapping("/teacher/{id}")
    public List<ClassScheduleResponseDTO> findAllByTeacher(@PathVariable("id") Long teacherId) {
        return classScheduleService.findAllByTeacher(teacherId)
                .stream().map(classScheduleMapper::toDTO).toList();
    }
}
