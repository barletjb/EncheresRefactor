//package fr.eni.projet.controller.converter;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import fr.eni.projet.bll.EnchereService;
//import fr.eni.projet.bo.Categorie;
//
//@Component
//public class StringToCategorieConverter implements Converter<String, Categorie> {
//
//	private EnchereService enchereService;
//
//	public StringToCategorieConverter(EnchereService enchereService) {
//		this.enchereService = enchereService;
//	}
//
//	@Override
//	public Categorie convert(String idArticle) {
//		return this.enchereService.detailVente(Long.parseLong(idArticle)).getCategorie();
//	}
//
//}
