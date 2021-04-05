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

public class MembreDetailsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/membre_details":
				membreDetails(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request, response);
		}
	}
	
	private void membreDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Emprunt> emprunts= new ArrayList<>();
		
		int idMembre = -1;
		Membre membre = new Membre();
		try {
			idMembre = Integer.parseInt(request.getParameter("id"));
			membre = membreService.getById(idMembre);
			emprunts = empruntService.getListCurrentByMembre(idMembre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Erreur d'affichage des details de livre #" + idMembre, e);
		}
		
		request.setAttribute("membre", membre);
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		dispatcher.forward(request, response);


	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Emprunt> emprunts= new ArrayList<>();
		Membre membre = new Membre();
		int idMembre = -1;
		try {
			idMembre = Integer.parseInt(request.getParameter("id"));
			
			membre = membreService.getById(idMembre);
			membre.setNom(request.getParameter("nom"));
			membre.setPrenom(request.getParameter("prenom"));
			membre.setAbonnement(Abonnement.valueOf(request.getParameter("abonnement")));
			membre.setAdresse(request.getParameter("adresse"));
			membre.setEmail(request.getParameter("email"));
			membre.setTelephone(request.getParameter("telephone"));
			membreService.update(membre);
			emprunts = empruntService.getListCurrentByMembre(idMembre);
			
		} catch (ServiceException e) {
			throw new ServletException( "erreur MembreDetailsServlet  " +idMembre, e);
		}
		
		request.setAttribute("membre", membre);
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		dispatcher.forward(request, response);
	}
	
}