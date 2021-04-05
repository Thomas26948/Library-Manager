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

// @WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/membre_delete":
				deleteMembre(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request, response);
		}
	}
	
	private void deleteMembre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		Membre membre = new Membre();
		boolean membreHaveEmprunts = false;
		int idMembre = -1;
		try {
			idMembre = Integer.parseInt(request.getParameter("id"));
			emprunts = empruntService.getListCurrentByMembre(idMembre);
			membreHaveEmprunts = !emprunts.isEmpty();
			membre  = membreService.getById(idMembre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("membre", membre);
		request.setAttribute("membreHaveEmprunts", membreHaveEmprunts);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
		dispatcher.forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		MembreService membreService = MembreServiceImpl.getInstance();
		int idMembre = -1;
		try {
			idMembre = Integer.parseInt(request.getParameter("id"));
			membreService.delete(idMembre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	    }
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("membre_list");
		dispatcher.forward(request, response);
				
	}
}