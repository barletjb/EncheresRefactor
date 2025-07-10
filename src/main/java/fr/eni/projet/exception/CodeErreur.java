package fr.eni.projet.exception;

public enum CodeErreur {
	
	VENTE_NON_DEBUTEE("Vente non débutée"),
    VENTE_EN_COURS("Vente toujours en cours"),
    VENTE_DEJA_TERMINEE("Vente déjà terminée"),
    VENTE_DEJA_CLOTUREE("Vente déjà clôturée"),
    ETAT_VENTE_INVALIDE("Etat vente inconnu"),
	ARTICLE_INEXISTANT("Aucun article trouvé"),
    CREDITS_INSUFFISANTS("Crédits insuffisants"),
    ENCHERE_TROP_BASSE("Saisir une enchère plus élevée"),
    AUCUNE_ENCHERE_TROUVEE("Aucune enchère trouvée"),
    UTILISATEUR_INEXISTANT ("L'utilisateur n'existe pas"),
    MDP_INCORRECT("Le mot de passe ou user ne correspond pas");


    private final String message;

    CodeErreur(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
