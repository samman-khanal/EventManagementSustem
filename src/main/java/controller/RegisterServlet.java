package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.UserModel;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import dao.UserDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/RegisterServlet")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 10
		)

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/register.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullName = request.getParameter("firstName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int role =Integer.parseInt( request.getParameter("role"));
		String created_date_str = request.getParameter("created_date");
		
		// For Validation
		boolean hasError = false;
		String errorMessage = "";
		
		// For Date
		Date created_date = null;
		try {
			if (created_date_str != null && !created_date_str.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				created_date = (Date) sdf.parse(created_date_str);
			}
		}
		catch (ParseException e) {
			hasError = true;
			errorMessage = "Invalid date format.";
		}
		
		// For Profile Image
		byte[] profilePicture = null;
		Part pf = request.getPart("profilePicture");
		if (pf != null && pf.getSize() > 0) {
			try (InputStream fileContent = pf.getInputStream()) {
				profilePicture = new byte[(int) pf.getSize()];
				fileContent.read(profilePicture);
			}
		}
		
		if (hasError) {
			request.setAttribute("error", errorMessage);
			request.getRequestDispatcher("/WEB-INF/view.register.jsp").forward(request, response);
			return;	
		}
		
		UserModel newUser = new UserModel(fullName, email, password, profilePicture, role, created_date);
		
		int userID = UserDAO.addUser(newUser);
		
		if (userID > 0) {
			request.getSession().setAttribute("registrationSuccessful", "Registration Successfull. Please login.");
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
		}
		else {
			request.setAttribute("error", "Registration failed. Email may already be in use.");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		}
	}
}
