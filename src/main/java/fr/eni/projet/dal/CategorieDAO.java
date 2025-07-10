package fr.eni.projet.dal;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;

import java.util.List;

public interface CategorieDAO {

	 /**
     * Ajoute une nouvelle catégorie dans la base de données.
     *
     * @param categorie : l'objet Categorie à insérer.
     */
	void ajouterCategorie(Categorie categorie);
	
	/**
     * Supprime une catégorie à partir de son identifiant.
     *
     * @param idCategorie : l'identifiant de la catégorie à supprimer.
     */
    void SupprimerCategorie(long idCategorie);
    
    
    
    /**
     * Récupère la liste de toutes les catégories disponibles.
     *
     * @return une liste d'objets Categorie.
     */
    List<Categorie> listerCategorie();
    
    

    /**
     * Récupère la catégorie associée à un article spécifique.
     *
     * @param idArticle : l'identifiant de l'article.
     * @return l'objet Categorie correspondant à l'article.
     */
    Categorie afficherCategorieArticle(long idArticle);
    
    

}
