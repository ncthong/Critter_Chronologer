package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConvertDtoService {

    private final CustomerService customerService;
    private final PetService petService;
    private final EmployeeService employeeService;

    public PetDTO convertPetToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    public Pet convertDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.findCustomerById(petDTO.getOwnerId()));
        return pet;
    }
    public EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public Employee convertDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee  = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
    public CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = null;
        if (customer.getPets() != null) {
            petIds = customer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> pets = petIds != null ? petIds.stream()
                .map(petService::getPetById)
                .collect(Collectors.toList()) : Collections.emptyList();
        customer.setPets(pets);
        return customer;
    }
    public ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = schedule.getPets() != null
                ? schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList())
                : null;
        List<Long> employeeIds = schedule.getEmployees() != null
                ? schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList())
                : null;
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }
    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Pet> pets = scheduleDTO.getPetIds() != null
                ? scheduleDTO.getPetIds().stream()
                .map(petService::getPetById)
                .collect(Collectors.toList())
                : null;
        List<Employee> employees = scheduleDTO.getEmployeeIds() != null
                ? scheduleDTO.getEmployeeIds().stream()
                .map(employeeService::getEmployeeById)
                .collect(Collectors.toList())
                : null;
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        return schedule;
    }
    public List<ScheduleDTO> convertListScheduleToDTO(List<Schedule> schedules) {
        return schedules.stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
}
