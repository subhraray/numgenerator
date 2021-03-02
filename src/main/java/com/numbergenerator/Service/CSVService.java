package com.numbergenerator.Service;

import com.numbergenerator.Dao.EmployeeRepo;
import com.numbergenerator.Model.Employee;
import com.numbergenerator.helper.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    EmployeeRepo repository;

    public void save(MultipartFile file) {
        try {
            List<Employee> employees = CSVHelper.csvToEmployeeData(file.getInputStream());
            repository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Employee> getEmployees() {
        return repository.findAll();
    }
}