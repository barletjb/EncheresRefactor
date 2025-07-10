package fr.eni.projet.bll;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.exception.BusinessException;

import java.util.List;

public interface EnchereService {

	
	int countArticles();
	
	
	
	/**
	 * Crée un nouvel article à mettre en vente. Etat de la vente par défaut à 'CR'
	 * (CRée)
	 * 
	 * @param article l'objet Article à créer.
	 * @throws BusinessException si une erreur survient lors de la création de
	 *                           l'article.
	 */
	void creationArticle(Article article) throws BusinessException;

	/**
	 * Démarre la vente d'un article, rendant l'enchère possible pour les
	 * utilisateurs. Change l'etat de la vente de 'CR'(CRée) à 'EC'(En Cours)
	 * Indique le début des enchères.
	 * 
	 * @param idArticle l'identifiant de l'article mis en vente.
	 * @throws BusinessException.
	 */
	void debuterVente(long idArticle) throws BusinessException;

	/**
	 * Supprime la vente d'un article. Cette opération peut être utilisée en cas
	 * d'annulation par le vendeur. Ne peut pas être executée si état vente en 'EC'
	 * (En Cours).
	 * 
	 * @param idArticle l'identifiant de l'article à supprimer.
	 * @throws BusinessException 
	 */
	void supprimerVente(long idArticle) throws BusinessException;

	/**
	 * Permet à un utilisateur de placer une enchère sur un article spécifique.
	 * 
	 * @param idArticle     l'identifiant de l'article concerné par l'enchère.
	 * @param idUtilisateur l'identifiant de l'utilisateur plaçant l'enchère.
	 * @param value         le montant proposé pour l'enchère.
	 * @throws BusinessException
	 */
	void encherir(long idArticle, long idUtilisateur, int value) throws BusinessException;

	/**
	 * Détermine et enregistre l'utilisateur ayant remporté l'enchère sur un article
	 * donné (c'est à dire celui ayant proposé la valeur la plus haute). Change
	 * l'etat de la vente de 'EC'(En cours) à 'RE'(Retrait Effectué) Indique la fin
	 * des enchères.
	 * 
	 * @param idArticle l'identifiant de l'article concerné.
	 * @throws BusinessException
	 */
	void remporterVente(long idArticle) throws BusinessException;

	/**
	 * Clôture la vente d’un article, empêchant toute nouvelle enchère. Change
	 * l'etat de la vente de 'ET'(Etat Terminé) à 'RE'(Retrait Effectué) Indique la
	 * clôture de la vente.
	 * 
	 * @param idArticle l'identifiant de l'article à clôturer.
	 * @throws BusinessException si l'article ne peut pas être clôturé (ex. : vente
	 *                           déjà terminée).
	 */
	void clotureArticle(long idArticle) throws BusinessException;
	
	

	/**
	 * Récupère la liste complète des ventes disponibles sur la plateforme.
	 * 
	 * @return une liste d'objets représentant toutes les ventes d'articles en
	 *         cours.
	 */
	List<Article> consulterAllVentes();

	/**
	 * Récupère la liste complète des catégories disponibles sur la plateforme.
	 * 
	 * @return une liste d'objets représentant toutes les catégories de la
	 *         plateforme.
	 */
	List<Categorie> consulterAllCategories();

	/**
	 * Filtre les articles en fonction des critères fournis.
	 * 
	 * @param filtreNomArticle   filtre sur le nom de l'article (peut être vide).
	 * @param categorieFiltree   identifiant de la catégorie (0 pour "toutes").
	 * @param encheresEnCours    "idUtilisateur" si l'utilisateur veut voir les
	 *                           enchères en cours.
	 * @param mesEncheres        "idUtilisateur" si l'utilisateur veut voir ses
	 *                           propres enchères.
	 * @param encheresRemportees "idUtilisateur" si l'utilisateur veut voir les
	 *                           enchères qu'il a remportées.
	 * @param ventesEnCours      "idUtilisateur" si l'utilisateur veut voir ses
	 *                           ventes en cours.
	 * @param ventesEnAttente    "idUtilisateur" si l'utilisateur veut voir ses
	 *                           ventes à venir.
	 * @param ventesTerminees    "idUtilisateur" si l'utilisateur veut voir ses
	 *                           ventes terminées.
	 * @param page    			La page actuelle où l'on est situé dans la liste d'articles
	 * @param pageSize    		Le nombre d'articles max par page
	 * @return une liste d'articles correspondant aux critères de recherche.
	 */
	List<Article> filtrerRecherche(String filtreNomArticle, int categorieFiltree, String encheresEnCours,
			int mesEncheres, int encheresRemportees, int ventesEnCours, int ventesEnAttente, int ventesTerminees);

	/**
	 * Récupère la liste de toutes les enchères pour un article donné.
	 * 
	 * @param idArticle l'identifiant de l'article concerné.
	 * @return une liste d'encheres associés à cet article.
	 */
	List<Enchere> consulterEncheres(long idArticle);
	
	List<Article> getTopTrendingArticles();
	
	List<Article> getArticlesByPage(int page, int pageSize);
	
	

	/**
	 * Récupère les détails complets d'un article en vente.
	 * 
	 * @param idArticle l'identifiant de l'article à consulter.
	 * @return un Article contenant toutes les informations détaillées de ce
	 *         dernier.
	 * @throws BusinessException
	 */
	Article detailVente(long idArticle) throws BusinessException;

	/**
	 * Récupère l'enchère la plus élevée actuellement placée pour un article donné.
	 * 
	 * @param idArticle l'identifiant de l'article concerné.
	 * @return une Enchere représentant l'enchère maximale, ou null si aucune
	 *         enchère n'est encore placée.
	 * @throws BusinessException 
	 */

	Enchere consulterEnchereMax(long idArticle) throws BusinessException;	

}
