package fr.eni.projet.dal;

import fr.eni.projet.bo.Enchere;

import java.util.List;

public interface EnchereDAO {

	/**
     * Crée une nouvelle enchère en base de données.
     * *
     * @param enchere : l'objet Enchère à enregistrer
     */
    void creerEnchere(Enchere enchere);
    
    
    
    /**
     * Récupère toutes les enchères pour un article donné.
     * *
     * @param idArticle : identifiant de l'article.
     * @return liste des enchères sur l'article.
     */
    List<Enchere> afficherEncheres(long idArticle);
    
    
    
    /**
     * Récupère l'enchère la plus élevée pour un article donné.
     * *
     * @param idArticle : identifiant de l'article.
     * @return l'enchère maximale ou null s'il n'y en a aucune
     */
    Enchere enchereMax(long idArticle);
}
