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



public class LivreDaoImpl implements LivreDao{

	private static LivreDaoImpl instance;
	
	private LivreDaoImpl(){}
	
	public static LivreDaoImpl getInstance() {
		if(instance==null) {
			instance = new LivreDaoImpl();
		}
		return instance;
	}

	public String GET_LIST = "SELECT id, titre, auteur, isbn FROM livre;";
	public String GET_BY_ID = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
	public String CREATE = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	public String UPDATE = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	public String DELETE = "DELETE FROM livre WHERE id = ?;";
	public String COUNT = "SELECT COUNT(id) AS count FROM livre;";




	public List<Livre> getList() throws DaoException{
		List<Livre> result = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(GET_LIST)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                       Livre livre =  new Livre();
                       livre.setId(rs.getInt("id"));
                       livre.setTitre(rs.getString("titre"));
                       livre.setAuteur(rs.getString("auteur"));
                       livre.setIsbn(rs.getString("isbn"));
                       result.add(livre);
                   }
                return result;
        } catch (SQLException e){
            System.out.println(e);
        }
        return result;
	}




	public Livre getById(int id) throws DaoException{
		Livre livre =  new Livre();
		 try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(GET_BY_ID)){
                   preparedStatement.setString(1, String.valueOf(id));
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){

                   		if (rs.getInt("id") == id){
	                       	livre.setId(rs.getInt("id"));
	                       	livre.setTitre(rs.getString("titre"));
	                       	livre.setAuteur(rs.getString("auteur"));
	                       	livre.setIsbn(rs.getString("isbn"));

	                       	return livre;
                   		}
                      
                   }
        } catch (SQLException e){
            System.out.println(e);
        }
        return livre;
	}


	public int create(String titre, String auteur, String isbn) throws DaoException{
		int id = 0;
		try {
			System.out.println("create livre");
			Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(CREATE,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, String.valueOf(titre));
				preparedStatement.setString(2, String.valueOf(auteur));
				preparedStatement.setString(3, String.valueOf(isbn));
				preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
			    if(rs.next()){
			        id = rs.getInt(1);
			    }
        } catch (SQLException e){
            System.out.println(e);
        }

		return id;
	}



	public void update(Livre livre) throws DaoException{
		try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(UPDATE)){
				preparedStatement.setString(1, String.valueOf(livre.getTitre()));
				preparedStatement.setString(2, String.valueOf(livre.getAuteur()));
				preparedStatement.setString(3, String.valueOf(livre.getIsbn()));
				preparedStatement.setString(4, String.valueOf(livre.getId()));
				preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.executeQuery();
        } catch (SQLException e){
            System.out.println(e);
        }
	}



	public void delete(int id) throws DaoException{
		try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(DELETE)){
				preparedStatement.setString(1, String.valueOf(id));
								// preparedStatement.executeUpdate();

                   preparedStatement.executeQuery();
        } catch (SQLException e){
            System.out.println(e);
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