package model;

import java.util.Locale;

public abstract class Employee {

    private final String empID;
    private String empName;
    private double baseSalary;

    public Employee(String empID, String empName, double baseSalary) {
        if (empID == null || empName == null || empID.isEmpty() || empName.isEmpty()) {
            throw new IllegalArgumentException("Employee ID and name cannot be null or empty.");
        }
        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Base salary must be positive.");
        }
        this.empID = empID;
        this.empName = empName;
        this.baseSalary = baseSalary;
    }

    public String getEmpID() {
        return empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Base salary must be positive.");
        }
        this.baseSalary = baseSalary;
    }

    @Override
    public String toString() {

        return String.format(Locale.US, "%s_%s_%.1f", empID, empName, baseSalary);
    }

    public abstract double getSalary();
}
