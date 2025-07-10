package fr.eni.projet.dal;

import fr.eni.projet.bo.Categorie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO{

    private final NamedParameterJdbcTemplate jdb;
    
    
    
    public CategorieDAOImpl(NamedParameterJdbcTemplate jdb) {
        this.jdb = jdb;
    }
    
    
    
    @Override
    public void ajouterCategorie(Categorie categorie) {
    	
        String sql = "insert into categorie (libelle) values (:libelle)";
        
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("libelle", categorie.getLibelle());
        
        jdb.update(sql, paramSource);
    }
    
    @Override
    public void SupprimerCategorie(long idCategorie) {
    	
        String sql = "delete from categorie where idCategorie = :idCategorie";
        
        jdb.update(sql, new MapSqlParameterSource("idCategorie", idCategorie));
    }
    
    

    @Override
    public List<Categorie> listerCategorie() {
        String sql = "select * from categorie";
        
        return jdb.query(sql,new BeanPropertyRowMapper<>(Categorie.class));
    }
    
    

    @Override
    public Categorie afficherCategorieArticle(long idArticle) {
        String sql = "select libelle,categorie.idCategorie from categorie Inner Join article "
        			+ "ON categorie.idCategorie = article.idCategorie where idArticle = :idArticle";
        
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idArticle", idArticle);
        
        return jdb.queryForObject(sql, paramSource, new BeanPropertyRowMapper<>(Categorie.class));
    }
    
}
