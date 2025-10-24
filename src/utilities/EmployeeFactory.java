package utilities;

import java.util.Arrays;
import java.util.List;
import model.Developer;
import model.Employee;
import model.TeamLeader;
import model.Tester;
import java.util.ArrayList;

public class EmployeeFactory {
    public static Employee createEmployeeFromFile(String line) {
        if (line == null || line.trim().isEmpty()) {
            System.err.println("Error: Cannot parse empty line.");
            return null;
        }

        String[] parts = line.split("_");
        try {
            // Kiểm tra số lượng phần tử tối thiểu (ID, Name, BaseSalary)
            if (parts.length < 3) {
                System.err.println("Error parsing line, too few parts: " + line);
                return null;
            }

            String empID = parts[0].trim();
            String empName = parts[1].trim();
            // Đảm bảo parse lương đúng cách, xử lý lỗi nếu không phải số
            double baseSal = Double.parseDouble(parts[2].trim());

            // Dựa vào ID để quyết định loại Employee
            if (empID.matches(RegexConst.REG_TL_ID)) {
                // Kiểm tra đủ phần tử cho TeamLeader
                if (parts.length < 7) {
                    System.err.println("Error parsing TeamLeader, too few parts: " + line);
                    return null;
                }

                // Tách ngôn ngữ, xử lý cả trường hợp không có ngôn ngữ nào (chuỗi rỗng sau
                // split)
                List<String> languages = Arrays.asList(parts[3].substring(1, parts[3].length() - 1).split(","));
                List<String> cleanLanguages = new ArrayList<>();
                for (String lang : languages) {
                    String trimmedLang = lang.trim();
                    if (!trimmedLang.isEmpty()) {
                        cleanLanguages.add(trimmedLang);
                    }
                }
                String teamName = parts[4].trim();
                int yearsOfExp = Integer.parseInt(parts[5].trim());
                double bonusRate = Double.parseDouble(parts[6].trim());
                // Kiểm tra giá trị hợp lệ trước khi tạo object (tùy chọn nhưng nên có)
                if (baseSal <= 0 || yearsOfExp < 0 || bonusRate < 0) {
                    System.err.println("Error parsing TeamLeader, invalid numeric values: " + line);
                    return null;
                }
                return new TeamLeader(empID, empName, baseSal, cleanLanguages, teamName, yearsOfExp, bonusRate);

            } else if (empID.matches(RegexConst.REG_DEV_ID)) {
                // Kiểm tra đủ phần tử cho Developer
                if (parts.length < 6) {
                    System.err.println("Error parsing Developer, too few parts: " + line);
                    return null;
                }

                List<String> languages = Arrays.asList(parts[3].substring(1, parts[3].length() - 1).split(","));
                List<String> cleanLanguages = new ArrayList<>(); // Dùng ArrayList
                for (String lang : languages) {
                    String trimmedLang = lang.trim();
                    if (!trimmedLang.isEmpty()) {
                        cleanLanguages.add(trimmedLang);
                    }
                }
                String teamName = parts[4].trim();
                int yearsOfExp = Integer.parseInt(parts[5].trim());
                if (baseSal <= 0 || yearsOfExp < 0) {
                    System.err.println("Error parsing Developer, invalid numeric values: " + line);
                    return null;
                }
                return new Developer(empID, empName, baseSal, cleanLanguages, teamName, yearsOfExp);

            } else if (empID.matches(RegexConst.REG_TESTER_ID)) {
                // Kiểm tra đủ phần tử cho Tester
                if (parts.length < 5) {
                    System.err.println("Error parsing Tester, too few parts: " + line);
                    return null;
                }
                double bonusRate = Double.parseDouble(parts[3].trim());
                String testingType = parts[4].trim();
                // Kiểm tra type hợp lệ
                if (!testingType.matches(RegexConst.REG_TEST_TYPE)) {
                    System.err.println("Error parsing Tester, invalid type '" + testingType + "': " + line);
                    return null;
                }
                if (baseSal < 0 || bonusRate < 0) { // Lương Tester có thể = 0
                    System.err.println("Error parsing Tester, invalid numeric values: " + line);
                    return null;
                }
                return new Tester(empID, empName, baseSal, bonusRate, testingType);
            } else {
                System.err.println("Error parsing line, unknown employee ID format: " + empID + " in line: " + line);
                return null; // ID không khớp với format nào
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number in line: " + line + " - " + e.getMessage());
            return null; // Lỗi parse số
        } catch (ArrayIndexOutOfBoundsException e) {
            // Lỗi này không nên xảy ra nếu đã kiểm tra length ở trên, nhưng để an toàn
            System.err.println("Error parsing line, unexpected number of parts: " + line);
            return null;
        } catch (IllegalArgumentException e) {
            // Bắt lỗi từ constructor của Employee (ví dụ tên rỗng, lương âm)
            System.err.println("Error creating employee from line: " + line + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Bắt các lỗi khác có thể xảy ra
            System.err.println("Unexpected error parsing line: " + line + " - " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug dễ hơn
            return null;
        }
    }

    public static Developer createDeveloper(String id, String name, double salary, List<String> langs, String team,
            int exp) throws IllegalArgumentException {
        return new Developer(id, name, salary, langs, team, exp);
    }

    public static Tester createTester(String id, String name, double salary, double bonus, String type)
            throws IllegalArgumentException {
        return new Tester(id, name, salary, bonus, type);
    }

    public static TeamLeader createTeamLeader(String id, String name, double salary, List<String> langs, String team,
            int exp, double bonus) throws IllegalArgumentException {
        return new TeamLeader(id, name, salary, langs, team, exp, bonus);
    }
}
