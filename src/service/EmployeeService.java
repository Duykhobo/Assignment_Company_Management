package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Developer;
import model.Employee;
import model.TeamLeader;
import model.Tester;
import repo.FileHandler;
import utilities.RegexConst;

public class EmployeeService {

    private static final String DATA_FILE = "employees.txt";
    private final ArrayList<Employee> employeeList;

    private final FileHandler<Employee> fileHandler = new FileHandler<Employee>() {
        @Override
        public Employee handleLine(String line) {
            String[] parts = line.split("_");
            try {
                String empID = parts[0];
                String empName = parts[1];
                double baseSal = Double.parseDouble(parts[2]);

                if (empID.matches(RegexConst.REG_TL_ID)) {
                    String teamName = parts[3];
                    List<String> languages = Arrays.asList(parts[4].split(","));
                    int yearsOfExp = Integer.parseInt(parts[5]);
                    double bonusRate = Double.parseDouble(parts[6]);
                    return new TeamLeader(empID, empName, baseSal, languages, teamName, yearsOfExp, bonusRate);

                } else if (empID.matches(RegexConst.REG_DEV_ID)) {
                    String teamName = parts[3];
                    List<String> languages = Arrays.asList(parts[4].split(","));
                    int yearsOfExp = Integer.parseInt(parts[5]);
                    return new Developer(empID, empName, baseSal, languages, teamName, yearsOfExp);

                } else if (empID.matches(RegexConst.REG_TESTER_ID)) {
                    double bonusRate = Double.parseDouble(parts[3]);
                    String testingType = parts[4];
                    return new Tester(empID, empName, baseSal, bonusRate, testingType);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error parsing line, invalid format: " + line);
            }
            return null;
        }
    };

    public EmployeeService() {
        this.employeeList = new ArrayList<>();
        this.loadFromFile(DATA_FILE);
    }

    // ================== CÁC PHƯƠNG THỨC LOGIC NGHIỆP VỤ ==================
    public List<Employee> getAllEmployees() {
        return this.employeeList;
    }

    public void addEmployee(Employee emp) {
        this.employeeList.add(emp);
    }

    public Employee findEmployeeById(String empID) {
        for (Employee emp : employeeList) {
            if (emp.getEmpID().equalsIgnoreCase(empID)) {
                return emp;
            }
        }
        return null;
    }

    public boolean doesTeamHaveLeader(String teamName) {
        for (Employee emp : employeeList) {
            if (emp instanceof TeamLeader) {
                TeamLeader tl = (TeamLeader) emp;
                if (tl.getTeamName().equalsIgnoreCase(teamName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Employee> sortEmployees() {
        List<Employee> list = new ArrayList<>(employeeList);
        Comparator orderBySalaryAndNameDesc = new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                if (emp2.getSalary() == emp1.getSalary()) {
                    return emp2.getEmpName().compareTo(emp1.getEmpName());
                }
                return Double.compare(emp2.getSalary(), emp1.getSalary());

            }
        };
        Collections.sort(list, orderBySalaryAndNameDesc);
        return list;
    }

    // ================== CÁC PHƯƠNG THỨC TÌM KIẾM ==================
    public List<Employee> searchByName(String searchTerm) {
        List<Employee> results = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (emp.getEmpName().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(emp);
            }
        }
        return results;
    }

    public Tester findTesterWithHighestSalary() {
        Tester highestPaidTester = null;
        double maxSalary = -1.0;

        for (Employee emp : employeeList) {
            if (emp instanceof Tester) {
                Tester currentTester = (Tester) emp;
                if (highestPaidTester == null || currentTester.getSalary() > maxSalary) {
                    maxSalary = currentTester.getSalary();
                    highestPaidTester = currentTester;
                }
            }
        }
        return highestPaidTester;
    }

    public List<Developer> searchByProgrammingLanguage(String language) {
        List<Developer> results = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (emp instanceof Developer) {
                Developer dev = (Developer) emp;
                // Vòng lặp bên trong để kiểm tra danh sách ngôn ngữ
                for (String lang : dev.getProgrammingLanguages()) {
                    if (lang.equalsIgnoreCase(language.trim())) {
                        results.add(dev);
                        break; // Tìm thấy rồi, không cần kiểm tra thêm ngôn ngữ
                    }
                }
            }
        }
        return results;
    }

    // ================== CÁC HÀM XỬ LÝ FILE ==================
    private void loadFromFile(String filename) {
        if (!fileHandler.load(employeeList, filename)) {
            System.err.println("Warning: Could not load data from " + filename + ". Starting with an empty list.");
        }
    }

    public boolean saveToFile() {
        if (fileHandler.save(employeeList, DATA_FILE)) {
            System.out.println("Data saved successfully to " + DATA_FILE);
            return true;
        }
        System.err.println("Error: Failed to save data to " + DATA_FILE);
        return false;
    }

}
