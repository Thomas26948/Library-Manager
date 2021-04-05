package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;


import java.util.ArrayList;
import java.util.List;

// @WebServlet("/livre_list")
public class LivreListServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_list":
				showLivreList(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request, response);
		}
	}
	
	private void showLivreList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		List<Livre> livres = new ArrayList<Livre>();
		
		try {
			livres = livreService.getList();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Erreur d'affichage des livres" , e);
		}

		
		request.setAttribute("livres", livres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
		dispatcher.forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}