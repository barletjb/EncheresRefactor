package fr.eni.projet.dal;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	private final NamedParameterJdbcTemplate jdb;

	public RetraitDAOImpl(NamedParameterJdbcTemplate jdb) {
		this.jdb = jdb;
	}

	@Override
	public void creerRetrait(Retrait retrait) {
		
		String sql = "insert into retrait(rue,codePostal,ville,idArticle) "
				+ "values (:rue,:codePostal,:ville,:idArticle)";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("rue", retrait.getRue());
		paramSource.addValue("codePostal", retrait.getCodePostal());
		paramSource.addValue("ville", retrait.getVille());
		paramSource.addValue("idArticle", retrait.getArticle().getIdArticle());
		
		jdb.update(sql, paramSource);
	}

	@Override
	public Retrait afficherRetrait(long idArticle) {
		
		String sql = "SELECT * FROM retrait WHERE idArticle = :idArticle";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("idArticle", idArticle);
		
		return jdb.queryForObject(sql, paramSource, new RetraitMapper());
	}

}

class RetraitMapper implements RowMapper<Retrait> {

	@Override
	public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {

		Article article = new Article();
		article.setIdArticle(rs.getLong("idArticle"));

		Retrait retrait = new Retrait();
		retrait.setRue(rs.getString("rue"));
		retrait.setCodePostal(rs.getString("codePostal"));
		retrait.setVille(rs.getString("ville"));
		retrait.setArticle(article);

		return retrait;
	}
}