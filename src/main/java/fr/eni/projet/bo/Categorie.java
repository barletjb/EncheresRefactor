package fr.eni.projet.bo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie {

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @jakarta.persistence.Id
    private long idCategorie;

    private String libelle;
    
    public Categorie() {
		// TODO Auto-generated constructor stub
	}

    public Categorie(String libelle, long idCategorie) {
        this.articles = new ArrayList<>();
        this.libelle = libelle;
        this.idCategorie = idCategorie;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(long idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "articles=" + articles +
                ", idCategorie=" + idCategorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
