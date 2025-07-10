package fr.eni.projet.bll;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;

public interface UtilisateurService {

	/**
	 * Crée un nouvel utilisateur dans la BDD.
	 * 
	 * @param utilisateur : l'objet Utilisateur contenant les informations du
	 * nouvel utilisateur à enregistrer.
	 */
	void creerUtilisateur(Utilisateur utilisateur) throws BusinessException;

	/**
	 * Permet de modifier les informations d'un utilisateur existant.
	 * 
	 * @param utilisateur : l'objet Utilisateur à modifier.
	 */
	void modifierProfil(Utilisateur utilisateur) throws BusinessException;
	
	/**
	 * Désactive temporairement un utilisateur sans le supprimer.
	 * 
	 * @param idUtilisateur : l’identifiant de l’utilisateur à désactiver.
	 * @throws BusinessException.
	 */
	void desactiverUtilisateur(long idUtilisateur) throws BusinessException;

	/**
	 * Supprime un utilisateur du système.
	 * 
	 * @param idUtilisateur : l'identifiant unique de l'utilisateur à supprimer.
	 */
	void supprimerUtilisateur(long idUtilisateur) throws BusinessException;
	
	

	/**
	 * Récupère les informations d'un utilisateur à partir de son ID
	 * afin de gérèr l'affichage de ce dernier.
	 * 
	 * @param idUtilisateur : l'identifiant unique de l'utilisateur à afficher.
	 * @return un objet Utilisateur correspondant à l'identifiant fourni.
	 */
	Utilisateur afficherProfil(long idUtilisateur) throws BusinessException;

	/**
	 * Récupère les informations d'un utilisateur à partir de son PSEUDO
	 * afin de gérèr l'affichage de ce dernier.
	 * 
	 * @param pseudo : pseudo unique de l'utilisateur à afficher.
	 * @return un objet Utilisateur correspondant au pseudo fourni.
	 */
	Utilisateur afficherProfil(String pseudo) throws BusinessException;
	
	/**
	 * Authentifie un utilisateur à partir de son pseudo et mot de passe.
	 * 
	 * @param pseudo 	 : le pseudo de l’utilisateur.
	 * @param motDePasse :le mot de passe fourni.
	 * @return un objet Utilisateur authentifié.
	 * @throws BusinessException en cas d’identifiants invalides.
	 */
	Utilisateur connecterUtilisateur(String pseudo, String motDePasse) throws BusinessException;
	
	

	/**
	 * Vérifie si un pseudo existe déjà.
	 * 
	 * @param pseudo : le pseudo à vérifier.
	 * @return true si le pseudo existe, false sinon.
	 */
	boolean pseudoExiste(String pseudo);

	/**
	 * Vérifie si un email est déjà utilisé.
	 * 
	 * @param email : l’email à vérifier.
	 * @return true si l’email existe, false sinon.
	 */
	boolean emailExiste(String email);


	void achatCredit(long idUtilisateur, int montant);

}
