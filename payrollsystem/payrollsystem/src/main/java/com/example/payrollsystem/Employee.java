package com.example.payrollsystem;

public class Employee {
    private int id;
    private String name;
    private String department;
    private String position;
    private double basicSalary;
    private double workingHours;

    public Employee(int id, String name, String department, String position, double basicSalary, double workingHours) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.basicSalary = basicSalary;
        this.workingHours = workingHours;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getBasicSalary() { return basicSalary; }
    public double getWorkingHours() { return workingHours; }
}