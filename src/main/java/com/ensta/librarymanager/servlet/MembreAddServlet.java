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

// @WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_add.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ServletException(e);
        } 
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int id;
        
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            id = membreService.create(nom, prenom, adresse, email, telephone);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(("/LibraryManager/membre_details?id="+id));
    }
	
	
}