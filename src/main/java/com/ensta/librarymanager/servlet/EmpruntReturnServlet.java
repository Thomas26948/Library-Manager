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

public class EmpruntReturnServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
		case "/emprunt_return":
			returnEmprunt(request, response);
			break;
		default:
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.forward(request, response);
		}
	}
	private void returnEmprunt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int id=-1;
		List<Emprunt> empruntList = new ArrayList<>();
		try {
			if(request.getParameter("id")!=null)
				id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			empruntList = empruntService.getListCurrent();
		}catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("idEmprunt", id);
		System.out.println(id);
		request.setAttribute("emprunts", empruntList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int id = -1;
		try {
	
			id = Integer.parseInt(request.getParameter("id"));
			empruntService.returnBook(id);  
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	    }
		RequestDispatcher dispatcher = request.getRequestDispatcher("emprunt_list");
		dispatcher.forward(request, response);
		
		
	}
}