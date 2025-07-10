//package fr.eni.projet.controller.converter;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import fr.eni.projet.bll.UtilisateurService;
//import fr.eni.projet.bo.Utilisateur;
//import fr.eni.projet.exception.BusinessException;
//
//@Component
//public class StringToUtilisateurConverter implements Converter<String, Utilisateur> {
//
//	private UtilisateurService utilisateurService;
//
//	public StringToUtilisateurConverter(UtilisateurService utilisateurService) {
//		this.utilisateurService = utilisateurService;
//	}
//
//	@Override
//	public Utilisateur convert(String idUtilisateur) {
//		try {
//			return this.utilisateurService.afficherProfil(Long.parseLong(idUtilisateur));
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//}
