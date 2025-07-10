
package fr.eni.projet.dal;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	private final NamedParameterJdbcTemplate jdb;
	
	

	public ArticleDAOImpl(NamedParameterJdbcTemplate jdb) {
		this.jdb = jdb;
	}

	

	@Override
	public long ajouterArticle(Article article) {
//		String sql = "insert into article(nomArticle,descriptions,dateDebutEncheres,dateFinEncheres,miseAPrix,prixVente,idUtilisateur,idCategorie) "
//				+ "values(:nomArticle, :descriptions, :dateDebutEncheres, :dateFinEncheres, :miseAPrix, :prixVente, :idUtilisateur, :idCategorie)";
		String sql1 = "merge into article v"
				+ " using (select :idArticle as idArticle, :photoArticle as photoArticle, :nomArticle as nomArticle, :descriptions as descriptions, :dateDebutEncheres as dateDebutEncheres,:dateFinEncheres as dateFinEncheres"
				+ " , :miseAPrix as miseAPrix, :prixVente as prixVente, :idUtilisateur as idUtilisateur, :idCategorie as idCategorie) s"
				+ " on v.idArticle = s.idArticle" + " when matched then"
				+ " update set photoArticle = s.photoArticle, nomArticle = s.nomArticle, descriptions = s.descriptions, dateDebutEncheres = s.dateDebutEncheres, dateFinEncheres = s.dateFinEncheres, miseAPrix = s.miseAPrix, prixVente = s.prixVente,"
				+ " idUtilisateur = s.idUtilisateur, idCategorie = s.idCategorie" + " when not matched then"
				+ " insert (photoArticle, nomArticle,descriptions,dateDebutEncheres,dateFinEncheres,miseAPrix,prixVente,idUtilisateur,idCategorie)"
				+ " values(:photoArticle, :nomArticle, :descriptions, :dateDebutEncheres, :dateFinEncheres, :miseAPrix, :prixVente, :idUtilisateur, :idCategorie);";

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("photoArticle", article.getPhotoArticle());
		mapSqlParameterSource.addValue("nomArticle", article.getNomArticle());
		mapSqlParameterSource.addValue("descriptions", article.getDescriptions());
		mapSqlParameterSource.addValue("dateDebutEncheres", article.getDateDebutEncheres());
		mapSqlParameterSource.addValue("dateFinEncheres", article.getDateFinEncheres());
		mapSqlParameterSource.addValue("miseAPrix", article.getMiseAPrix());
		mapSqlParameterSource.addValue("prixVente", 0);
		mapSqlParameterSource.addValue("idUtilisateur", article.getUtilisateur().getIdUtilisateur());
		mapSqlParameterSource.addValue("idCategorie", article.getCategorie().getIdCategorie());
		mapSqlParameterSource.addValue("idArticle", article.getIdArticle());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdb.update(sql1,mapSqlParameterSource,keyHolder);

		if (article.getIdArticle() == 0) {
			article.setIdArticle(keyHolder.getKey().longValue());
		}
		
		return article.getIdArticle();
	}
	
	@Override
	public void updateEtatArticle(long idArticle, String etat) {
		
		String sql = "UPDATE article SET etatVente = :etatVente WHERE idArticle = :idArticle";
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("etatVente", etat);
		mapSqlParameterSource.addValue("idArticle", idArticle);
		
		jdb.update(sql, mapSqlParameterSource);

	}

	@Override
	public void supprimerArticle(long idArticle) {
		
		String sql = "delete from article where idArticle = :idArticle";
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("idArticle", idArticle);
		
		jdb.update(sql, mapSqlParameterSource);
	}

	@Override
	public List<Article> afficherArticles() {
		
		String sql = "select * from article inner join retrait on article.idArticle = retrait.idArticle";
		
		return jdb.query(sql, new ArticleMapper());
	}
	
	@Override
	public List<Article> afficherArticlesFiltres(String filtreNomArticle, int categorieFiltree, String encheresEnCours,
			int mesEncheres, int encheresRemportees, int ventesEnCours, int ventesEnAttente, int ventesTerminees) {
		
		String sql = "SELECT a.idArticle, a.photoArticle, a.nomArticle, a.descriptions, a.dateDebutEncheres, a.dateFinEncheres, a.miseAPrix, a.prixVente, a.idUtilisateur, a.idCategorie, a.etatVente, r.rue, r.codePostal, r.ville "
				+ "FROM article a LEFT JOIN retrait r ON a.idArticle = r.idArticle LEFT JOIN enchere e ON a.idArticle = e.idArticle WHERE "
				+ "(:filtreNomArticle IS NULL OR a.nomArticle LIKE CONCAT('%', :filtreNomArticle, '%')) "
				+ "AND (:categorieFiltree = 0 OR a.idCategorie = :categorieFiltree) "
				+ "AND (:encheresEnCours IS NULL OR a.etatVente = :encheresEnCours) "
				+ "AND (:mesEncheres = 0 OR e.idUtilisateur = :mesEncheres) "
				+ "AND (:encheresRemportees = 0 OR a.idUtilisateur = :encheresRemportees AND a.etatVente = 'ET') "
				+ "AND (:ventesEnCours = 0 OR a.idUtilisateur = :ventesEnCours AND a.etatVente = 'EC') "
				+ "AND (:ventesEnAttente = 0 OR a.idUtilisateur = :ventesEnAttente AND a.etatVente = 'CR') "
				+ "AND (:ventesTerminees = 0 OR a.idUtilisateur = :ventesTerminees AND a.etatVente = 'ET') "
				+ "GROUP BY a.idArticle, a.photoArticle, a.nomArticle, a.descriptions, a.dateDebutEncheres, a.dateFinEncheres, a.miseAPrix, a.prixVente, a.idUtilisateur, a.idCategorie, a.etatVente, r.rue, r.codePostal, r.ville";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("filtreNomArticle", filtreNomArticle);
		map.addValue("categorieFiltree", categorieFiltree);
		map.addValue("encheresEnCours", encheresEnCours);
		map.addValue("mesEncheres", mesEncheres);
		map.addValue("encheresRemportees", encheresRemportees);
		map.addValue("ventesEnCours", ventesEnCours);
		map.addValue("ventesEnAttente", ventesEnAttente);
		map.addValue("ventesTerminees", ventesTerminees);
		
		return jdb.query(sql, map, new ArticleMapper());
	}

	public List<Article> getTopTrendingArticles() {

		String sql = "SELECT TOP 5 a.*, r.rue, r.codePostal, r.ville FROM article a "
				+ "LEFT JOIN retrait r ON a.idArticle = r.idArticle JOIN (SELECT idArticle, COUNT(*) AS nbEncheres FROM enchere "
				+ "GROUP BY idArticle) e ON a.idArticle = e.idArticle ORDER BY e.nbEncheres DESC";

		return jdb.query(sql, new ArticleMapper());
	}

	public List<Article> getArticlesByPage(int page, int pageSize) {
		
		String sql = "select * from article inner join retrait on article.idArticle = retrait.idArticle ORDER BY article.dateFinEncheres DESC OFFSET :offset ROWS FETCH NEXT 6 ROWS ONLY";

		int offset = (page - 1) * pageSize;
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("offset", offset);

		return jdb.query(sql, map, new ArticleMapper());
		
	}

	public int countArticles() {
		String sql = "SELECT COUNT(*) FROM article";
		
		return jdb.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

	@Override
	public Article afficherArticle(long idArticle) {
		
		String sql = "select * from article inner join retrait on article.idArticle = retrait.idArticle where article.idArticle = :idArticle";
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("idArticle", idArticle);

		return jdb.queryForObject(sql, mapSqlParameterSource, new ArticleMapper());
	}

	@Override
	public boolean hasArticle(long idArticle) {
		
		String sql = "select count(*) FROM article WHERE idArticle = :idArticle";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);

		Integer nbArticle = jdb.queryForObject(sql, map, Integer.class);

		return nbArticle != 0;
	}
	
}

class ArticleMapper implements RowMapper<Article> {

	@Override
	public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Article article = new Article();
		article.setIdArticle(rs.getLong("idArticle"));
		article.setPhotoArticle(rs.getString("photoArticle"));
		article.setNomArticle(rs.getString("nomArticle"));
		article.setDescriptions(rs.getString("descriptions"));
		article.setDateDebutEncheres(rs.getTimestamp("dateDebutEncheres").toLocalDateTime());
		article.setDateFinEncheres(rs.getTimestamp("dateFinEncheres").toLocalDateTime());
		article.setMiseAPrix(rs.getInt("miseAPrix"));
		article.setPrixVente(rs.getInt("prixVente"));
		article.setEtatVente(rs.getString("etatVente"));

		Categorie categorie = new Categorie();
		categorie.setIdCategorie(rs.getLong("idCategorie"));
		article.setCategorie(categorie);

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(rs.getLong("idUtilisateur"));
		article.setUtilisateur(utilisateur);

		Retrait retrait = new Retrait();
		retrait.setArticle(article);
		retrait.setCodePostal(rs.getString("codePostal"));
		retrait.setRue(rs.getString("rue"));
		retrait.setVille(rs.getString("ville"));
		article.setRetrait(retrait);

		return article;
	}
}