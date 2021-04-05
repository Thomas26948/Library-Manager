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

public class DashboardServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
		case "/dashboard":
			dashboard(request, response);
			break;
		default:
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.forward(request, response);
			
		}
	}	
				
	private void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		MembreService membreService = MembreServiceImpl.getInstance();
		LivreService livreService = LivreServiceImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<>();
		int nEmprunt = -1;
		int nMembre = -1;
		int nLivre = -1;
		try {
			empruntList = empruntService.getListCurrent();
			nEmprunt = empruntService.count();
			nLivre = livreService.count();
			nMembre = membreService.count();


			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", empruntList);
		request.setAttribute("nmembre", nMembre);
		request.setAttribute("nemprunt", nEmprunt);
		request.setAttribute("nlivre", nLivre);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		dispatcher.forward(request, response);
	}
	
}

