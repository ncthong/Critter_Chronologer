package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.Exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Not found employee with id " + id));
    }
    public void updateDayAvailable(Set<DayOfWeek> daysAvailable, Long id){
        Employee employee = this.getEmployeeById(id);
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }
    public Employee save(Employee employee){
        return this.employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    public List<Employee> findByDaysAvailable(DayOfWeek daysAvailable){
        return employeeRepository.findByDaysAvailable(daysAvailable);
    }
}
