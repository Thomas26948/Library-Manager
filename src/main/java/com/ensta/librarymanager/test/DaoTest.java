package com.ensta.librarymanager.test;

import java.time.LocalDate;

import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.MembreDao;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.dao.MembreDaoImpl;

import com.ensta.librarymanager.exception.DaoException;



public class DaoTest {

	public static void main(String[] args) throws DaoException {
		
		LivreDao livre = LivreDaoImpl.getInstance(); 
		livre.getList();
		livre.count();									
		livre.toString();
		

		MembreDao membre = MembreDaoImpl.getInstance();
		membre.getList();
		membre.count();
		membre.toString();
		
		EmpruntDao emprunt = EmpruntDaoImpl.getInstance();
		emprunt.count();
		emprunt.getList();
		emprunt.getById(1);
		emprunt.getListCurrentByLivre(1);
		emprunt.getListCurrentByMembre(1);
		emprunt.toString();

	}
}