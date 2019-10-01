import java.net.*;
import java.io.*;
import java.sql.*;

public class Server {
    public static String getFormula(int a, int b) {
        String s = "";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No JBDC");
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection
                    ("jdbc:postgresql://127.0.0.1:5432/Formulas 2",
                            "postgres",	"123456");
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement
                    ("SELECT required.name, formula.value FROM required, "
                            + "formula WHERE (required.num = formula.num) AND " +
                            "(required.num = ?) AND (formula.numb = ?);");
            preparedStatement.setInt(1, a);
            preparedStatement.setInt(2, b);
            ResultSet result1 = preparedStatement.executeQuery();
            while (result1.next()) {
                s += result1.getString("name")
                        + result1.getString("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static void main(String[] args) {
        int port = 6666;
        try {
            ServerSocket ss = new ServerSocket(port);

            Socket socket = ss.accept();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;
            while(true) {
                line = in.readUTF();
                out.writeUTF(getFormula(line.charAt(0) - '0', line.charAt(2) - '0'));
                out.flush();
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}
