package todo;

import java.time.LocalDate;

/**
 *
 * @author Nguyen Xuan Trung Task: id name begin deadline done status
 */
public class Task {

    public int id;
    public String name;
    public String desc;
    public LocalDate begin;
    public LocalDate end;
    public LocalDate deadLine;
    public boolean isDone;

    public Task(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.begin = null;
        this.end = null;
        this.deadLine = null;
        this.isDone = false;
    }

    public Task(String name, String desc, LocalDate deadLine) {
        this.name = name;
        this.desc = desc;
        this.begin = LocalDate.now();
        this.deadLine = deadLine;
        this.end = null;
        this.isDone = false;
    }

    public void done() {
        isDone = true;
        this.end = LocalDate.now();
    }

    public void undone() {
        isDone = false;
        this.end = null;
    }

    public void flip() {
        if (isDone) {
            undone();
        } else {
            done();
        }
    }

    public Task(int id, String name, String desc, LocalDate begin, LocalDate end, LocalDate deadLine, boolean isDone) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.begin = begin;
        this.end = end;
        this.deadLine = deadLine;
        this.isDone = isDone;
    }

}
