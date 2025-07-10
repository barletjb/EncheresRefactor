package fr.eni.projet.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Liste des erreurs métiers associées à cette exception.
	 */
	private List<CodeErreur> erreurs = new ArrayList<CodeErreur>();
	
	public BusinessException() {
		
	}
	
	public BusinessException(CodeErreur codeErreur) {
		this.erreurs.add(codeErreur);
	}

	/**
	 * Ajoute un code d'erreur à la liste des erreurs de cette exception.
	 * 
	 * @param code Le code d'erreur à ajouter.
	 */
	public void add(CodeErreur code) {
		this.erreurs.add(code);
	}
	
	/**
	 * Retourne la liste des {@link CodeErreur} ajoutés à cette exception.
	 * 
	 * @return une liste d'objets {@link CodeErreur}.
	 */
	public Iterable<CodeErreur> getExceptionMessages() {
		return erreurs;
	}

	/**
	 * Vérifie si au moins une erreur a été enregistrée.
	 * 
	 * @return {@code true} s'il y a au moins une erreur, sinon {@code false}.
	 */
	public boolean hasError() {
		return !this.erreurs.isEmpty();
	}

	/**
	 * Retourne une liste des messages d'erreur associés aux codes d'erreur enregistrés.
	 * 
	 * @return une liste de chaînes contenant les messages d'erreur à afficher.
	 */
	public List<String> getMessages() {
		
		List<String> messages = new ArrayList<>();
		
		for (CodeErreur e : erreurs) {
			messages.add(e.getMessage());
		}
		return messages;
	}
}