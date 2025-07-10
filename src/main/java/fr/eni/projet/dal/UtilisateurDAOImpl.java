package fr.eni.projet.dal;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO{

    private final NamedParameterJdbcTemplate jdb;
    private final JdbcTemplate jdbcTemplate;


    public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdb, JdbcTemplate jdbcTemplate) {
        this.jdb = jdb;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public int consulterNbreCredit(long idUtilisateur) {

        String sql = "select credit from utilisateur where idUtilisateur  = :idUtilisateur";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", idUtilisateur);

        return jdb.queryForObject(sql,mapSqlParameterSource,Integer.class);
    }



    @Override
    public void creerCompte(Utilisateur u) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        String sql = "INSERT INTO utilisateur (pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,roles) "
                + "values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :roles)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", u.getPseudo());
        mapSqlParameterSource.addValue("nom", u.getNom());
        mapSqlParameterSource.addValue("prenom", u.getPrenom());
        mapSqlParameterSource.addValue("email", u.getEmail());
        mapSqlParameterSource.addValue("telephone", u.getTelephone());
        mapSqlParameterSource.addValue("rue", u.getRue());
        mapSqlParameterSource.addValue("codePostal", u.getCodePostal());
        mapSqlParameterSource.addValue("ville", u.getVille());
        mapSqlParameterSource.addValue("motDePasse", passwordEncoder.encode(u.getMotDePasse()));
        mapSqlParameterSource.addValue("roles", u.getRoles());


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdb.update(sql,mapSqlParameterSource,keyHolder);

        if(keyHolder.getKey()!=null) {
            u.setIdUtilisateur(keyHolder.getKey().longValue());
        }
    }

	public void updateCompte(Utilisateur u) {

		String sql = "update utilisateur set pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email,"
				+ "telephone = :telephone, rue = :rue, codePostal = :codePostal, ville = :ville where idUtilisateur = :idUtilisateur";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", u.getPseudo());
		map.addValue("nom", u.getNom());
		map.addValue("prenom", u.getPrenom());
		map.addValue("email", u.getEmail());
		map.addValue("telephone", u.getTelephone());
		map.addValue("rue", u.getRue());
		map.addValue("codePostal", u.getCodePostal());
		map.addValue("ville", u.getVille());

        map.addValue("idUtilisateur", u.getIdUtilisateur());

        jdb.update(sql,map);
	}

    @Override
    public void desactiverCompte(long idUtilisateur) {
        String sql1 = "delete from enchere where idUtilisateur = :idUtilisateur;";
        String sql2 = "delete from article where idUtilisateur = :idUtilisateur";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", idUtilisateur);

        jdb.update(sql1,mapSqlParameterSource);
        jdb.update(sql2,mapSqlParameterSource);

    }

    @Transactional
    @Override
    public void supprimerCompte(long idUtilisateur) {

        String sql1 = "delete from enchere where idUtilisateur = :idUtilisateur;";
        String sql2 = "delete from utilisateur where idUtilisateur = :idUtilisateur";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", idUtilisateur);

        jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement callableStatement = connection.prepareCall("{call sp_delete_user_with_refund(?)}")) {
                callableStatement.setLong(1, idUtilisateur);
                callableStatement.execute();
            }
            return null;
        });
        jdb.update(sql1,mapSqlParameterSource);
        jdb.update(sql2,mapSqlParameterSource);

    }


    @Override
	public void crediter(long idUtilisateur, int montant) {
		String sqlUpdate = "update utilisateur set credit = :montantCredit where idUtilisateur = :idUtilisateur";

		MapSqlParameterSource map2 = new MapSqlParameterSource();
		map2.addValue("montantCredit", montant + consulterNbreCredit(idUtilisateur));
		map2.addValue("idUtilisateur", idUtilisateur);

		jdb.update(sqlUpdate, map2);
	}

	@Override
	public void crediterVendeur(long idUtilisateur, long idArticle) {
		String sql = "SELECT MAX(montantEnchere) FROM enchere WHERE idArticle = :idArticle";
		String sqlUpdate = "update utilisateur set credit = :montantCredit where idUtilisateur = :idUtilisateur";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);

		Integer montantCredit = jdb.queryForObject(sql, map, Integer.class);

		MapSqlParameterSource map2 = new MapSqlParameterSource();
		map2.addValue("montantCredit", montantCredit + consulterNbreCredit(idUtilisateur));
		map2.addValue("idUtilisateur", idUtilisateur);

		jdb.update(sqlUpdate, map2);
	}

	@Override
	public void debiter(long idUtilisateur, int montant) {

		String sql = "update utilisateur set credit = :montant where idUtilisateur = :idUtilisateur";

		int montantDebit = (consulterCompte(idUtilisateur).getCredit() - montant);

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur", idUtilisateur);
		map.addValue("montant", montantDebit);

		jdb.update(sql, map);
	}




	@Override
    public List<Utilisateur> afficherComptes() {

        String sql = "select * from utilisateur";

        return jdb.query(sql,new BeanPropertyRowMapper<>(Utilisateur.class));
    }



	@Override
    public String  motDePasseOublie(String email) {

        String sql = "select motDePasse from utilisateur where email = :email";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);

        return jdb.queryForObject(sql, mapSqlParameterSource, String.class);
    }


    @Override
    public Utilisateur connecterCompte(String pseudo, String motDePasse) {

        String sql = "select * from utilisateur where pseudo = :pseudo";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", pseudo);

        Utilisateur utilisateurBDD = jdb.queryForObject(sql,mapSqlParameterSource,new BeanPropertyRowMapper<>(Utilisateur.class));

        if(utilisateurBDD.getMotDePasse().equals(motDePasse)) {
        	return utilisateurBDD;
        }

        return null;
    }

    @Override
    public Utilisateur consulterCompte(long idUtilisateur) {

        String sql = "select * from utilisateur where idUtilisateur = :idUtilisateur";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", idUtilisateur);

        return jdb.queryForObject(sql,mapSqlParameterSource,new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public Utilisateur consulterCompte(String pseudo) {
        String sql = "select * from utilisateur where pseudo = :pseudo";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", pseudo);

        return jdb.queryForObject(sql,mapSqlParameterSource,new BeanPropertyRowMapper<>(Utilisateur.class));
    }




    @Override
    public boolean isUtilisateurInBDD(long idUtilisateur) {

        String sql = "select count(*) from utilisateur where idUtilisateur = :idUtilisateur";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", idUtilisateur);

        int i = jdb.queryForObject(sql,mapSqlParameterSource,Integer.class);
        
        return i != 0;
    }
    
    @Override
    public boolean isUtilisateurInBDD(String pseudo) {

        String sql = "select count(*) from utilisateur where pseudo = :pseudo";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", pseudo);

        int i = jdb.queryForObject(sql,mapSqlParameterSource,Integer.class);
        
        return i != 0; 
    }

    @Override
	public boolean emailExist(String email) {

    	String sql = "SELECT COUNT(*) FROM utilisateur WHERE email = :email";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        int count = jdb.queryForObject(sql, params, Integer.class);

        return count != 0;
    }

}
