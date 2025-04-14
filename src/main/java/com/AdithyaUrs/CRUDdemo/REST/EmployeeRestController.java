package com.AdithyaUrs.CRUDdemo.REST;

import com.AdithyaUrs.CRUDdemo.Entity.Employee;
import com.AdithyaUrs.CRUDdemo.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController
{
    //DAo
    private EmployeeService employeeService;
    private ObjectMapper objectMapper; // using this cause we need patch mapping

    //constructor injection
    @Autowired
    public EmployeeRestController(EmployeeService employeService , ObjectMapper objectMapper)
    {
        this.employeeService = employeService;
        this.objectMapper = objectMapper;
    }

    //expose "/employees" and return a list of students
    @GetMapping("/employees")
    public List<Employee> findALl()
    {
        return employeeService.findAll();
    }

    @GetMapping("employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId)
    {
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null)
            throw new RuntimeException("Couldn't find the damn Employee");
        return theEmployee;
    }

    //add mapping for POST /employees - add new employee
    @PostMapping("employees")
    public Employee addEmployee(@RequestBody Employee theEmployee)
    {
        //also just in case they send the id in JSCOn ... manaually put iyt as 0.
        //this is to force a save of new item rather than update the db by mistake
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee)
    {
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for PATCH /employees/{employeesID} - partial updates
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId , @RequestBody Map<String,Object> patchPayLoad)
    {
        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if null
        if(tempEmployee == null)
            throw new RuntimeException("Didn't find the guy u need");

        // throw exception if req body conatins the "id" key
        if(patchPayLoad.containsKey("id"))
            throw new RuntimeException("Employees id is mentioned , this is not allowed");

        Employee patchEmployee = apply(patchPayLoad,tempEmployee);
        Employee dbEmployee = employeeService.save(patchEmployee);
        return dbEmployee;
    }

    private Employee apply (Map<String,Object> patchPayload, Employee tempEmployee)
    {
        //convert emp obj to a JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        //conert patch payload to json obj node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        //merege updates
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }

    //add mapping for DEELETE /employees/{employeeID} - delete employee
    @DeleteMapping("employees/{employeeId}")
    public String deleteEmployeById(@PathVariable int employeeId)
    {
        Employee tempEmployee = employeeService.findById(employeeId);

        if(tempEmployee == null)
            throw new RuntimeException("The employee id doesnot exist :" + employeeId);

        employeeService.deletedById(employeeId);
        return "This id has been deleted :" + employeeId;
    }

}
