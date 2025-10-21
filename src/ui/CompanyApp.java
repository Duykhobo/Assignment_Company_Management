package ui;

import controller.CompanyManagement;
import java.util.Scanner;

public class CompanyApp {

    private CompanyManagement cm;
    private Menu<String> mainMenu;

    public CompanyApp() {
        try {
            this.cm = new CompanyManagement();
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Could not start the application. Reason: " + e.getMessage());
            System.exit(1);
        }

        this.mainMenu = new Menu<>("===== Company Employee Management Program =====");
        mainMenu.addNewOption("Show the Employee list");
        mainMenu.addNewOption("Add Employee");
        mainMenu.addNewOption("Update Employee");
        mainMenu.addNewOption("Search Employee");
        mainMenu.addNewOption("Store data to file");
        mainMenu.addNewOption("Sort Employee");
        mainMenu.addNewOption("Exit");
    }

    public void run() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            mainMenu.print();
            choice = mainMenu.getChoice();

            switch (choice) {
                case 1:
                    cm.showEmployeeList();
                    break;
                case 2:
                    cm.addEmployee();
                    break;
                case 3:
                    cm.updateEmployee();
                    break;
                case 4:
                    cm.searchEmployee();
                    break;
                case 5:
                    cm.saveToFile();
                    break;
                case 6:
                    cm.sortEmployees();
                    break;
                case 7:
                    System.out.println("Thank you for using the program. Goodbye!");
                    break;
            }

            if (choice != 7) {
                System.out.println("Press Enter to continue...");
                sc.nextLine();
            }

        } while (choice != 7);
        sc.close();
    }
}
