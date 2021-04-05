package com.ensta.librarymanager.modele;

import java.time.LocalDate; 



public class Emprunt{

	private int id;
	private Membre idMembre;
	private Livre idLivre;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;

	public Emprunt(int id, Membre idMembre, Livre idLivre, LocalDate dateEmprunt, LocalDate dateRetour){
		this.id = id;
		this.idMembre = idMembre;
		this.idLivre = idLivre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}
	public Emprunt(int id, Membre idMembre, Livre idLivre, LocalDate dateEmprunt){
		this.id = id;
		this.idMembre = idMembre;
		this.idLivre = idLivre;
		this.dateEmprunt = dateEmprunt;
	}

	public Emprunt(){}


	public int getId(){
		return this.id;
	}
	
	public Membre getIdMembre(){
		return this.idMembre;
	}
	public Livre getIdLivre(){
		return this.idLivre;
	}
	public LocalDate getDateEmprunt(){
		return this.dateEmprunt;
	}
	public LocalDate getDateRetour(){
		return this.dateRetour;
	}

	public void setId(int id){
		this.id = id;
	}
	public void setIdMembre( Membre idMembre){
		this.idMembre = idMembre;
	}
	public void setIdLivre(Livre idLivre){
		this.idLivre = idLivre;
	}
	public void setDateEmprunt(LocalDate dateEmprunt){
		this.dateEmprunt = dateEmprunt ;
	}
	public void setDateRetour(LocalDate dateRetour){
		this.dateRetour = dateRetour ;
	}

	@Override
    public String toString() {
        return "Emprunt [id=" + id + ", idMembre=" + idMembre.getId() + ", idLivre=" + idLivre.getId() + ", dateEmprunt=" + dateEmprunt.getChronology() + ", dateRetour=" + dateRetour.getChronology() + "]";
    }



}