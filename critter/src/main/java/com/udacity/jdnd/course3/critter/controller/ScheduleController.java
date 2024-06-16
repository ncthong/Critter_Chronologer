package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.Exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.services.ConvertDtoService;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ConvertDtoService convertDtoService;
    private final ScheduleRepository scheduleRepository;
    private final CustomerService customerService;

    public ScheduleController(ScheduleService scheduleService, ConvertDtoService convertDtoService, ScheduleRepository scheduleRepository, CustomerService customerService) {
        this.scheduleService = scheduleService;
        this.convertDtoService = convertDtoService;
        this.scheduleRepository = scheduleRepository;
        this.customerService = customerService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDtoService.convertDTOToSchedule(scheduleDTO);
        schedule = scheduleService.save(schedule);
        return convertDtoService.convertScheduleToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedule();
        return convertDtoService.convertListScheduleToDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
//    public List<ScheduleDTO> findSchedulesByPetsId(@PathVariable Long petId) {
//        List<Schedule> schedules = scheduleService.findByPetsId(petId);
//        if (schedules == null || schedules.isEmpty()) {
//            throw new ScheduleNotFoundException("No schedules found for pet ID: " + petId);
//        }
//        return convertDtoService.convertListScheduleToDTO(schedules);
//    }
    public List<ScheduleDTO> findSchedulesByPetsId(@PathVariable Long petId) {
        List<Schedule> schedules = scheduleService.findByPetsId(petId);
        if (schedules == null || schedules.isEmpty()) {
            throw new ScheduleNotFoundException("No schedules found for pet ID: " + petId);
        }
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertDtoService.convertScheduleToDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findByEmployeesId(employeeId);
        if (schedules == null || schedules.isEmpty()) {
            throw new ScheduleNotFoundException("No schedules found for employee ID: " + employeeId);
        }
        return convertDtoService.convertListScheduleToDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        if(customer.getPets() != null){
            for(Pet pet : customer.getPets()){
                List<Schedule> schedulesByPetsId = scheduleService.findByPetsId(pet.getId());
                scheduleDTOs.addAll(convertDtoService.convertListScheduleToDTO(schedulesByPetsId));
            }
        }
        return scheduleDTOs;
    }
}
