import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class BlogPage extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		// TODO : Add link to stylesheet.
		String rootdir = "http://127.0.0.1:8080/examples/servlets/servlet/";
		pw.print("<html><head><title>Blog Page</title></head><body><h2>");
		pw.print("<a href='http://127.0.0.1:8080/examples/servlets/servlet/HomePage'>Home</a>");
		pw.print("&nbsp&nbsp");
		pw.print("<a href='http://127.0.0.1:8080/examples/servlets/servlet/BlogPage?blog_id=new'>Latest</a>");
		pw.print("&nbsp&nbsp");
		pw.print("<a href='http://127.0.0.1:8080/examples/newBlog.html'>New</a>");
		pw.print("</h2><hr>");

		try {

			// Fetch the blog_id param as well as create a query while checking if the newest blog is requested.
			// TODO : Figure out if a better way to implement this.
			String blog_id = request.getParameter("blog_id");
			String fetchQuery;
			if (blog_id.equals("new")) fetchQuery = "SELECT * FROM myblog ORDER BY blog_id DESC LIMIT 1;";
			else fetchQuery = "SELECT * FROM myblog WHERE blog_id=" + blog_id + ";";

	        	String url = "jdbc:mariadb://localhost/mydb";
	                String user = "arch";
	                String pass = "00000";

			Connection connection = DriverManager.getConnection(url, user, pass);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);

			// TODO : Figure out a replacement of ResultSet for containing an output with single row.
			if (resultSet.first()) {
				pw.print("<h1>" + resultSet.getString("blog_title"));
				pw.print("</h1><p>" + resultSet.getDate("blog_date"));
				pw.print(", " + resultSet.getString("blog_author"));
				pw.print("</p><br><br><p>" + resultSet.getString("blog_intro"));
                                pw.print("</p><br><p>" + resultSet.getString("blog_para"));
                                pw.print("</p><br><p>" + resultSet.getString("blog_outro") + "</p>");
			} else pw.print("<h1>ResultSetException</h1>");

			statement.close();
			connection.close();
		} catch (SQLException e) { pw.print("<h1>SQLException</h1>"); }

		pw.print("</body></html>");
		pw.close();
	}
}
