package fr.eni.projet.bll;

import fr.eni.projet.bo.*;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.dal.ArticleDAO;
import fr.eni.projet.dal.CategorieDAO;
import fr.eni.projet.dal.EnchereDAO;
import fr.eni.projet.dal.RetraitDAO;
import fr.eni.projet.dal.UtilisateurDAO;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.CodeErreur;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

	private ArticleDAO articleDAO;
	private EnchereDAO enchereDAO;
	private CategorieDAO categorieDAO;
	private RetraitDAO retraitDAO;
	private UtilisateurDAO utilisateurDAO;

	public static final String ETAT_CREE = "CR";
	public static final String ETAT_EN_COURS = "EC";
	public static final String ETAT_TERMINE = "ET";
	public static final String ETAT_RETRAIT_EFFECTUE = "RE";

	public EnchereServiceImpl(ArticleDAO articleDAO, EnchereDAO enchereDAO, CategorieDAO categorieDAO,
			RetraitDAO retraitDAO, UtilisateurDAO utilisateurDAO) {
		this.articleDAO = articleDAO;
		this.enchereDAO = enchereDAO;
		this.categorieDAO = categorieDAO;
		this.retraitDAO = retraitDAO;
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public int countArticles() {
		return articleDAO.countArticles();
	}

	@Override

	public void creationArticle(Article article) throws BusinessException {
		

		if (article.getIdArticle() == 0) {
			long id = articleDAO.ajouterArticle(article);
			article.setIdArticle(id);

			Retrait retrait = new Retrait();
			retrait.setVille(article.getRetrait().getVille());
			retrait.setRue(article.getRetrait().getRue());
			retrait.setCodePostal(article.getRetrait().getCodePostal());
			retrait.setArticle(article);
			retraitDAO.creerRetrait(retrait);
		} else {
			articleDAO.ajouterArticle(article);
		}
	}

	@Override
	public void debuterVente(long idArticle) throws BusinessException {

		Article article = getArticleOrThrow(idArticle);

		if (article.getEtatVente().equals(ETAT_CREE)) {

			article.setEtatVente(ETAT_EN_COURS);
			articleDAO.updateEtatArticle(idArticle, ETAT_EN_COURS);
		} else {
			throw new BusinessException(CodeErreur.VENTE_NON_DEBUTEE);
		}
	}

	@Override
	public void supprimerVente(long idArticle) throws BusinessException {

		if (articleDAO.hasArticle(idArticle)) {
			this.articleDAO.supprimerArticle(idArticle);
		} else {

			throw new BusinessException(CodeErreur.ARTICLE_INEXISTANT);
		}
	}

	@Override
	public void encherir(long idArticle, long idUtilisateur, int montant) throws BusinessException {

		BusinessException be = new BusinessException();

		// Récupération de l'article (exception si non trouvé)
		Article article = getArticleOrThrow(idArticle);

		// Vérification que la vente est bien en cours
		if (!ETAT_EN_COURS.equals(article.getEtatVente())) {
			be.add(CodeErreur.VENTE_NON_DEBUTEE);
			throw be;
		}

		Enchere enchereActuelle = enchereDAO.enchereMax(idArticle);
		int montantActuel = (enchereActuelle != null) ? enchereActuelle.getMontantEnchere() : 0;

		if (montant <= montantActuel) {
			be.add(CodeErreur.ENCHERE_TROP_BASSE);
			throw be;
		}

		Utilisateur utilisateur = this.utilisateurDAO.consulterCompte(idUtilisateur);

		if (utilisateur.getCredit() < montant) {
			be.add(CodeErreur.CREDITS_INSUFFISANTS);
			throw be;
		}

		// Remboursement de l'ancien enchérisseur si présent
		if (enchereActuelle != null && enchereActuelle.getUtilisateur() != null) {
			Utilisateur ancienEncherisseur = enchereActuelle.getUtilisateur();
			this.utilisateurDAO.crediterVendeur(ancienEncherisseur.getIdUtilisateur(), idArticle);
		}

		// Création de la nouvelle enchère
		Enchere newEnchere = new Enchere(utilisateur, article, LocalDateTime.now(), montant);
		this.enchereDAO.creerEnchere(newEnchere);

		// Débit du nouveau enchérisseur
		this.utilisateurDAO.debiter(idUtilisateur, montant);

	}

	@Override
	public void remporterVente(long idArticle) throws BusinessException {

		Article article = getArticleOrThrow(idArticle);

		switch (article.getEtatVente()) {

		case ETAT_CREE:

			throw new BusinessException(CodeErreur.VENTE_NON_DEBUTEE);

		case ETAT_EN_COURS:
			article.setEtatVente(ETAT_TERMINE);
			articleDAO.updateEtatArticle(idArticle, ETAT_TERMINE);
			utilisateurDAO.crediterVendeur(article.getUtilisateur().getIdUtilisateur(), idArticle);
			break;

		case ETAT_TERMINE:

			throw new BusinessException(CodeErreur.VENTE_DEJA_TERMINEE);

		case ETAT_RETRAIT_EFFECTUE:

			throw new BusinessException(CodeErreur.VENTE_DEJA_CLOTUREE);

		default:
			
			throw new BusinessException(CodeErreur.ETAT_VENTE_INVALIDE);
		}
	} 

	
	@Override
	public void clotureArticle(long idArticle) throws BusinessException {

		Article article = getArticleOrThrow(idArticle);

		if (ETAT_TERMINE.equals(article.getEtatVente())) {
			articleDAO.updateEtatArticle(idArticle, ETAT_RETRAIT_EFFECTUE);
		} else {
			throw new BusinessException(CodeErreur.VENTE_DEJA_CLOTUREE);
		}
	}

	@Override
	public List<Article> consulterAllVentes() {

		List<Article> articles = this.articleDAO.afficherArticles();

		for (Article article : articles) {
			article.setUtilisateur(utilisateurDAO.consulterCompte(article.getUtilisateur().getIdUtilisateur()));
		}

		return articles;
	}

	@Override
	public List<Categorie> consulterAllCategories() {

		return this.categorieDAO.listerCategorie();
	}

	@Override
	public List<Article> filtrerRecherche(String filtreNomArticle, int categorieFiltree, String encheresEnCours,
			int mesEncheres, int encheresRemportees, int ventesEnCours, int ventesEnAttente, int ventesTerminees) {

		List<Article> articles = this.articleDAO.afficherArticlesFiltres(filtreNomArticle, categorieFiltree,
				encheresEnCours, mesEncheres, encheresRemportees, ventesEnCours, ventesEnAttente, ventesTerminees);

		for (Article article : articles) {
			article.setUtilisateur(utilisateurDAO.consulterCompte(article.getUtilisateur().getIdUtilisateur()));
		}

		return articles;
	}

	@Override
	public List<Enchere> consulterEncheres(long idArticle) {

		return this.enchereDAO.afficherEncheres(idArticle);
	}

	@Override
	public List<Article> getTopTrendingArticles() {

		return this.articleDAO.getTopTrendingArticles();
	}

	@Override
	public List<Article> getArticlesByPage(int page, int pageSize) {

		
		List<Article> articles = this.articleDAO.getArticlesByPage(page, pageSize);

		for (Article article : articles) {
			article.setUtilisateur(utilisateurDAO.consulterCompte(article.getUtilisateur().getIdUtilisateur()));
		}
		
		return articles;

	}

	@Override
	public Article detailVente(long idArticle) throws BusinessException {

		Article article = getArticleOrThrow(idArticle);

		article.setCategorie(categorieDAO.afficherCategorieArticle(idArticle));
		article.setUtilisateur(utilisateurDAO.consulterCompte(article.getUtilisateur().getIdUtilisateur()));
		article.setRetrait(retraitDAO.afficherRetrait(idArticle));

		return article;
	}

	@Override
	public Enchere consulterEnchereMax(long idArticle) throws BusinessException {

		Enchere enchere = enchereDAO.enchereMax(idArticle);

		if (enchere != null) {

			return enchere;
		} else {
			throw new BusinessException(CodeErreur.AUCUNE_ENCHERE_TROUVEE);
		}
	}

	/**
	 * Récupère un article à partir de son identifiant. Si l'article n'existe pas en
	 * base de données, une {@link BusinessException} est levée avec le code
	 * d'erreur {@link CodeErreur#ARTICLE_INEXISTANT}.
	 *
	 * @param idArticle L'identifiant de l'article à récupérer.
	 * @return L'article correspondant à l'identifiant fourni.
	 * @throws BusinessException Si aucun article ne correspond à l'identifiant.
	 */
	private Article getArticleOrThrow(long idArticle) throws BusinessException {

		Article article = articleDAO.afficherArticle(idArticle);
		if (article == null) {

			throw new BusinessException(CodeErreur.ARTICLE_INEXISTANT);
		}
		return article;

	}

}