package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.*;


public class ServiceTest {

	public static void main(String[] args) throws ServiceException {
		LivreService livre =  LivreServiceImpl.getInstance();
		EmpruntService emprunt = EmpruntServiceImpl.getInstance(); 
		MembreService membre =  MembreServiceImpl.getInstance();

		livre.getList();
		livre.count();
		livre.getListDispo();
		livre.create("Le meilleur des mondes","Aldous Huxley", "0123456");
		livre.delete(1);

		membre.count();
		membre.getById(1);
		membre.getList();
		membre.getListMembreEmpruntPossible();
		membre.create("Bob","Dylan","Paris","adresse@mail.com","0601020304");
		membre.delete(1);

		emprunt.getById(1);
		emprunt.count();
		emprunt.getListCurrent();
		emprunt.getListCurrentByMembre(1);

	}

}