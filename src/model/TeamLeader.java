package model;

import java.util.List;

public class TeamLeader extends Developer {

    private double bonusRate;

    public TeamLeader(String empID, String empName, double baseSalary,
            List<String> programmingLanguages, String teamName,
            int yearsOfExperience, double bonusRate) {
        super(empID, empName, baseSalary, programmingLanguages, teamName, yearsOfExperience);
        this.bonusRate = bonusRate;
    }

    public double getBonusRate() {
        return bonusRate;
    }

    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    @Override
    public double getSalary() {
        double developerSalary = super.getSalary();
        return developerSalary + (developerSalary * (bonusRate / 100.0));
    }

    @Override
    public String toString() {
        return super.toString() + "_" + this.bonusRate;
    }

    public void updateDetails(String newName, double newBaseSal, String newTeamName, List<String> newLanguages,
            int newExpYear, double newBonusRate) {
        super.updateDetails(newName, newBaseSal, newTeamName, newLanguages, newExpYear);

        if (newBonusRate != -1 && newBonusRate >= 0) {
            this.setBonusRate(newBonusRate);
        }
    }
}
