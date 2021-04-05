package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MembreDaoImpl implements MembreDao{

  private static MembreDaoImpl instance;
  
  private MembreDaoImpl(){}
  
  public static MembreDaoImpl getInstance() {
    if(instance==null) {
      instance = new MembreDaoImpl();
    }
    return instance;
  }

	private final String GET_LIST = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private final String GET_BY_ID = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
	private final String CREATE = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private final String UPDATE = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
	private final String DELETE = "DELETE FROM membre WHERE id = ?;";
	private final String COUNT = "SELECT COUNT(id) AS count FROM membre;";



	public List<Membre> getList() throws DaoException{
		List<Membre> result = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                       Membre membre =  new Membre();
                       membre.setId(rs.getInt("id"));
                       //TODO !!!!!
                       membre.setNom(rs.getString("nom"));
                       membre.setPrenom(rs.getString("prenom"));
                       membre.setAdresse(rs.getString("adresse"));
                       membre.setEmail(rs.getString("email"));
                       membre.setTelephone(rs.getString("telephone"));
                       membre.setAbonnement(Abonnement.valueOf(rs.getString("abonnement")));
                       result.add(membre);
                   }
                return result;
        } catch (SQLException e){
            System.out.println(e);
        }
        return result;
	}




	public Membre getById(int id) throws DaoException{
    Membre selectedMembre = new Membre();
    try {
      Connection connection =  ConnectionManager.getConnection();
      PreparedStatement readPreparedStatement = connection.prepareStatement(GET_BY_ID);
      readPreparedStatement.setString(1, String.valueOf(id));
      ResultSet rs = readPreparedStatement.executeQuery();
      if(rs.next()){
        Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));
      
      selectedMembre.setId(rs.getInt("id"));
      selectedMembre.setNom(rs.getString("nom"));
      selectedMembre.setPrenom(rs.getString("prenom"));
      selectedMembre.setAdresse(rs.getString("adresse"));
      selectedMembre.setEmail(rs.getString("email"));
      selectedMembre.setTelephone(rs.getString("telephone"));
      selectedMembre.setAbonnement(abonnement);
      }

      readPreparedStatement.close();
    }
    catch (SQLException e) {;
      throw new DaoException("Problème lors de l'exécution de la requête",e);
    }
    catch (NumberFormatException e) {
      throw new DaoException("Problème de parsing");
    }
    catch (Exception e) {
      throw new DaoException("Erreur de connection",e);
    }
    
    return(selectedMembre);
  }

	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
    ResultSet rs = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int id = -1;
    try {
      connection = ConnectionManager.getConnection();
      preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, nom);
      preparedStatement.setString(2, prenom);
      preparedStatement.setString(3, adresse);
      preparedStatement.setString(4, email);
      preparedStatement.setString(5, telephone);
      preparedStatement.setString(6, String.valueOf("BASIC"));
      preparedStatement.executeUpdate();
            preparedStatement.executeQuery();

      rs = preparedStatement.getGeneratedKeys();
      if(rs.next()){
        id = rs.getInt(1);        
      }

    }
    catch (SQLException e) {;
      throw new DaoException("Problème lors de l'exécution de la requête",e);
    }
    catch (Exception e) {
      throw new DaoException("Erreur de connection",e);
    }
    finally {
      try {
        if(connection!=null) {
          connection.close();
        }
      }
      catch (Exception e) {
        throw new DaoException("La connection n'a pas pu être refermée...",e);
      }
    }
    
    return id;
  }


	public void update(Membre membre) throws DaoException{
    Connection connection = null;
    try {
      connection = ConnectionManager.getConnection();
      connection.setAutoCommit(false);
            
      PreparedStatement updateStatement = null;
      updateStatement = connection.prepareStatement(UPDATE);
      updateStatement.setString(1, membre.getNom());
      updateStatement.setString(2, membre.getPrenom());
      updateStatement.setString(3, membre.getAdresse());
      updateStatement.setString(4, membre.getEmail());
      updateStatement.setString(5, membre.getTelephone());
      Abonnement abonnement = membre.getAbonnement();
      updateStatement.setString(6, abonnement.name());
      updateStatement.setString(7, String.valueOf(membre.getId()));
      
      updateStatement.executeUpdate();
      updateStatement.close();
      
      connection.commit();
    }
    catch (SQLException e) {;
      throw new DaoException("Problème lors de l'exécution de la requête",e);
    }
    catch (Exception e) {
      throw new DaoException("Erreur de connection",e);
    }
    finally {
      try {
        if(connection!=null) {
          connection.close();
        }
      }
      catch (Exception e) {
        throw new DaoException("La connection n'a pas pu être refermée...",e);
      }
    }
  }

	public void delete(int id) throws DaoException{
    Connection connection = null;
    try {
      connection = ConnectionManager.getConnection();
      connection.setAutoCommit(false);
            
      PreparedStatement updateStatement = null;
      updateStatement = connection.prepareStatement(DELETE);
      updateStatement.setString(1, String.valueOf(id));
      
      updateStatement.executeUpdate();
      updateStatement.close();
      
      connection.commit();
    }
    catch (SQLException e) {;
      throw new DaoException("Problème lors de l'exécution de la requête",e);
    }
    catch (Exception e) {
      throw new DaoException("Erreur de connection",e);
    }
    finally {
      try {
        if(connection!=null) {
          connection.close();
        }
      }
      catch (Exception e) {
        throw new DaoException("La connection n'a pas pu être refermée...",e);
      }
    }
  }
  
	public int count() throws DaoException{
    int result = 0;
    try {Connection conn = ConnectionManager.getConnection();
                   PreparedStatement preparedStatement = conn.prepareStatement(COUNT);
                       ResultSet rs = preparedStatement.executeQuery();
                       if (rs.next()){
                           result =  rs.getInt("count");;
                       }

                    return result;
            } catch (SQLException e){
                System.out.println(e);
            }
            return result;

  }
  

}