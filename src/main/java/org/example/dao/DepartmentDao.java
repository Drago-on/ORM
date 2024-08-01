package org.example.dao;

import org.example.Department;

import java.util.List;

public interface DepartmentDao {
    void create(Department department);
    Department get(long id);
    List<Department> getAll();
    void update(Department department);
    void delete(Department department);
}
