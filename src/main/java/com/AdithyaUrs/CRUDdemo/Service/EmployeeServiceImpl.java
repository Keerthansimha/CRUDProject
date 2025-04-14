package com.AdithyaUrs.CRUDdemo.Service;

import com.AdithyaUrs.CRUDdemo.Entity.Employee;
import com.AdithyaUrs.CRUDdemo.dao.EmployeeIDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeServiceImpl implements EmployeeService
{
    private EmployeeIDao employeeIDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeIDao employeeIDao)
    {
        this.employeeIDao = employeeIDao;
    }

    @Override
    public List<Employee> findAll()
    {
        return employeeIDao.findALl();
    }

    @Override
    public Employee findById(int theId)
    {
        return employeeIDao.findById(theId);
    }

    @Override @Transactional
    public Employee save(Employee theEmployee)
    {
        return employeeIDao.save(theEmployee);
    }

    @Override @Transactional
    public void deletedById(int theid)
    {
        employeeIDao.deletedById(theid);
    }
}
