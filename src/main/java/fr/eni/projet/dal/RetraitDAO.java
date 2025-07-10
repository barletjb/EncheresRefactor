package fr.eni.projet.dal;

import fr.eni.projet.bo.Retrait;

public interface RetraitDAO {
	
	 /**
     * Enregistre une nouvelle adresse de retrait pour un article.
     *
     * @param retrait : l'objet Retrait contenant les informations du point de Retrait.
     */
    void creerRetrait(Retrait retrait);
    
    
    
    /**
     * Récupère les informations de retrait associées à un article donné.
     *
     * @param idArticle : l'identifiant de l'article concerné.
     * @return un objet Retrait contenant les informations du point de retrait.
     */
    Retrait afficherRetrait(long idArticle);

}
