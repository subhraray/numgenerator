package com.numbergenerator.Service;

import com.numbergenerator.Model.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee updateEmployeeInformation(Employee employee);

    public void deleteEmployeeInfo(Integer id);

    public Employee saveEmployeeData(Employee employee);

    public List<Employee> findByName(String name);

}
