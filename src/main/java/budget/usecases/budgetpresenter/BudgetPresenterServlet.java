package budget.usecases.budgetpresenter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/budget")
public class BudgetPresenterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("test");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h2>This works!<h2>");
	}

}
