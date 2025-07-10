package fr.eni.projet.dal;

import fr.eni.projet.bo.Article;

import java.util.List;

public interface ArticleDAO {
	
	/**
	 * Ajoute un nouvel article au système.
	 * 
	 * @param article : l’objet Article à enregistrer.
	 * @return l’identifiant généré pour l’article ajouté.
	 */
	long ajouterArticle(Article article);
	
	
	
	/**
	 * Met à jour l’état d’un article ("EC", "ET", "RE").
	 * 
	 * @param idArticle : l’identifiant de l’article concerné.
	 * @param etat le nouvel état à appliquer à l’article.
	 */
	void updateEtatArticle(long idArticle, String etat);

	/**
	 * Supprime un article à partir de son identifiant.
	 * 
	 * @param idArticle : l’identifiant de l’article à supprimer.
	 */
	void supprimerArticle(long idArticle);
	
	
	
	/**
	 * Récupère la liste de tous les articles.
	 * 
	 * @return une liste d’objets Article.
	 */
	List<Article> afficherArticles();
	
	/**
	 * Récupère la liste des articles filtrés selon plusieurs critères combinables :
	 * - Nom partiel de l’article.
	 * - Catégorie.
	 * - État de vente (ex. : "EC" = En Eours, "ET" = Enchère Terminée, "CR" = Créée).
	 * - Enchères placées par l'utilisateur connecté.
	 * - Enchères remportées par l'utilisateur (état "ET").
	 * - Ventes en cours, en attente ou terminées appartenant à l'utilisateur.
	 * Un filtre est ignoré s’il est null (String) ou égal à 0 (int).
	 *
	 * @param filtreNomArticle    : filtre sur le nom de l’article (recherche partielle).
	 * @param categorieFiltree    : identifiant de la catégorie (0 pour toutes).
	 * @param encheresEnCours     : état "EC" pour les ventes en cours (null pour ignorer).
	 * @param mesEncheres         : id de l’utilisateur pour filtrer ses enchères (0 pour ignorer).
	 * @param encheresRemportees  : id de l’utilisateur pour filtrer ses articles remportés (0 pour ignorer).
	 * @param ventesEnCours       : id de l’utilisateur pour ses ventes en cours (0 pour ignorer).
	 * @param ventesEnAttente     : id de l’utilisateur pour ses ventes à venir (état "CR") (0 pour ignorer).
	 * @param ventesTerminees     : id de l’utilisateur pour ses ventes terminées (état "ET") (0 pour ignorer).
	 * @return une liste d’articles correspondant aux critères donnés.
	 */
	List<Article> afficherArticlesFiltres(String filtreNomArticle, int categorieFiltree, String encheresEnCours,
			int mesEncheres, int encheresRemportees, int ventesEnCours, int ventesEnAttente, int ventesTerminees);

	/**
	 * Récupère les 5 articles où il y a le plus d'enchère.
	 * 
	 * @return une liste des articles les plus tendances.
	 */
	List<Article> getTopTrendingArticles();
	
	

	List<Article> getArticlesByPage(int page, int pageSize);

	int countArticles();
	

	/**
	 * Récupère un article à partir de son identifiant.
	 * 
	 * @param idArticle : l’identifiant de l’article.
	 * @return l’objet Article correspondant.
	 */
	Article afficherArticle(long idArticle);
	
	

	/**
	 * Vérifie si un article existe dans le système.
	 * 
	 * @param idArticle : l’identifiant de l’article.
	 * @return true si l’article existe, false sinon.
	 */
	boolean hasArticle(long idArticle);

	

}
