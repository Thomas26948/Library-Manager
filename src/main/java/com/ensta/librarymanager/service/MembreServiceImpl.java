package com.ensta.librarymanager.service;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.exception.DaoException;

import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.MembreDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class MembreServiceImpl implements MembreService{

	private static MembreServiceImpl instance = new MembreServiceImpl();
	
	private MembreServiceImpl() { }	
	
	public static MembreServiceImpl getInstance() {		
		if(instance==null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<>();		
		try {
			membres = membreDao.getList();
		} catch (DaoException e) {
			System.out.println(e.getMessage());		
			throw new ServiceException(e.getMessage());
		}
		return membres;
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Membre> membreList = new ArrayList<>();
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			for (int i = 0; i < membreDao.getList().size(); i++) {
					if(empruntService.isEmpruntPossible(membreDao.getList().get(i)))
						membreList.add(membreDao.getList().get(i));	
			}
			return membreList;
		}catch (DaoException e) {
			System.out.println(e.getMessage());
			throw new ServiceException(e.getMessage());			
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id); 
		} catch (DaoException e) {
			System.out.println(e.getMessage());
			throw new ServiceException(e.getMessage());			
		}
		return membre;
	}

	@Override
	/*
	return -1 if we couldn't create a new member 
	*/
	public int create(String nom, String prenom, String adresse, String email, String telephone)
			throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int i = -1;
		System.out.println("AD");

		if (nom == "" ||nom == null|| prenom == "" || prenom == null) {
						System.out.println("AE");

			throw new ServiceException("Error, please enter a full name and first name.");
		}
		else{
			try {
			System.out.println("AB");
			i = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
			}catch (DaoException e) {
			throw new ServiceException(e.getMessage());			
			} 
		}
		return i;
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		if (membre.getNom() == "" ||membre.getNom() == null || membre.getPrenom() == "" || membre.getPrenom() == null) {
			throw new ServiceException("Error, please enter a full name and first name.");
		}
		try {
			membreDao.update(membre); 
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());			
		} catch (NumberFormatException e2) {
			throw new ServiceException("Error " + e2);
		}
		
	}

	@Override
	public void delete(int id) throws ServiceException {
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			membreDao.delete(id); 
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());			
		} catch (NumberFormatException e2) {
			throw new ServiceException("Error "+ e2);
		}
		
	}

	@Override
	public int count() throws ServiceException {
			MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
			int count=0;
			try {
			count=membreDao.count();
			return count;
			// System.out.println("count : " + count);
			} catch (DaoException e) {
				return count;			
			} 
	}

}