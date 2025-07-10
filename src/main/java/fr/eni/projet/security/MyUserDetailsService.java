package fr.eni.projet.security;

import fr.eni.projet.bo.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MonUtilisateurRepository monUtilisateurRepository;

    // Constructor injection for MyUserRepository
    public MyUserDetailsService(MonUtilisateurRepository monUtilisateurRepository) {
        this.monUtilisateurRepository = monUtilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        Utilisateur utilisateur = monUtilisateurRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + pseudo));
        return new MyUserDetails(utilisateur);
    }
}