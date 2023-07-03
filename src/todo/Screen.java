package todo;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Nguyen Xuan Trung
 */
public class Screen {
    static Scanner sc = new Scanner(System.in);

    static void showInstruction() {
        String instruction
                = "To show all tasks:\t todo.jar alltask"
                + "";
    }

    static void illegalArgumentsError() {
        System.out.println("Illegal arguments!");
    }
    static void unexpectedError() {
        System.out.println("An Unexpected Error occur! Abort!");
    }
    static void showException(Exception ex) {
        System.out.println("An Error occur!");
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }

    static void showDateFormat() {
        System.out.println("Use yyyy-mm-dd for date format");
    }

    static void showMarkFormat() {
        System.out.println("Use \"mark [task number]\" to mark a task as completed");
    }

    static boolean overduedTask() {
        System.out.println("Do you want the task to be overdued?(y/n)");
        if (sc.next().toLowerCase().compareTo("y") == 0) {
            return true;
        } else {
            return false;
        }
    }

    static int getChoice() {
        System.out.print("(Enter 0 to return)\nYour choice:..");
        int result = 0;
        boolean valid = false;
        while (!valid) {
            try {
                result = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }
        return result;
    }
    static void abort() {
        System.out.println("Abort!");
    }

    static void alert(String x) {
        System.out.println(x);
    }

    static void taskNotFoundAlert() {
        System.out.println("Task not found!");
    }

    static void showTask(LinkedList<Task> x) {
        for (Task t : x) {
            String taskRep = ((t.isDone) ? "[Done] " : "[  X ] ") + t.id + "." + t.name;
            if (t.deadLine != null) {
                taskRep += "(Deadline :" + t.deadLine.toString() + ")";
            }
            System.out.println(taskRep);
        }
    }
    static void showTask(Task x) {

        String rep = x.id + "." + x.name + "\n"
                + "Desciption : " + x.desc + "\n"
                + "Since : " + String.valueOf(x.begin) + "\n"
                + "Till : " + String.valueOf(x.end) + "\n"
                + "Deadline : " + String.valueOf(x.deadLine) + "\n"
                + "Done? : " + ((x.isDone) ? "Yes" : "No");
        System.out.println(rep);
        System.out.print("Press enter to continue..");
        sc.nextLine();
    }
}
