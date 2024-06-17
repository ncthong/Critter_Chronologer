package com.udacity.jdnd.course3.critter.services;


import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
    public List<Schedule> findAllSchedule() {
        return scheduleRepository.findAll();
    }
    public Schedule findScheduleById(long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule is not found with ID: " + id));
    }
    public List<Schedule> findByEmployeesId(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.getScheduleByEmployees_Id(employeeId);
        if (schedules == null || schedules.isEmpty()) {
            throw new NotFoundException("No schedules found for employee ID: " + employeeId);
        }
        return schedules;
    }
    public List<Schedule> findByPetsId(Long petId) {
        List<Schedule> schedules = scheduleRepository.getScheduleByPets_Id(petId);
        if (schedules == null || schedules.isEmpty()) {
            throw new NotFoundException("No schedules found for Pet ID: " + petId);
        }
        return schedules;
    }
}
