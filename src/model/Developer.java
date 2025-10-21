package model;

import java.util.List;

public class Developer extends Employee {

    private List<String> programmingLanguages;
    private String teamName;
    private int yearsOfExperience;

    public Developer(String empID, String empName, double baseSalary,
            List<String> programmingLanguages, String teamName, int yearsOfExperience) {
        super(empID, empName, baseSalary);
        this.programmingLanguages = programmingLanguages;
        this.teamName = teamName;
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public double getSalary() {
        double bonus;
        if (yearsOfExperience >= 10) {
            bonus = 2_000_000;
        } else if (yearsOfExperience >= 3) {
            bonus = 1_000_000;
        } else {
            bonus = 0;
        }
        return getBaseSalary() + yearsOfExperience * bonus;
    }

    @Override
    public String toString() {
        String langs = String.join(",", programmingLanguages);

        return super.toString() + "_" + langs + "_" + this.teamName + "_" + this.yearsOfExperience;
    }

    public void updateDetails(String newName, double newBaseSal, String newTeamName, List<String> newLanguages,
            int newExpYear) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.setEmpName(newName);
        }
        if (newBaseSal != -1 && newBaseSal > 0) {
            this.setBaseSalary(newBaseSal);
        }

        if (newTeamName != null && !newTeamName.trim().isEmpty()) {
            this.setTeamName(newTeamName);
        }
        if (newLanguages != null) {
            this.setProgrammingLanguages(newLanguages);
        }
        if (newExpYear != -1 && newExpYear >= 0) {
            this.setYearsOfExperience(newExpYear);
        }
    }
}
