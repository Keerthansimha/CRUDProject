package com.AdithyaUrs.CRUDdemo.Service;

import com.AdithyaUrs.CRUDdemo.Entity.Employee;
import java.util.List;

public interface EmployeeService
{
    List<Employee> findAll();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    void deletedById(int theid);
}
