package com.numbergenerator.Dao;

import com.numbergenerator.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    public List<Employee> findByName(String name);
}
