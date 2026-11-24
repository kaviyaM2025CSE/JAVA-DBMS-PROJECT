import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public TaskService() {
    }

    public void addTask(String title, String priority, Date deadline) throws SQLException {
        String query = "INSERT INTO tasks (title, priority, status, deadline, reward_claimed) VALUES (?, ?, 'PENDING', ?, FALSE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, priority);
            pstmt.setDate(3, deadline);
            pstmt.executeUpdate();
            System.out.println("🎉 DBMS Create: Task Inserted Successfully!");
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, title, status, priority, deadline, reward_claimed FROM tasks ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tasks.add(new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    rs.getString("priority"),
                    rs.getDate("deadline"),
                    rs.getBoolean("reward_claimed")
                ));
            }
        }
        return tasks;
    }

    public void updateTaskStatus(int id, String status) throws SQLException {
        String query = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("✅ DBMS Update: Task ID " + id + " Status marked as " + status);
        }
    }

    public void updateTaskDeadline(int id, Date deadline) throws SQLException {
        String query = "UPDATE tasks SET deadline = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, deadline);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("📅 DBMS Update: Task ID " + id + " Deadline updated to " + deadline);
        }
    }

    public boolean markRewardClaimed(int id) throws SQLException {
        String query = "UPDATE tasks SET reward_claimed = TRUE WHERE id = ? AND status = 'COMPLETED' AND reward_claimed = FALSE AND deadline IS NOT NULL AND deadline >= CURDATE()";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    public void deleteTask(int id) throws SQLException {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("🗑️ DBMS Delete: Task ID " + id + " removed successfully.");
        }
    }
}