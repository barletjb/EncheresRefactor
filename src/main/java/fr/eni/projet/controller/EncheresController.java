package fr.eni.projet.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.projet.bo.Retrait;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.projet.bll.EnchereService;
import fr.eni.projet.bll.filestorage.ImageService;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;
import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

@SessionAttributes({ "utilisateurEnSession", "categoriesEnSession" })
@Controller
public class EncheresController {

	private EnchereService enchereService;
	private ImageService imageService;

	public EncheresController(EnchereService enchereService, ImageService imageService) {
		this.enchereService = enchereService;
		this.imageService = imageService;
	}

	@GetMapping({ "/", "/index", "/encheres" })
	public String goToIndex(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		LocalDateTime today = LocalDateTime.now();

		int pageSize = 6;
		List<Article> articles = enchereService.getArticlesByPage(page, pageSize);
		int totalArticles = enchereService.countArticles();
		int totalPages = (int) Math.ceil((double) totalArticles / pageSize);

		Map<Long, String> couleurParArticle = new HashMap<>();

		for (Article a : articles) {
			long joursRestants = ChronoUnit.DAYS.between(today, a.getDateFinEncheres());
			String couleur;
			if (joursRestants < 2) {
				couleur = "red";
			} else if (joursRestants < 7) {
				couleur = "orange";
			} else {
				couleur = "";
			}
			couleurParArticle.put(a.getIdArticle(), couleur);
		}

		model.addAttribute("articles", articles);
		model.addAttribute("couleurs", couleurParArticle);

		List<Article> trendingArticles = enchereService.getTopTrendingArticles();
		model.addAttribute("trendingArticles", trendingArticles);
		model.addAttribute("pagination", "on");
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "index";
	}

	@PostMapping("/encheres/rechercher")
	public String filtrerRecherche(@RequestParam(name = "filtreNom", required = false) String filtreNomArticle,
			@RequestParam(name = "categories", required = false, defaultValue = "0") int categorieFiltree,
			@RequestParam(name = "encheresEnCours", required = false) String encheresEnCours,
			@RequestParam(name = "mesEncheres", required = false, defaultValue = "0") int mesEncheres,
			@RequestParam(name = "encheresRemportees", required = false, defaultValue = "0") int encheresRemportees,
			@RequestParam(name = "ventesEnCours", required = false, defaultValue = "0") int ventesEnCours,
			@RequestParam(name = "ventesEnAttente", required = false, defaultValue = "0") int ventesEnAttente,
			@RequestParam(name = "ventesTerminees", required = false, defaultValue = "0") int ventesTerminees,
			Model model) {
		LocalDateTime today = LocalDateTime.now();

		List<Article> listeFiltree = this.enchereService.filtrerRecherche(filtreNomArticle, categorieFiltree,
				encheresEnCours, mesEncheres, encheresRemportees, ventesEnCours, ventesEnAttente, ventesTerminees);

		Map<Long, String> couleurParArticle = new HashMap<>();

		for (Article a : listeFiltree) {
			long joursRestants = ChronoUnit.DAYS.between(today, a.getDateFinEncheres());
			String couleur;
			if (joursRestants < 2) {
				couleur = "red";
			} else if (joursRestants < 7) {
				couleur = "orange";
			} else {
				couleur = "";
			}
			couleurParArticle.put(a.getIdArticle(), couleur);
		}

		model.addAttribute("articles", listeFiltree);
		model.addAttribute("couleurs", couleurParArticle);
		model.addAttribute("pagination", "off");

		return "index";
	}

	@GetMapping("/acquisition")
	public String goToAcquisition(@RequestParam(name = "idArticle") long idArticle, Model model) {

		try {
			Article articleGagne = enchereService.detailVente(idArticle);
			model.addAttribute("article", articleGagne);
			return "acquisition";

		} catch (BusinessException e) {
			model.addAttribute("global", e.getMessages());
			return "redirect:/";
		}

	}

	@GetMapping("/detail-vente")
	public String goToDetailVente(@RequestParam(name = "idArticle") long idArticle,Model model,
			@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) throws BusinessException {
		LocalDateTime today = LocalDateTime.now();

		try {
			Article article = this.enchereService.detailVente(idArticle);

			if (today.isAfter(article.getDateDebutEncheres()) && today.isBefore(article.getDateFinEncheres())
					&& article.getEtatVente().equals("CR")) {
				this.enchereService.debuterVente(idArticle);
				return "redirect:/detail-vente?idArticle=" + idArticle;
			}

			if (today.isAfter(article.getDateFinEncheres()) && !article.getEtatVente().equals("RE")) {
				this.enchereService.remporterVente(idArticle);

			}
			
			List<Enchere> encheresEnCours = enchereService.consulterEncheres(idArticle);
			model.addAttribute("listeEncheres", encheresEnCours);

			try {
				Enchere enchereEnCours = enchereService.consulterEnchereMax(idArticle);

				if (enchereEnCours != null) {
					model.addAttribute("enchere", enchereEnCours.getMontantEnchere());
					model.addAttribute("pseudoAcheteur", enchereEnCours.getUtilisateur().getPseudo());
				} else {
					model.addAttribute("enchere", 0);
				}
			} catch (BusinessException e) {
			
				model.addAttribute("enchere", 0);
			}
			
			model.addAttribute("today", today);
			model.addAttribute("article", article);
			model.addAttribute("etatVente", article.getEtatVente());

			return "detail-vente";

		} catch (BusinessException e) {
			model.addAttribute("global", e.getMessages());
			return "redirect:/";
		}
	}

