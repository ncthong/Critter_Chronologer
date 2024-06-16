package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.Exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.Exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.classify.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.ConvertDtoService;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final EmployeeService employeeService;

    private final CustomerService customerService;

    private final PetService petService;

    private final ConvertDtoService convertDtoService;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertDtoService.convertDTOToCustomer(customerDTO);
        customer = customerService.save(customer);
        return convertDtoService.convertCustomerToDTO(customer);
    }

    @GetMapping("/customer")
//    public List<CustomerDTO> getAllCustomers(){
//        List<CustomerDTO> customerDTOS = new ArrayList<>();
//        List<Customer> customers = this.customerService.findAllCustomer();
//        for(Customer customer:customers){
//            customerDTOS.add(convertCustomerToDTO(customer));
//        }
//        return customerDTOS;
//    }
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.findAllCustomer();
        return customers.stream()
                .map(convertDtoService::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        if (pet == null || pet.getCustomer() == null) {
            throw new PetNotFoundException("Pet or Customer not found for petId: " + petId);
        }
        return convertDtoService.convertCustomerToDTO(pet.getCustomer());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDtoService.convertDTOToEmployee(employeeDTO);
        employee = employeeService.save(employee);
        return convertDtoService.convertEmployeeToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }
        return convertDtoService.convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/availability/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        if (employeeService.getEmployeeById(employeeId) == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }
        employeeService.updateDayAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> checkAvailability(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        Set<EmployeeSkill> employeeSkills = employeeRequestDTO.getSkills();
        DayOfWeek daysAvailable = employeeRequestDTO.getDate().getDayOfWeek();
        List<Employee> employees = employeeService.findByDaysAvailable(daysAvailable);

        return employees.stream()
                .filter(employee -> employee.getSkills().containsAll(employeeSkills))
                .map(convertDtoService::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

}
