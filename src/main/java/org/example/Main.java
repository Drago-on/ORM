package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.example.dao.DepartmentDao;
import org.example.dao.EmployeeDao;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DepartmentDao departmentDAO = new DepartmentDAOImpl();
        EmployeeDao employeeDAO = new EmployeeDAOImpl();

        try {
            boolean exit = false;

            while (!exit) {
                System.out.println("Select an option:");
                System.out.println("1. Create Department");
                System.out.println("2. Create Employee");
                System.out.println("3. Update Department");
                System.out.println("4. Delete Department");
                System.out.println("5. Exit");
                int choice = getValidIntInput(scanner, "Enter your choice: ");
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createDepartment(scanner, departmentDAO);
                        break;
                    case 2:
                        createEmployee(scanner, departmentDAO, employeeDAO);
                        break;
                    case 3:
                        updateDepartment(scanner, departmentDAO);
                        break;
                    case 4:
                        deleteDepartment(scanner, departmentDAO);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (HibernateException e) {
            System.err.println("An error occurred while interacting with the database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }

        scanner.close();
    }

    private static void createDepartment(Scanner scanner, DepartmentDao departmentDAO) {
        String depName = getStringInput(scanner, "Enter Department Name: ");
        Department department = new Department();
        department.setName(depName);
        departmentDAO.create(department);
        System.out.println("Department created with ID: " + department.getId());
    }

    private static void createEmployee(Scanner scanner, DepartmentDao departmentDAO, EmployeeDao employeeDAO) {
        String empName = getStringInput(scanner, "Enter Employee Name: ");
        long depId = getValidDepartmentId(scanner, departmentDAO);
        Employee employee = new Employee();
        employee.setName(empName);
        Department empDepartment = departmentDAO.get(depId);
        employee.setDepartment(empDepartment);
        employeeDAO.create(employee);
        System.out.println("Employee created with ID: " + employee.getId());
    }

    private static void updateDepartment(Scanner scanner, DepartmentDao departmentDAO) {
        long updateDepId = getValidDepartmentId(scanner, departmentDAO);
        Department updateDepartment = departmentDAO.get(updateDepId);
        String newDepName = getStringInput(scanner, "Enter new Department Name: ");
        updateDepartment.setName(newDepName);
        departmentDAO.update(updateDepartment);
        System.out.println("Department updated.");
    }

    private static void deleteDepartment(Scanner scanner, DepartmentDao departmentDAO) {
        long deleteDepId = getValidDepartmentId(scanner, departmentDAO);
        Department deleteDepartment = departmentDAO.get(deleteDepId);
        departmentDAO.delete(deleteDepartment);
        System.out.println("Department deleted.");
    }

    private static long getValidDepartmentId(Scanner scanner, DepartmentDao departmentDAO) {
        while (true) {
            long depId = getValidIntInput(scanner, "Enter Department ID: ");
            scanner.nextLine();
            Department department = departmentDAO.get(depId);
            if (department != null) {
                return depId;
            } else {
                System.out.println("Department not found. Please try again.");
            }
        }
    }

    private static int getValidIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Error: Input cannot be empty. Please try again.");
            }
        }
        return input;
    }
}