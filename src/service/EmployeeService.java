package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Developer;
import model.Employee;
import model.TeamLeader;
import model.Tester;
import repo.FileHandler;
import utilities.EmployeeFactory;

public class EmployeeService {
    private static EmployeeService instance = null;
    private static final String DATA_FILE = "employees.txt";
    private final ArrayList<Employee> employeeList;

    private final FileHandler<Employee> fileHandler = new FileHandler<Employee>() {
        @Override
        public Employee handleLine(String line) {
            return EmployeeFactory.createEmployeeFromFile(line);
        }
    };

    private EmployeeService() {
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

    public boolean updateTester(String empID, String newName, double newBaseSal, double newBonusRate, String newType) {
        Employee emp = findEmployeeById(empID);
        if (emp instanceof Tester) {
            Tester t = (Tester) emp;
            t.updateDetails(newName, newBaseSal, newBonusRate, newType);
            return true;

        }
        return false;
    }

    public boolean updateDeveloper(String empID, String newName, double newBaseSal, String newTeamName,
            List<String> newLanguages, int newExpYear) {
        Employee emp = findEmployeeById(empID);
        if (emp instanceof Developer) {
            Developer dev = (Developer) emp;
            dev.updateDetails(newName, newBaseSal, newTeamName, newLanguages, newExpYear);
            return true;
        }

        return false;
    }

    public boolean updateTeamLeader(String empID, String newName, double newBaseSal, String newTeamName,
            List<String> newLanguages, int newExpYear, double newBonusRate) {
        Employee emp = findEmployeeById(empID);
        if (emp instanceof TeamLeader) {
            TeamLeader tl = (TeamLeader) emp;
            tl.updateDetails(newName, newBaseSal, newTeamName, newLanguages, newExpYear, newBonusRate);
            return true;
        }
        return false;
    }

    public static EmployeeService getInstance() {
        if (instance == null) {
            synchronized (EmployeeService.class) {

                if (instance == null) {
                    instance = new EmployeeService();
                }
            }
        }
        return instance;
    }
}
