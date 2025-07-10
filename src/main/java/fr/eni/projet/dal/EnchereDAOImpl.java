package fr.eni.projet.dal;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.bo.Utilisateur;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO{

    private final NamedParameterJdbcTemplate jdb;
    public EnchereDAOImpl(NamedParameterJdbcTemplate jdb) {
        this.jdb = jdb;
    }


    @Override
    public void creerEnchere(Enchere enchere) {
    	
        String sql = "insert into enchere(dateEnchere, montantEnchere, idArticle, idUtilisateur) "
                + " values(:dateEnchere, :montantEnchere,:idArticle, :idUtilisateur)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("dateEnchere", enchere.getDateEnchere());
        mapSqlParameterSource.addValue("montantEnchere", enchere.getMontantEnchere());
        mapSqlParameterSource.addValue("idArticle", enchere.getEnchereArticle().getIdArticle());
        mapSqlParameterSource.addValue("idUtilisateur", enchere.getUtilisateur().getIdUtilisateur());

        jdb.update(sql, mapSqlParameterSource);
    }

   

    @Override
    public List<Enchere> afficherEncheres(long idArticle) {
    	
        String sql = "select * from enchere e INNER JOIN Utilisateur u ON e.idUtilisateur = u.idUtilisateur where idArticle = :idArticle";
        
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idArticle", idArticle);

        return jdb.query(sql,mapSqlParameterSource,new EnchereMapper());
    }
    
    
    
    @Override
    public Enchere enchereMax(long idArticle) {
       
         String sql = "SELECT e.montantEnchere, e.dateEnchere, e.idArticle, e.idUtilisateur, u.pseudo " 
        			   + "FROM enchere e " 
        			   + "INNER JOIN utilisateur u ON e.idUtilisateur = u.idUtilisateur "
        			   + "WHERE idArticle = :idArticle "
        			   + "AND e.montantEnchere = ( SELECT MAX(montantEnchere) FROM enchere WHERE idArticle = :idArticle)";
        
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idArticle", idArticle);
        
        try {
            return jdb.queryForObject(sql, mapSqlParameterSource, new EnchereMapper());
            
        } 
        catch (EmptyResultDataAccessException e) {
        	
            return null; 
        }
    }
}

class EnchereMapper implements RowMapper<Enchere> {

	@Override
	public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Enchere enchere = new Enchere();
		enchere.setMontantEnchere(rs.getInt("montantEnchere"));
        enchere.setDateEnchere(rs.getTimestamp("dateEnchere").toLocalDateTime());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(rs.getLong("idUtilisateur"));
        utilisateur.setPseudo(rs.getString("pseudo"));
        enchere.setUtilisateur(utilisateur);
        
        Article article = new Article();
        article.setIdArticle(rs.getLong("idArticle"));
        enchere.setEnchereArticle(article);
        
        return enchere;
	}
}
