package todo;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Nguyen Xuan Trung QE170172 todo app, it can: add a task, see all
 * tasks -> choose one task for edit/extra info, see all done tasks, see all
 * pending tasks mark a task as done.
 */
public class Main {

    static TaskList tasks = new TaskList();

    public static void add(String[] args) {
        if (args.length == 2) {
            tasks.add(new Task(0, args[1], ""));
        }
        if (args.length == 3) {
            tasks.add(new Task(0, args[1], args[2]));
        }
        if (args.length == 4) {
            try {
                Task newtask = new Task(0, args[1], args[2]);
                newtask.deadLine = LocalDate.parse(args[3]);
                if (newtask.deadLine.compareTo(LocalDate.now()) < 0
                        && !Screen.overduedTask()) {
                    Screen.abort();
                    return;
                }
                tasks.add(newtask);
                Screen.alert("Add successful!");
            } catch (Exception ex) {
                Screen.showException(ex);
                Screen.showDateFormat();
            }
        }

    }

    public static void see(String[] args) {
        LinkedList<Task> alltasks = tasks.allTask();
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
                return;
            }
            if (choice >= alltasks.size()) {
                Screen.taskNotFoundAlert();
            } else {
                Screen.showTask(alltasks.get(choice));
            }
        }
    }

    public static void mark(String[] args) {
        if (args.length != 2) {
            Screen.illegalArgumentsError();
            Screen.showMarkFormat();
        }
        try {
            int id = Integer.parseInt(args[1]);
            tasks.mark(id);
            Screen.alert("Task " + id + " marked completed");
        } catch (NumberFormatException ex) {
            Screen.illegalArgumentsError();
            Screen.showMarkFormat();
        } catch (Exception ex) {
            Screen.unexpectedError();
            Screen.showException(ex);
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
        tasks.close();
    }
}
