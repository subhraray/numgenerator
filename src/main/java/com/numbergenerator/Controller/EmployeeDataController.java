package com.numbergenerator.Controller;

import com.numbergenerator.Constants.Constants;
import com.numbergenerator.Model.Employee;
import com.numbergenerator.Service.CSVService;
import com.numbergenerator.Service.EmployeeService;
import com.numbergenerator.helper.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/api")


public class EmployeeDataController {
    @Autowired
    CSVService fileService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/csv/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/csv/upload")
    public ResponseEntity<String> uploadEmployeesData(@RequestParam("file") MultipartFile file) {

        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);
                message = Constants.FILE_UPLOAD_SUCCESS_MSG + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = Constants.FILE_UPLOAD_ERROR_MSG + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = Constants.FILE_UPLOAD_BAD_REQUEST_MSG;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = fileService.getEmployees();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployeeInformation(@RequestBody Employee emp) {

        try {
            Employee result = employeeService.updateEmployeeInformation(emp);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/employee", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployeeInfo(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> findByName(@RequestParam(required = true) String name) {
        try {
            List<Employee> employees = employeeService.findByName(name);
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws URISyntaxException {
        try {
            Employee result = employeeService.saveEmployeeData(employee);
            return ResponseEntity.created(new URI("/api/employee/" + result.getId())).body(result);
        } catch (EntityExistsException e) {
            return new ResponseEntity<Employee>(HttpStatus.CONFLICT);
        }
    }


}
