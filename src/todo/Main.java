package todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

/**
 *
 * @author Nguyen Xuan Trung QE170172 todo app, it can: add a task, see all
 * tasks -> choose one task for edit/extra info, see all done tasks, see all
 * pending tasks mark a task as done.
 */
public class Main {

    static TaskList tasks;

    public static void add(String[] args) {
        String name = "";
        String desc = "";
        LocalDate deadline = null;
        try {
            name = args[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            name = Screen.getStringWithPrompt("Task name");
            if (name.equals("") && !Screen.confirmation("Leave name blank?")) {
                Screen.abort();
                return;
            }
        }
        try {
            desc = args[2];
        } catch (ArrayIndexOutOfBoundsException ex) {
            desc = Screen.getStringWithPrompt("Task description");
        }
        try {
            if (args.length < 4) {
                deadline = LocalDate.parse(Screen.getStringWithPrompt("Task deadline"));
            } else {
                deadline = LocalDate.parse(args[3]);
            }
            if (deadline.compareTo(LocalDate.now()) < 0
                    && !Screen.overduedTask()) {
                Screen.abort();
                return;
            }
        } catch (DateTimeParseException ex) {
            Screen.showDateFormat();
            if (!Screen.confirmation("Leave deadline blank?")) {
                Screen.abort();
                return;
            }
        }

        tasks = new TaskList();
        tasks.add(name, desc, deadline);
        tasks.close();
    }

    public static void showList(LinkedList<Task> alltasks) {
        if (alltasks == null) {
            Screen.unexpectedError();
            return;
        } else if (alltasks.size() == 1) {
            Screen.showTask(alltasks.element());
            return;
        } else if (alltasks.size() == 0) {
            Screen.alert("Empty task list.");
            return;
        }
        while (true) {
            Screen.showTask(alltasks);
            int choice = Screen.getChoice() - 1;
            if (choice == -1) {
                break;
            }
            if (choice >= alltasks.size()) {
                Screen.taskNotFoundAlert();
            } else {
                Screen.showTask(alltasks.get(choice));
            }
        }
    }
    public static void see(String[] args) {
        tasks = new TaskList();
        LinkedList<Task> alltasks = tasks.allTask();
        showList(alltasks);
        tasks.close();
    }

    public static void mark(String[] args) {
        if (args.length != 2) {
            Screen.illegalArgumentsError();
            Screen.showMarkFormat();
        }
        try {
            tasks = new TaskList();
            int id = Integer.parseInt(args[1]);
            tasks.mark(id);
            Screen.alert("Task " + id + " marked completed");
        } catch (NumberFormatException ex) {
            Screen.illegalArgumentsError();
            Screen.showMarkFormat();
        } catch (Exception ex) {
            Screen.unexpectedError();
            Screen.showException(ex);
        } finally {
            tasks.close();
        }

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            Screen.showInstruction();
        } else {
            switch (args[0]) {
                case "add":
                case "create":
                case "a":
                case "new":
                    add(args);
                    break;
                case "check":
                case "alltask":
                    see(args);
                    break;
                case "mark":
                case "done":
                    mark(args);
                    break;
                default:
                    break;
            }
        }

    }
}
