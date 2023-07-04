package todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

/**
 *
 * @author Nguyen Xuan Trung QE170172 todo app, it can: add a task, see all
 * tasks -> choose one task for edit/extra info, see all done tasks, see all
 * pending tasks mark a task as done.
 *
 * this application only work when my tunnel agent is on.
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
        Screen.horiLine();
        if (alltasks == null) {
            Screen.unexpectedError();
            return;
        } else if (alltasks.size() == 1) {
            Screen.showTask(alltasks.element());
            return;
        } else if (alltasks.isEmpty()) {
            Screen.alert("Empty task list.");
            return;
        }
        while (true) {
            Screen.showTask(alltasks);
            int choice = Screen.getChoice();
            if (choice == 0) {
                break;
            }
            boolean found = false;
            for (Task x : alltasks) {
                if (x.id == choice) {
                    Screen.horiLine();
                    Screen.showTask(x);
                    if (Screen.confirmation("Edit the task?")) {
                        editTask(x);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                Screen.taskNotFoundAlert();
            }
        }
    }

    public static void editTask(Task x) {
        String newName = null;
        String newDesc = null;
        String newDeadline = null;
        String isDone = null;
        Screen.horiLine();
        Screen.showTask(x);
        Screen.alert("You will now edit this task. This action can not be cancelled. Enter blank to keep the old value.");
        newName = Screen.getStringWithPrompt("New task name");
        newName = newName.equals("") ? x.name : newName;
        newDesc = Screen.getStringWithPrompt("New task description");
        newDesc = newDesc.equals("") ? x.desc : newDesc;
        newDeadline = Screen.getStringWithPrompt("New task deadline");
        while (newDeadline.equals("") == false) {
            try {
                x.deadLine = LocalDate.parse(newDeadline);
                if (x.deadLine.compareTo(LocalDate.now()) < 0
                        && !Screen.confirmation("Deadline already overdued. Keep it?")) {
                    continue;
                }
                newDeadline = "";
            } catch (DateTimeParseException ex) {
                Screen.alert("Wrong date format");
                Screen.showDateFormat();
                newDeadline = Screen.getStringWithPrompt("New task deadline");
            }
        }
        isDone = Screen.getStringWithPrompt("Completed task? (y/n)").toLowerCase();
        boolean valid = false;
        while (!valid) {
            valid = true;
            switch (isDone) {
            case "y":
                x.isDone = true;
                break;
            case "n":
                x.isDone = false;
            case "":
                break;
            default:
                Screen.alert("Invalid value.");
                valid = false;
                break;
            }
        }
        x.name = newName;
        x.desc = newDesc;
        tasks = new TaskList();
        if (tasks.update(x)) {
            Screen.alert("Update task successfully!");
        } else {
            Screen.alert("Update failed");
        }
        tasks.close();
    }

    public static void see(String[] args) {
        LinkedList<Task> alltasks = null;
        boolean status = false;
        if (args.length > 2) {
            Screen.illegalArgumentsError();
            Screen.showAllTaskInstruction();
            return;
        }
        if (args.length == 2) {
            switch (args[1]) {
                case "done":
                case "finish":
                    status = true;
                    break;
                case "pending":
                case "unfinish":
                    status = false;
                    break;
                default:
                    Screen.illegalArgumentsError();
                    Screen.showAllTaskInstruction();
                    return;
            }
            tasks = new TaskList();
            alltasks = tasks.allStatusTask(status);
        } else if (args.length == 1) {
            tasks = new TaskList();
            alltasks = tasks.allTask();
        }
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
                    break;
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
