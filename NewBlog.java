import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
// import jakarta.servlet.http.HttpServlet does not work for some reason, find out why.

public class NewBlog extends HttpServlet {
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Fetch params sent via the form element.
		String blog_author = request.getParameter("blog_author");
		String blog_title = request.getParameter("blog_title");
		String blog_intro = request.getParameter("blog_intro");
		String blog_para = request.getParameter("blog_para");
		String blog_outro = request.getParameter("blog_outro");

		// Arrange the query for insertion.
		String insertQuery = "INSERT INTO myblog (blog_author, blog_title, blog_intro, blog_para, blog_outro) VALUES ('";
		insertQuery += blog_author + "', '" + blog_title + "', '" + blog_intro + "', '"  + blog_para + "', '" + blog_outro + "');";

		try {
			// Establish connection to db for query execution.
			String url = "jdbc:mariadb://localhost/mydb";
			String user = "arch";
			String pass = "00000";
			Connection connection = DriverManager.getConnection(url, user, pass);
			Statement statement = connection.createStatement();

			// Execute insertion query and close the connection.
			statement.executeQuery(insertQuery);
			statement.close();
			connection.close();

	                // Automatically redirect to BlogPage servlet for viewing the newly created blog.
	                // blog_id parameter is passed with value 'new' to identify and fetch our new blog as the latest entry in the table.
	                // TODO : sendRedirect() should probably be replaced with forward() using RequestDispatcher... why?
	                response.sendRedirect("localhost:8080/examples/servlet/servlets/BlogPage?blog_id=new");

		} catch (SQLException e) {
			// In case of sql error, generate an html page instead.
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("<html><head><title>SQLException</title></head><body><h1>SQLException</h1></body></html>");
			pw.close();
		}

		// Automatically redirect to BlogPage servlet for viewing the newly created blog.
		// blog_id parameter is passed with value 'new' to identify and fetch our new blog as the latest entry in the table.
		// TODO : sendRedirect() should probably be replaced with forward() using RequestDispatcher... why?
		// response.sendRedirect("localhost:8080/examples/servlet/servlets/BlogPage?blog_id=new");
	}
}
