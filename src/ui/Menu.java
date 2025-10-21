package ui;

import java.util.ArrayList;
import utilities.Inputter;

public class Menu<E> {

    private String title;
    private ArrayList<String> options;

    public Menu(String title) {
        this.title = title;
        this.options = new ArrayList<>();
    }

    public void addNewOption(String newOption) {
        this.options.add(newOption);
    }

    public void print() {
        System.out.println("\n" + title);
        System.out.println("--------------------------------");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("--------------------------------");
    }

    public int getChoice() {
        return Inputter.getAnInteger("Enter your choice: ",
                "Your choice must be between 1 and " + options.size(), 1, options.size());
    }

    public E getChoiceFromObjectList(ArrayList<E> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. No items to choose from.");
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }

        int choice = Inputter.getAnInteger("Enter your choice: ",
                "Your choice must be between 1 and " + list.size(), 1, list.size());

        return list.get(choice - 1);
    }
}
