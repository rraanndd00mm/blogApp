import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HomePage extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		printWriter.print("<html><head><title>");
		try {
			String fetchQuery = "SELECT * FROM myblog ORDER BY blog_id DESC LIMIT 10;";
			String url = "jdbc:mariadb://localhost/mydb";
			String user = "arch";
			String pass = "00000";
			Connection connection = DriverManager.getConnection(url, user, pass);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			printWriter.print("Home Page</title></head><body>");
			printWriter.print("<h2><a href='http://127.0.0.1:8080/examples/servlets/servlet/HomePage'>Home</a>&nbsp&nbsp");
			printWriter.print("<a href='http://127.0.0.1:8080/examples/servlets/servlet/BlogPage?blog_id=new'>Latest</a>&nbsp&nbsp");
                        printWriter.print("<a href='http://127.0.0.1:8080/examples/servlets/servlet/HomePage'>New Blog</a>&nbsp&nbsp");
                        printWriter.print("<a href='http://127.0.0.1:8080/examples/servlets/servlet/HomePage'>About</a></h2>");
			while (resultSet.next()) {
				printWriter.print("<h1>" + resultSet.getString("blog_title"));
				printWriter.print("</h1><p>" + resultSet.getString("blog_date"));
                                printWriter.print(", " + resultSet.getString("blog_author"));
                                printWriter.print("<br>" + resultSet.getString("blog_intro") + "</p><hr>");
			}
			printWriter.print("</body></html>");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			printWriter.print("SQLException</title></head><body><h1>SQLException</h1><body></html>");
		}
		printWriter.close();
	}
}
