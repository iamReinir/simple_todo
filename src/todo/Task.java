/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
