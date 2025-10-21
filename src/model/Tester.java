package model;

import java.util.Locale;

public class Tester extends Employee {

    private double bonusRate;
    private String type;

    public Tester(String empID, String empName, double baseSalary,
            double bonusRate, String type) {
        super(empID, empName, baseSalary);
        this.bonusRate = bonusRate;
        this.type = type;
    }

    public double getBonusRate() {
        return bonusRate;
    }

    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double getSalary() {
        return getBaseSalary() + (bonusRate * getBaseSalary());
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s_%s_%.0f_%.2f_%s",
                getEmpID(),
                getEmpName(),
                getBaseSalary(),
                bonusRate,
                type);
    }

    public void updateDetails(String newName, double newBaseSal, double newBonusRate, String newType) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.setEmpName(newName);
        }
        if (newBaseSal != -1 && newBaseSal > 0) {
            this.setBaseSalary(newBaseSal);
        }
        if (newBonusRate != -1 && newBonusRate >= 0) {
            this.setBonusRate(newBonusRate);
        }
        if (newType != null && !newType.trim().isEmpty()) {
            this.setType(newType);
        }
    }
}
