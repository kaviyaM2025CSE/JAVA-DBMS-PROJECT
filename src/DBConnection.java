import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
   public DBConnection() {
   }

   public static Connection getConnection() {
      Connection var0 = null;

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         var0 = DriverManager.getConnection("jdbc:mysql://localhost:3306/VSCodeTaskDB", "root", "password");
      } catch (Exception var2) {
         System.out.println("❌ Database Connection Error: " + var2.getMessage());
      }

      return var0;
   }
}