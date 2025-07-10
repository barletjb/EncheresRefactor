package fr.eni.projet.security;

import fr.eni.projet.bo.Utilisateur;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


@Repository
public interface MonUtilisateurRepository extends JpaRepository<Utilisateur, Long>{

    Optional<Utilisateur> findByPseudo(String pseudo);
}
