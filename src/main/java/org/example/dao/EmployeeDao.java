package org.example.dao;

import org.example.Employee;

import java.util.List;

public interface EmployeeDao {
    void create(Employee employee);
    Employee get(long id);
    List<Employee> getAll();
    void update(Employee employee);
    void delete(Employee employee);
}