	@PostMapping("/retraitEffectue")
	public String retraitEffectue(@RequestParam(name = "idArticle") long idArticle, Model model) {

		try {

			this.enchereService.clotureArticle(idArticle);
		} catch (BusinessException e) {
			model.addAttribute("global", e.getMessages());
		}

		return "redirect:/";
	}

	@PostMapping("/encherir")
	public String goToEncherir(@RequestParam(name = "idArticle") long idArticle, Model model,
			@RequestParam(name = "montant") int montant,
			@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {

		try {
			enchereService.encherir(idArticle, utilisateurEnSession.getIdUtilisateur(), montant);
			return "redirect:/detail-vente?idArticle=" + idArticle;
		} catch (BusinessException e) {
			
			try {
				Article article = enchereService.detailVente(idArticle);
				model.addAttribute("article", article);
				
				 List<Enchere> encheresEnCours = enchereService.consulterEncheres(idArticle);
		         model.addAttribute("listeEncheres", encheresEnCours);

		         Enchere enchereEnCours = null;
		         
		         try {
		                enchereEnCours = enchereService.consulterEnchereMax(idArticle);
		            } catch (BusinessException ex) {
		                
		            }
		            if (enchereEnCours != null) {
		                model.addAttribute("enchere", enchereEnCours.getMontantEnchere());
		                model.addAttribute("pseudoAcheteur", enchereEnCours.getUtilisateur().getPseudo());
		            } else {
		                model.addAttribute("enchere", 0);
		            }

		            model.addAttribute("etatVente", article.getEtatVente());
		            model.addAttribute("today", LocalDateTime.now());

		        } catch (BusinessException ex) {
		            
		            return "redirect:/";
		        }

		        model.addAttribute("global", e.getMessages()); 
		        return "detail-vente";
		    }
				
	}

	@GetMapping("/vendre-article")
	public String goToVendreArticle(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession,
			Model model) {
		Article article = new Article();
		Retrait retrait = new Retrait();

		retrait.setRue(utilisateurEnSession.getRue());
		retrait.setCodePostal(utilisateurEnSession.getCodePostal());
		retrait.setVille(utilisateurEnSession.getVille());
		article.setRetrait(retrait);
		model.addAttribute("article", article);

		model.addAttribute("utilisateurEnSession", utilisateurEnSession);

		return "vendre-article";
	}

	@PostMapping("/articleEnVente")
	public String creationArticle(@ModelAttribute("article") Article article, @RequestParam("file") MultipartFile file,
			@SessionAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) throws BusinessException {
		article.setUtilisateur(utilisateurEnSession);
		article.setDateDebutEncheres(LocalDateTime.parse(article.getParsedDateDebut()));
		article.setDateFinEncheres(LocalDateTime.parse(article.getParsedDateFin()));

		String imageNom = "";

		if (!file.isEmpty()) {
			String uploadDirectory = "src/main/resources/static/images";
			try {
				imageNom = imageService.saveImageToStorage(uploadDirectory, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		article.setPhotoArticle(imageNom);

		enchereService.creationArticle(article);

		return "redirect:/index";
	}

	@PostMapping("/annulerVente")
	public String goToAnnulerArticle(@RequestParam(name = "idArticle") long idArticle,
			@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession, Model model) {

		try {
			this.enchereService.supprimerVente(idArticle);
		} catch (BusinessException e) {
			model.addAttribute("global", e.getMessages());
		}

		List<Article> listeArticles = enchereService.consulterAllVentes();
		List<Categorie> listeCategories = enchereService.consulterAllCategories();
		model.addAttribute("categoriesEnSession", listeCategories);
		model.addAttribute("articles", listeArticles);

		return "redirect:/";
	}

	@GetMapping("/modifierVente")
	public String modifierArticle(@RequestParam(name = "arti", required = false) long idArticle, Model model)
			throws BusinessException {

		Article article = enchereService.detailVente(idArticle);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		article.setParsedDateDebut(article.getDateDebutEncheres().format(formatter));
		article.setParsedDateFin(article.getDateFinEncheres().format(formatter));

		model.addAttribute("article", article);

		System.out.println("modifVente " + article.getIdArticle());
		return "vendre-article";
	}

	@ModelAttribute("categoriesEnSession")
	public List<Categorie> addCategorieEnSession() {
		return this.enchereService.consulterAllCategories();
	}

	@ModelAttribute("utilisateurEnSession")
	public Utilisateur addUtilisateurEnSession() {
		return new Utilisateur();
	}

}
