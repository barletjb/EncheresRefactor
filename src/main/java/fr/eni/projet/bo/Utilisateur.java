package fr.eni.projet.bo;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
public class Utilisateur {

	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enchere> encheres;

	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Article> articles;




	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Id
	@Column(name = "idUtilisateur")
	private long idUtilisateur;

	@NotBlank(message = "Le pseudo est obligatoire.")
	private String pseudo;
	@Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\- ]+$", message = "Caractères non autorisés")
	private String nom;
	@Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\- ]+$", message = "Caractères non autorisés")
	private String prenom;
	@Email(message = "Adresse email invalide")
	private String email;
	@Pattern(regexp = "^(\\+33|0)[1-9](\\s?\\d{2}){4}$", message = "Numéro de téléphone invalide")
	private String telephone;
	@Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\-,. ]+$", message = "Adresse invalide")
	private String rue;
	@Pattern(regexp = "^\\d{5}$", message = "Le code postal doit contenir 5 chiffres")
	@Column(name = "codePostal")
	private String codePostal;
	@Pattern(regexp = "^[a-zA-ZÀ-ÿ'\\- ]+$", message = "Ville invalide")
	private String ville;

	@NotBlank(message = "Le mot de passe est obligatoire.")
	@Column(name = "motDePasse")
	private String motDePasse;

	private int credit;
	private String roles;

	public Utilisateur() {
	}

	public Utilisateur(long idUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit) {
		this.encheres = new ArrayList<>();
		this.articles = new ArrayList<>();
		this.idUtilisateur = idUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
	}


	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}



	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Utilisateur{" +
				"encheres=" + encheres +
				", articles=" + articles +
				", idUtilisateur=" + idUtilisateur +
				", pseudo='" + pseudo + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", email='" + email + '\'' +
				", telephone='" + telephone + '\'' +
				", rue='" + rue + '\'' +
				", codePostal='" + codePostal + '\'' +
				", ville='" + ville + '\'' +
				", motDePasse='" + motDePasse + '\'' +
				", credit=" + credit +
				", roles='" + roles + '\'' +
				'}';
	}
}
