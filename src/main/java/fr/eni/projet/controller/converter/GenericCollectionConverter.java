package fr.eni.projet.controller.converter;

import fr.eni.projet.bll.EnchereService;
import fr.eni.projet.bll.UtilisateurService;
import fr.eni.projet.exception.BusinessException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;

import java.util.*;
import java.util.stream.*;

/** @author Souheil SULTAN */
//@Component
public class GenericCollectionConverter implements ConditionalGenericConverter {

	private UtilisateurService utilisateurService;
	private EnchereService enchereService;

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return null;
	}

	@Override
	public boolean matches(TypeDescriptor idType, TypeDescriptor beanType) {
		final var idClass = idType.isArray() ? idType.getElementTypeDescriptor().getType() : idType.getType();
		return idClass.equals(String.class) && beanType.isCollection();
	}

	@Override
	public Object convert(Object idValues, TypeDescriptor idType, TypeDescriptor beanType) {
		final var beanClass = beanType.getElementTypeDescriptor().getType();
		final var values = idType.isArray() ? Arrays.stream((Object[]) idValues) : Stream.of(idValues);
		return values.map(id -> switch (beanClass.getSimpleName()) {
		case "Retrait" -> {
			try {
				yield enchereService.detailVente(Long.parseLong((String) id)).getRetrait();
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		}
		case "Categorie" -> {
			try {
				yield enchereService.detailVente(Long.parseLong((String) id)).getCategorie();
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		}
		case "Utilisateur" -> {
			try {
				yield utilisateurService.afficherProfil(Long.parseLong((String) id));
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		}
		default -> throw new UnknownFormatConversionException("Unsupported type: " + beanClass);
		}).collect(Collectors.toList());
	}

}