package org.example;

import org.example.dao.DepartmentDao;
import org.example.dao.EmployeeDao;
import org.example.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        DepartmentDao departmentDAO = new DepartmentDAOImpl();
        EmployeeDao employeeDAO = new EmployeeDAOImpl();

        Department department = new Department();
        department.setName("Dep 1");
        departmentDAO.create(department);

        Employee employee = new Employee();
        employee.setName("Emp 1");
        employee.setDepartment(department);
        employeeDAO.create(employee);

        Department readDepartment = departmentDAO.get(department.getId());
        System.out.println("Department Name: " + readDepartment.getName());

        readDepartment.setName("Updated Dep 1");
        departmentDAO.update(readDepartment);

        Department updatedDepartment = departmentDAO.get(readDepartment.getId());
        System.out.println("Updated Department Name: " + updatedDepartment.getName());

        departmentDAO.delete(updatedDepartment);

        HibernateUtil.shutdown();
    }
}