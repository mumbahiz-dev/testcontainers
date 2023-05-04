package com.testcontainers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public ResponseDTO create(RequestDTO requestDTO){
        Employee employee = Employee.builder()
                .name(requestDTO.name())
                .age(requestDTO.age())
                .gender(requestDTO.gender())
                .nip(requestDTO.nip())
                .build();

        employeeRepository.saveAndFlush(employee);

        return new ResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getNip(),
                employee.getGender(),
                employee.getAge()
        );
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
}
