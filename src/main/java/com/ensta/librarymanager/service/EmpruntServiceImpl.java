package com.ensta.librarymanager.service;


import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.exception.DaoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService {

    private static EmpruntServiceImpl instance;
	
	private EmpruntServiceImpl(){}
	
	public static EmpruntServiceImpl getInstance() {
		if(instance==null) {
			instance = new EmpruntServiceImpl();
		}
		return instance;
    }
    
    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getList();
			return emprunts;
		} catch (DaoException e){
			System.out.println(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
    }

    @Override
	public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getListCurrent();
		} catch (DaoException e){
			System.out.println(e.getMessage());
			return emprunts;
			// throw new ServiceException(e.getMessage());
		}
		
		return emprunts;
    }
    
    @Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunts;
    }
    
    @Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunts;
    }
    
    @Override
	public Emprunt getById(int id) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = null;
        
		try{
			emprunt = empruntDao.getById(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunt;
    }
    
    @Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        
		try{
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
    }
    
    @Override
	public void returnBook(int id) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

		try{
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
    }
    
    @Override
	public int count() throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        int count = 0;
        
		try{
			count = empruntDao.count();
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return count;
    }
    
    @Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        boolean res = false;
        
		try{
            List<Emprunt> listLivre = empruntDao.getListCurrentByLivre(idLivre);
            if(listLivre.size() == 0) {
                res = true;
            }
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return res;
    }
    
    @Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        boolean res = false;
        
		try{
            List<Emprunt> listemprunt = empruntDao.getListCurrentByMembre(membre.getId());
            Abonnement abo = membre.getAbonnement();
            int n_emprunts = listemprunt.size();
            switch(abo) {
                case BASIC :
                    if (n_emprunts < 2) {
                    	res = true;
                    } 
                    break;
                case PREMIUM :
                    if (n_emprunts < 5) {
                    	res = true;
                    } 
                    break;
                case VIP :
                    if (n_emprunts < 20) {
                    	res = true;
                    } 
                    break;
            }
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return res;
    }
    


}