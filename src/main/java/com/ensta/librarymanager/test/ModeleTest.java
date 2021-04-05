package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;

public class ModeleTest {

    public static void main(String[] args) {
        testLivre();
		testMembre();
		testEmprunt();
    }
	
	public static void testLivre(){
		Livre livre = new Livre();
		System.out.println(livre);
	}
	
	public static void testMembre(){
		Membre membre = new Membre();
		System.out.println(membre);
	}
	
	public static void testEmprunt(){
		Emprunt emprunt = new Emprunt();
		System.out.println(emprunt);
	}

}