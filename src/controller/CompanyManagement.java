package controller;

import java.util.ArrayList;
import java.util.List;
import model.Developer;
import model.Employee;
import model.TeamLeader;
import model.Tester;
import service.EmployeeService;
import ui.Menu;
import utilities.Inputter;
import utilities.RegexConst;
import utilities.Validator;
import utilities.EmployeeFactory;

public class CompanyManagement {

    private final EmployeeService employeeService;

    public CompanyManagement() {
        this.employeeService = new EmployeeService();
    }

    public void showEmployeeList() {
        List<Employee> list = employeeService.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("Employee list is empty.");
            return;
        }
        System.out.println("===== Employee List =====");
        for (Employee employee : list) {
            System.out.println(employee.toString() + " | Final Salary: " + employee.getSalary());
        }
        System.out.println("=========================");
    }

    public void addEmployee() {
        Menu<String> addMenu = new Menu<>("----- Add Employee Sub-menu -----");
        addMenu.addNewOption("Add Developer");
        addMenu.addNewOption("Add Tester");
        addMenu.addNewOption("Add Team Leader");
        addMenu.addNewOption("Back to main menu");
        int choice;
        do {
            addMenu.print();
            choice = addMenu.getChoice();
            switch (choice) {
                case 1:
                    addDeveloper();
                    break;
                case 2:
                    addTester();
                    break;
                case 3:
                    addTeamLeader();
                    break;
            }
        } while (choice != 4);
    }

    public void updateEmployee() {
        String empID = Inputter.getAString("Enter the ID of the employee to update: ", "ID cannot be empty.");
        Employee emp = employeeService.findEmployeeById(empID);
        if (emp == null) {
            System.out.println("FAIL: Employee with ID '" + empID + "' does not exist.");
            return;
        }

        System.out.println("Found employee: " + emp.toString());
        System.out.println("Enter new information below. Press ENTER to keep the current value.");

        // --- Thu thập thông tin chung ---
        String newName = Validator.getValidatedStringInput("Enter new name", emp.getEmpName());
        double newBaseSal = Validator.getValidatedDoubleInput("Enter new base salary", emp.getBaseSalary(), 0,
                Double.MAX_VALUE);

        boolean updateSuccess = false;

        // --- Dùng instanceof để XÁC ĐỊNH loại input cần hỏi VÀ gọi service tương ứng
        // ---
        if (emp instanceof TeamLeader) {
            TeamLeader tl = (TeamLeader) emp;
            String newTeamName = Validator.getValidatedStringInput("Enter new team name", tl.getTeamName());
            List<String> newLanguages = Validator.getValidatedLanguagesInput();
            int newExpYear = Validator.getValidatedIntInput("Enter new years of experience", tl.getYearsOfExperience(),
                    0, 50);
            double newBonusRate = Validator.getValidatedDoubleInput("Enter new bonus rate", tl.getBonusRate(), 0,
                    Double.MAX_VALUE);

            // GỌI SERVICE ĐỂ UPDATE
            updateSuccess = employeeService.updateTeamLeader(empID, newName, newBaseSal, newTeamName, newLanguages,
                    newExpYear, newBonusRate);

        } else if (emp instanceof Developer) {
            Developer dev = (Developer) emp;
            String newTeamName = Validator.getValidatedStringInput("Enter new team name", dev.getTeamName());
            List<String> newLanguages = Validator.getValidatedLanguagesInput();
            int newExpYear = Validator.getValidatedIntInput("Enter new years of experience", dev.getYearsOfExperience(),
                    0, 50);

            // GỌI SERVICE ĐỂ UPDATE
            updateSuccess = employeeService.updateDeveloper(empID, newName, newBaseSal, newTeamName, newLanguages,
                    newExpYear);

        } else if (emp instanceof Tester) {
            Tester t = (Tester) emp;
            double newBonusRate = Validator.getValidatedDoubleInput("Enter new bonus rate", t.getBonusRate(), 0,
                    Double.MAX_VALUE);
            String newType = Validator.getValidatedTesterTypeInput(t.getType());

            // GỌI SERVICE ĐỂ UPDATE
            updateSuccess = employeeService.updateTester(empID, newName, newBaseSal, newBonusRate, newType);
        } else {
            System.out.println("WARN: Unknown employee type for ID " + empID); // Trường hợp phòng hờ
        }

        // --- Thông báo kết quả dựa trên giá trị trả về từ service ---
        if (updateSuccess) {
            System.out.println("==> SUCCESS: Employee information updated.");
        } else {
            // Service đã in log lỗi chi tiết hơn (nếu có)
            System.out.println("==> FAIL: Could not update employee. Please check logs or input.");
        }
    }

    public void searchEmployee() {
        Menu<String> searchMenu = new Menu<>("----- Search Employee Sub-menu -----");
        searchMenu.addNewOption("Search by Name");
        searchMenu.addNewOption("Find Tester with Highest Salary");
        searchMenu.addNewOption("Search by Programming Language");
        searchMenu.addNewOption("Back to main menu");
        int choice;
        do {
            searchMenu.print();
            choice = searchMenu.getChoice();
            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    findTesterWithHighestSalary();
                    break;
                case 3:
                    searchByProgrammingLanguage();
                    break;
            }
        } while (choice != 4);
    }

    public void saveToFile() {
        employeeService.saveToFile();
    }

    public void sortEmployees() {
        List<Employee> sortedList = employeeService.sortEmployees();
        System.out.println("==> SUCCESS: The employee list has been sorted.");
        for (Employee employee : sortedList) {
            System.out.println(employee);
        }
    }

    private void addDeveloper() {
        System.out.println("----- Adding a new Developer -----");
        String empID = getNewEmployeeId(RegexConst.REG_DEV_ID, "ID format must be 'DXXX'!");
        String empName = Inputter.getAString("Enter name: ", "Name is required.", RegexConst.REG_GLOBAL_NAME);
        double baseSalary = Inputter.getADouble("Enter base salary: ", "Salary > 0.", 1, Double.MAX_VALUE);
        String teamName = Inputter.getAString("Enter team name: ", "Team name is required!");
        List<String> langList = getProgrammingLanguages();
        int exp = Inputter.getAnInteger("Enter years of experience: ", "Exp between 0 and 50.", 0, 50);

        try {
            // --- GỌI FACTORY ---
            Employee dev = EmployeeFactory.createDeveloper(empID, empName, baseSalary, langList, teamName, exp); //
            employeeService.addEmployee(dev);
            System.out.println("==> Developer added successfully!");
        } catch (IllegalArgumentException e) {
            // Bắt lỗi nếu Factory (hoặc constructor ngầm) báo dữ liệu không hợp lệ
            System.out.println("Error adding developer: " + e.getMessage());
        }
    }

    private void addTester() {
        System.out.println("----- Adding a new Tester -----");
        String empID = getNewEmployeeId(RegexConst.REG_TESTER_ID, "ID format must be 'TXXX'!");
        String empName = Inputter.getAString("Enter name: ", "Name is required.", RegexConst.REG_GLOBAL_NAME);
        double baseSal = Inputter.getADouble("Enter base salary: ", "Salary >= 0.", 0, Double.MAX_VALUE);
        double bonusRate = Inputter.getADouble("Enter bonus rate: ", "Rate >= 0.", 0, Double.MAX_VALUE);
        String type = Inputter.getAString("Enter type (AM/MT): ", "Must be AM or MT.", RegexConst.REG_TEST_TYPE);

        try {
            // --- GỌI FACTORY ---
            Employee tester = EmployeeFactory.createTester(empID, empName, baseSal, bonusRate, type); //
            employeeService.addEmployee(tester);
            System.out.println("==> Tester added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding tester: " + e.getMessage());
        }
    }

    private void addTeamLeader() {
        System.out.println("----- Adding a new Team Leader -----");
        String teamName = Inputter.getAString("Enter team name: ", "Team name is required!");
        if (employeeService.doesTeamHaveLeader(teamName)) {
            System.out.println("Error: Team '" + teamName + "' already has a leader.");
            return;
        }
        String empID = getNewEmployeeId(RegexConst.REG_TL_ID, "ID format must be 'TLXXX'!");
        String empName = Inputter.getAString("Enter name: ", "Name is required.", RegexConst.REG_GLOBAL_NAME);
        double baseSalary = Inputter.getADouble("Enter base salary: ", "Salary > 0.", 1, Double.MAX_VALUE);
        List<String> langList = getProgrammingLanguages();
        int exp = Inputter.getAnInteger("Enter years of experience: ", "Exp between 0 and 50.", 0, 50);
        double bonusRate = Inputter.getADouble("Enter bonus rate: ", "Rate >= 0.", 0, Double.MAX_VALUE);

        try {
            // --- GỌI FACTORY ---
            Employee tl = EmployeeFactory.createTeamLeader(empID, empName, baseSalary, langList, teamName, exp,
                    bonusRate); //
            employeeService.addEmployee(tl);
            System.out.println("==> Team Leader added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding team leader: " + e.getMessage());
        }
    }

    private void searchByName() {
        String searchTerm = Inputter.getAString("Enter name to search for: ", "Search term cannot be empty.");
        List<Employee> results = employeeService.searchByName(searchTerm);

        if (results.isEmpty()) {
            System.out.println("No Employee is matched with the name '" + searchTerm + "'.");
        } else {
            System.out.println("===== Search Results =====");
            for (Employee emp : results) {
                System.out.println(emp.toString() + " | Final Salary: " + emp.getSalary());
            }
            System.out.println("==========================");
        }
    }

    private void findTesterWithHighestSalary() {
        Tester highestPaidTester = employeeService.findTesterWithHighestSalary();
        if (highestPaidTester == null) {
            System.out.println("There are no testers in the list.");
        } else {
            System.out.println("===== Tester with Highest Salary =====");
            System.out.println(highestPaidTester.toString() + " | Final Salary: " + highestPaidTester.getSalary());
            System.out.println("==========================");
        }
    }

    private void searchByProgrammingLanguage() {
        String searchLang = Inputter.getAString("Enter programming language: ", "Language cannot be empty.");
        List<Developer> results = employeeService.searchByProgrammingLanguage(searchLang);
        if (results.isEmpty()) {
            System.out.println("No Developer is matched with the language '" + searchLang + "'.");
        } else {
            System.out.println("===== Search Results =====");
            for (Developer dev : results) {
                System.out.println(dev.toString() + " | Final Salary: " + dev.getSalary());
            }
            System.out.println("==========================");
        }
    }

    private String getNewEmployeeId(String regex, String formatErrorMsg) {
        while (true) {
            String empID = Inputter.getAString("Enter Employee ID: ", "ID is required!");
            if (!empID.matches(regex)) {
                System.out.println(formatErrorMsg);
            } else if (employeeService.findEmployeeById(empID) != null) {
                System.out.println("Error: This ID already exists!");
            } else {
                return empID;
            }
        }
    }

    private List<String> getProgrammingLanguages() {
        String langStr = Inputter.getAString("Enter languages (comma-separated): ", "Required!");

        String[] langArray = langStr.split(",");

        List<String> langList = new ArrayList<>();

        for (String lang : langArray) {
            langList.add(lang.trim());
        }

        return langList;
    }

}
