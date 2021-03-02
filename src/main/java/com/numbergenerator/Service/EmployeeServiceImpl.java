package com.numbergenerator.Service;

import com.numbergenerator.Dao.EmployeeRepo;
import com.numbergenerator.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public Employee updateEmployeeInformation(Employee employee) {
        if (!employeeRepo.existsById(employee.getId())) {
            throw new EntityExistsException("There is no  existing entity with such ID in the database.");
        }
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployeeInfo(Integer id) {
        employeeRepo.deleteById(Long.valueOf(id));
    }

    @Override
    public Employee saveEmployeeData(Employee employee) {
        if (employeeRepo.existsById(employee.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> findByName(String name) {
        return employeeRepo.findByName(name);
    }
}
