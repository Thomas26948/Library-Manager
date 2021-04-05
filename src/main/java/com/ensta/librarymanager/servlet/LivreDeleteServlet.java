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

// @WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_delete":
				livreDelete(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request, response);
		}
	}
	
	private void livreDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();		
		Livre livre = new Livre();
		boolean isLivreDispo = false;
		int idLivre = -1;
		try {
			idLivre = Integer.parseInt(request.getParameter("id"));
			isLivreDispo = empruntService.isLivreDispo(idLivre);
			livre  = livreService.getById(idLivre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("livre", livre);
		request.setAttribute("isLivreDispo", isLivreDispo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		dispatcher.forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		int idLivre = -1;
		try {
			idLivre = Integer.parseInt(request.getParameter("id"));
			livreService.delete(idLivre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	    }
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("livre_list");
		dispatcher.forward(request, response);
				
	}
}