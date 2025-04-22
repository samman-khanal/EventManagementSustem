package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final String REMEMBER_ME_COOKIE_NAME = "rememberMe";
	private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (REMEMBER_ME_COOKIE_NAME.equals(cookie.getName())) {
                    String[] credentials = decodeCookieValue(cookie.getValue());
					if (credentials != null && credentials.length == 2) {
						String username = credentials[0];
						String password = credentials[1];
						UserModel user = UserDAO.getUserByEmail(username, password);
						if (user != null) {
							HttpSession session = request.getSession();
							session.setAttribute("user", user);
							session.setAttribute("loggedIn", true);
							response.sendRedirect(request.getContextPath() + "/");
							return ;
						}
					}
				}
			}
		}
		String registrationSuccess = (String) request.getSession().getAttribute("registrationSuccess");
		if (registrationSuccess != null) {
			request.setAttribute("registrationSuccess", registrationSuccess);
			request.getSession().removeAttribute("registrationSuccess");
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");
		
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request.setAttribute("error", "Username and password are required.");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		UserModel user = UserDAO.getUserByEmail(email, password);
		
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("loggedIn", true);
			
			if (rememberMe != null && rememberMe.equals("on")) {
				String cookieValue = encodeCookieValue(email, password);
				Cookie rememberMeCookie = new Cookie(REMEMBER_ME_COOKIE_NAME, cookieValue);
				rememberMeCookie.setMaxAge(COOKIE_MAX_AGE);
				rememberMeCookie.setPath(request.getContextPath());
				response.addCookie(rememberMeCookie);
			}
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			request.setAttribute("error", "Invalid email or password.");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
	}
	
	private String encodeCookieValue(String email, String password) {
		return email + ":" +password;
	}
	
	private String[] decodeCookieValue(String cookieValue) {
		return cookieValue.split(":");
	}

}
