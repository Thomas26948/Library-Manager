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


public class LivreAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_add":
		
				addLivre(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request, response);
			}
				
		}
	
	
	private void addLivre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
		dispatcher.forward(request, response);

	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		int idLivre = -1;

		try {

			idLivre = livreService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));
			if(idLivre!=-1) {
					request.setAttribute("livre", livreService.getById(idLivre));
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
					dispatcher.forward(request, response);
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
					dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
	    }
		
	}
}