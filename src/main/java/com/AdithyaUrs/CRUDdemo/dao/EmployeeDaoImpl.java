package com.AdithyaUrs.CRUDdemo.dao;

import com.AdithyaUrs.CRUDdemo.Entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeIDao
{
    //Entity manager
    private EntityManager entityManager;

    //constructor Injection
    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findALl()
    {
        //create query
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee", Employee.class);

        //execute query and get res
        List<Employee> employees = theQuery.getResultList();

        //return res
        return employees;
    }

    @Override
    public Employee findById(int theId)
    {
        //get employee
        Employee theEmployee = entityManager.find(Employee.class,theId);

        //retrun
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee)
    {
        Employee dbEmployee = entityManager.merge(theEmployee); //if id == 0 , it adds to db else it updates current emp in db
        return dbEmployee;
    }

    @Override
    public void deletedById(int theid)
    {
        //find the emp
        Employee theEmployee = entityManager.find(Employee.class,theid);

        //delete employee
        entityManager.remove(theEmployee);
    }
}
