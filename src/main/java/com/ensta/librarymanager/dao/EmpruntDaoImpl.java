package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.time.LocalDate; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class EmpruntDaoImpl implements EmpruntDao{

	private static EmpruntDaoImpl instance;
	
	private EmpruntDaoImpl(){}
	
	public static EmpruntDaoImpl getInstance() {
		if(instance==null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}
	
	private final String GET_LIST = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private final String GET_LIST_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private final String GET_LIST_CURRENT_BY_MEMBRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private final String GET_LIST_CURRENT_BY_LIVRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private final String GET_BY_ID = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private final String CREATE = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private final String COUNT = "SELECT COUNT(id) AS count FROM emprunt;";
	private final String UPDATE = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";


	public List<Emprunt> getList() throws DaoException{
		Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
				Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null){
	        	 emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
                emprunts.add(emprunt);
            }

            preparedStatement.close();
            rs.close();
        }catch (SQLException e) {
			throw new DaoException("Erreur SQL Emprunt GetListCurrent", e);
        }catch (Exception e) {
			throw new DaoException("Une erreur est survenue dans Emprunt GetListCurrent",e);
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
        }
        return emprunts;
    }


	public List<Emprunt> getListCurrent() throws DaoException{
		Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
				Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null){
	        	 emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
                emprunts.add(emprunt);
            }

            preparedStatement.close();
            rs.close();
        }catch (SQLException e) {
			throw new DaoException("Erreur SQL Emprunt GetListCurrent", e);
        }catch (Exception e) {
			throw new DaoException("Une erreur est survenue dans Emprunt GetListCurrent",e);
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
        }
        return emprunts;
    }





	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();
        
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT_BY_MEMBRE);
            preparedStatement.setInt(1, idMembre);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
				Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                emprunts.add(emprunt);
            }

            preparedStatement.close();
            rs.close();
            
        }catch (SQLException e) {
			throw new DaoException("Erreur SQL", e);
        }catch (Exception e) {
			throw new DaoException("Une erreur est survenue.",e);
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
        }
        return emprunts;
	}


	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();
        
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LIST_CURRENT_BY_LIVRE);
            preparedStatement.setInt(1, idLivre);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
				Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                emprunts.add(emprunt);
            }

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Erreur SQL.", e);
        } catch (Exception e) {
            throw new DaoException("Une erreur est survenue.",e);
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            }
            catch (Exception e) {
                throw new DaoException("Erreur de connection.");
            }
        }
        return emprunts;
	}


	public Emprunt getById(int id) throws DaoException{
		Connection connection = null;
        Emprunt emprunt = null;       
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
                emprunt = new Emprunt(rs.getInt("idEmprunt"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
            }  

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Erreur SQL.", e);
        } catch (Exception e) {
            throw new DaoException("Une erreur est survenue.",e);
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            }catch (Exception e) {
                throw new DaoException("Erreur de connection.");
            }
        }
        return emprunt;
	}



	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
		Connection connection = null;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            preparedStatement.setDate(4, java.sql.Date.valueOf("NULL"));
            preparedStatement.executeUpdate();
            // ResultSet rs = preparedStatement.executeQuery();

            preparedStatement.close();
            // rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Erreur SQL create EmpruntDaoImpl.", e);
        }catch (Exception e) {
			throw new DaoException("Une erreur est survenue create EmpruntDaoImpl.",e);
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
		}
	}


	public void update(Emprunt emprunt) throws DaoException{
		Connection connection = null;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            Livre livre = emprunt.getIdLivre();
            Membre membre = emprunt.getIdMembre();
            preparedStatement.setInt(1, membre.getId());
            preparedStatement.setInt(2, livre.getId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();
            // ResultSet rs = preparedStatement.executeQuery();

            preparedStatement.close();
            // rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Erreur SQL.", e);
        }
        catch (Exception e) {
			throw new DaoException("Une erreur est survenue.",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
		}
	}

	public int count() throws DaoException{
		Connection connection = null;
        int count = 0;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT);            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }

            preparedStatement.close();
            rs.close();
            
		}catch (SQLException e) {
			throw new DaoException("Erreur SQL", e);
        }catch (Exception e) {
			throw new DaoException("Une erreur est survenue.",e);
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("Erreur de connection.");
			}
        }
        return count;
	}

	
	
}