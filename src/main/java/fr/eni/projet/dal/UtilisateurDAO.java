package fr.eni.projet.dal;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;

import java.util.List;

public interface UtilisateurDAO {
	
	/**
     * Récupère le nombre de total crédits disponibles pour un utilisateur.
     * 
     * @param idUtilisateur : identifiant de l'utilisateur.
     * @return le montant total de crédits de l'utilisateur.
     */
	int consulterNbreCredit(long idUtilisateur);

	
	
	/**
     * Crée un nouvel utilisateur dans la base.
     * 
     * @param u :objet Utilisateur à créer.
     */
    void creerCompte(Utilisateur u);
    
    /**
     * Met à jour les informations d’un utilisateur (hors mot de passe, crédit...).
     * 
     * @param u : utilisateur avec les nouvelles données
     */
    void updateCompte(Utilisateur u);
    
    /**
     * Désactive un compte utilisateur temporairement. N.B : A COMPLETER!
     * 
     * @param idUtilisateur : identifiant de l'utilisateur à désactiver.
     */
    void desactiverCompte(long idUtilisateur);
    
    /**
     * Supprime un compte utilisateur (et ses enchères) de manière définitive. N.B A compléter. 
     * 
     * @param idUtilisateur : identifiant de l'utilisateur à supprimer
     */
    void supprimerCompte(long idUtilisateur);
   
    void crediter(long idUtiisateur, int montant);
    
    /**
     * Créditer le vendeur/utilisateurBattu de l’article après/pendant la fin de l’enchère.
     * 
     * @param idUtilisateur identifiant du vendeur
     * @param idArticle identifiant de l’article vendu
     */
	void crediterVendeur(long idUtilisateur, long idArticle);
	
	/**
     * Débite le crédit d’un utilisateur après une enchère.
     * 
     * @param idUtilisateur : identifiant de l'utilisateur
     * @param montant : montant à débiter
     */
    void debiter(long idUtilisateur, int montant);
    
    
    
    /**
     * Retourne tous les comptes utilisateurs de la base.
     * 
     * @return liste des utilisateurs
     */
    List<Utilisateur> afficherComptes();
    
    
    
    /**
     * Récupère un mot de passe à partir de l'email.
     * 
     * @param email : adresse email associée.
     * @return le mot de passe oublé correspondant
     */
    String motDePasseOublie(String email);
    
    
    
    /**
     * Récupère un utilisateur par pseudo et vérifie son mot de passe.
     * 
     * @param pseudo : pseudo de l'utilisateur
     * @param motDePasse : le mot de passe fourni
     * @return utilisateur si le mot de passe correspond, sinon null
     */
    Utilisateur connecterCompte(String pseudo, String motDePasse);
    
    /**
     * Récupère un utilisateur par son identifiant.
     * 
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return l'utilisateur correspondant
     */
    Utilisateur consulterCompte(long idUtilisateur);
    
    /**
     * Récupère un utilisateur par son pseudo.
     * 
     * @param pseudo : pseudo de l'utilisateur
     * @return l'utilisateur correspondant
     */
    Utilisateur consulterCompte(String pseudo);
    
    
    /**
     * Vérifie si un utilisateur existe en base à partir de son identifiant.
     * 
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return true s'il existe, false sinon
     */
    boolean isUtilisateurInBDD(long idUtilisateur);
    
    /**
     * Vérifie si un utilisateur existe en base à partir de son pseudo.
     * 
     * @param pseudo : pseudo de l'utilisateur
     * @return true s'il existe, false sinon
     */
    boolean isUtilisateurInBDD(String pseudo); 
    
    /**
     * Vérifie si un email est déjà présent dans la base.
     * 
     * @param email : adresse email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean emailExist(String email);
    
}
