//package fr.eni.projet.controller.converter;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import fr.eni.projet.bll.EnchereService;
//import fr.eni.projet.bo.Retrait;
//
//@Component
//public class StringToRetraitConverter implements Converter<String, Retrait> {
//
//	private EnchereService enchereService;
//
//	public StringToRetraitConverter(EnchereService enchereService) {
//		this.enchereService = enchereService;
//	}
//
//	@Override
//	public Retrait convert(String idArticle) {
//		return this.enchereService.detailVente(Long.parseLong(idArticle)).getRetrait();
//	}
//
//}
