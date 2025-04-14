package com.AdithyaUrs.CRUDdemo.dao;

import com.AdithyaUrs.CRUDdemo.Entity.Employee;
import java.util.List;

public interface EmployeeIDao
{
    List<Employee> findALl();
    Employee findById(int theId);
    Employee save(Employee theEmployee);
    void deletedById(int theid);
}
