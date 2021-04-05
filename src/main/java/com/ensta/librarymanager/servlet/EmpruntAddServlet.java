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

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;


// @WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
		case "/emprunt_add":
			addEmprunt(request, response);
			break;
		default:
			System.out.println("Default redirecting case from " + action + " !");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.forward(request, response);
		}
			
	}
	
	private void addEmprunt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LivreService livreService = LivreServiceImpl.getInstance();
		MembreService membreService = MembreServiceImpl.getInstance();
		List<Membre> membreList = new ArrayList<>();
		List<Livre> livreList = new ArrayList<>();
		try {
			membreList = membreService.getListMembreEmpruntPossible();
			livreList= livreService.getListDispo();
			
		}catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("livres", livreList);
		request.setAttribute("membres", membreList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int idMembre = -1;
		int idLivre = -1;
		System.out.println("teste1");
		try {

			if(request.getParameter("idMembre")== null || request.getParameter("idLivre")== null) {
				throw new ServiceException("Tous les label doivent etre rempli");
			}else {
				idMembre = Integer.parseInt(request.getParameter("idMembre"));
				idLivre = Integer.parseInt(request.getParameter("idLivre"));
				empruntService.create(idMembre, idLivre, LocalDate.now());	
			}
		}catch (ServiceException e) {

			e.printStackTrace();
			throw new ServletException(e.getMessage(),e);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("emprunt_list");
		dispatcher.forward(request, response);	
	
	}
}