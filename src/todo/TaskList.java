package todo;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Giga P34
 */
public class TaskList {
    //private String db_url = "jdbc:mysql://127.0.0.1:3306";
    private String db_url = "jdbc:mysql://category-dot.at.ply.gg:46462";
    private Connection db;

    public TaskList() {
        Statement stmt = null;
        ResultSet resultset = null;
        try {            
            db = DriverManager.getConnection(db_url, user_name, password);            
            stmt = db.createStatement();
            stmt.execute("USE todo");

        } catch (SQLException e) {
            Screen.showException(e);
        }
    }

    public boolean add(Task x) {
        if (x.begin == null) {
            x.begin = LocalDate.now();
        }
        try {
            String qry = "INSERT INTO task(task_name,task_desc,task_begin,task_deadline)"
                    + " VALUES (?,?,?,?)";
            PreparedStatement stmt
                    = db.prepareStatement(qry);
            stmt.setString(1, x.name);
            stmt.setString(2, x.desc);
            stmt.setDate(3, Date.valueOf(x.begin));
            if (x.deadLine == null) {
                stmt.setNull(4, java.sql.Types.DATE);
            } else
                stmt.setDate(4, Date.valueOf(x.deadLine));
            stmt.execute();
        } catch (Exception ex) {
            Screen.showException(ex);
            return false;
        }
        return true;
    }

    public LinkedList<Task> allTask() {
        LinkedList<Task> result = new LinkedList<>();
        ResultSet resultset = null;
        try {
            Statement statement = db.createStatement();
            statement.execute("SELECT * FROM task;");
            ResultSet rest = statement.getResultSet();
            while (rest.next()) {
                Task cur = new Task(rest.getInt("task_id"), rest.getString("task_name"), rest.getString("task_desc"));
                Date begin = rest.getDate("task_begin");
                if (begin != null) {
                    cur.begin = begin.toLocalDate();
                }
                Date done = rest.getDate("task_done");
                if (done != null) {
                    cur.end = done.toLocalDate();
                }
                Date deadline = rest.getDate("task_DeadLine");
                if (deadline != null) {
                    cur.deadLine = deadline.toLocalDate();
                }
                cur.isDone = rest.getBoolean("task_status");
                result.add(cur);
            }
        } catch (SQLException e) {
            Screen.showException(e);
            return null;
        }
        return result;
    }

    public boolean mark(int id) throws Exception {
        String qry = "UPDATE task SET task_status = true, task_done = ? WHERE task_id = ?;";
        PreparedStatement stmt
                = db.prepareStatement(qry);
        stmt.setDate(1, Date.valueOf(LocalDate.now()));
        stmt.setInt(2, id);
        stmt.execute();
        return true;
    }

    public void close() {
        try {
        if (db != null) {
            db.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String user_name = "reinir";
    private String password = "password";
}
