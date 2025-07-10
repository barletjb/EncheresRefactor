package fr.eni.projet.bo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Enchere {

	@ManyToOne
	@JoinColumn(name = "utilisateur_id_utilisateur")
	private Utilisateur utilisateur;

	@ManyToOne
	@JoinColumn(name = "enchere_article_id_article")
	private Article enchereArticle;

	private LocalDateTime dateEnchere;
	private int montantEnchere;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	public Enchere() {
		// TODO Auto-generated constructor stub
	}

	public Enchere(Utilisateur utilisateur, Article enchereArticle, LocalDateTime dateEnchere, int montantEnchere) {
		this.utilisateur = utilisateur;
		this.enchereArticle = enchereArticle;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Article getEnchereArticle() {
		return enchereArticle;
	}

	public void setEnchereArticle(Article enchereArticle) {
		this.enchereArticle = enchereArticle;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		return "Enchere{" + "utilisateur=" + utilisateur + ", enchereArticle=" + enchereArticle + ", dateEnchere="
				+ dateEnchere + ", montantEnchere=" + montantEnchere + '}';
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
