package fr.eni.projet.bo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Article {

    @OneToMany(mappedBy = "enchereArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enchere> encheres;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id_utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "categorie_id_categorie")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "retrait_id")
    private Retrait retrait;

    @jakarta.persistence.Id
    private long idArticle = 0;

    private String photoArticle;
    private String nomArticle;
    private String descriptions;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private String etatVente;

    private String parsedDateDebut;
    private String parsedDateFin;
    
    public Article() {
		// TODO Auto-generated constructor stub
	}

    public Article(List<Enchere> encheres, Utilisateur utilisateur, Categorie categorie, Retrait retrait,
			long idArticle, String photoArticle, String nomArticle, String descriptions,
			LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, int miseAPrix, int prixVente,
			String etatVente) {
		this.encheres = encheres;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.retrait = retrait;
		this.idArticle = idArticle;
		this.photoArticle = photoArticle;
		this.nomArticle = nomArticle;
		this.descriptions = descriptions;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
	}

	public String getPhotoArticle() {
		return photoArticle;
	}

	public void setPhotoArticle(String photoArticle) {
		this.photoArticle = photoArticle;
	}

	public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    public long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(long idArticle) {
        this.idArticle = idArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public LocalDateTime getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDateTime getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(int miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public String getParsedDateDebut() {
        return parsedDateDebut;
    }

    public void setParsedDateDebut(String parsedDateDebut) {
        this.parsedDateDebut = parsedDateDebut;
    }

    public String getParsedDateFin() {
        return parsedDateFin;
    }

    public void setParsedDateFin(String parsedDateFin) {
        this.parsedDateFin = parsedDateFin;
    }

    @Override
    public String toString() {
    	
        return "Article{" +
                "encheres=" + encheres +
                ", utilisateur=" + utilisateur +
                ", categorie=" + categorie +
                ", retrait=" + retrait +
                ", idArticle=" + idArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + descriptions + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", miseAPrix=" + miseAPrix +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                '}';
    }
}
