package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Article;
import com.example.demo.login.domain.repository.ArticleDao;

@Repository("ArticleDaoJdbcImpl")
public class ArticleDaoJdbcImpl implements ArticleDao {

	@Autowired
	JdbcTemplate jdbc;
	
	// Articleテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		
		// 全件取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM article", Integer.class);
		
		return count;
	}
	
	// Userテーブルにデータを1件insert
	@Override
	public int insertOne(Article article) throws DataAccessException {
		
		// １件登録
		int rowNumber = jdbc.update(
				"INSERT INTO article(name," + " title,"
						+ " theme," + " overview)" + " VALUES(?, ?, ?, ?)",
				article.getName(), article.getTitle(), article.getTheme(),
				article.getOverview());
		
		return rowNumber;
	}
	
	// Articleテーブルのデータを１件取得
	public Article selectOne(Long id) throws DataAccessException {
		
		// １件取得
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM article"
											+ " WHERE id = ?", id);
		
		// 結果返却用の変数
		Article article = new Article();
		
		// 取得したデータを結果返却用の変数にセットする
		article.setId((Long) map.get("id"));
		article.setName((String) map.get("name"));
		article.setTitle((String) map.get("title"));
		article.setTheme((String) map.get("theme"));
		article.setOverview((String) map.get("overview"));
		
		return article;
	}
	
	// Articleテーブルの全データを取得
	@Override
	public List<Article> selectMany() throws DataAccessException {
		
		// Articleテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM article");
		
		// 結果返却用の変数
		List<Article> articleList = new ArrayList<>();
		
		// 取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			
			// Articleインスタンスの生成
			Article article = new Article();
			
			// Articleインスタンスに取得したデータをセットする
			article.setId((Long) map.get("id"));
			article.setName((String) map.get("name"));
			article.setTitle((String) map.get("title"));
			article.setTheme((String) map.get("theme"));
			article.setOverview((String) map.get("overview"));
			
			articleList.add(article);
		}
		
		return articleList;
	}
	
	@Override
	public List<Article> pageTopSelect() throws DataAccessException {
		
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM article ORDER BY id  LIMIT 10 ");
		// 結果返却用の変数
		List<Article> articleList = new ArrayList<>();
				
		// 取得したデータを結果返却用のListに格納していく
			for (Map<String, Object> map : getList) {
				
				// Articleインスタンスの生成
				Article article = new Article();
					
				// Articleインスタンスに取得したデータをセットする
				article.setId((Long) map.get("id"));
				article.setName((String) map.get("name"));
				article.setTitle((String) map.get("title"));
				article.setTheme((String) map.get("theme"));
				article.setOverview((String) map.get("overview"));
					
				articleList.add(article);
			}
			
			return articleList;
	}
	
	@Override
	public List<Article> pageNextSelect(int page) throws DataAccessException {
		
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM article ORDER BY id  LIMIT ? , 10 ", page);
		// 結果返却用の変数
			List<Article> articleList = new ArrayList<>();
						
			// 取得したデータを結果返却用のListに格納していく
				for (Map<String, Object> map : getList) {
						
						// Articleインスタンスの生成
					Article article = new Article();
							
					// Articleインスタンスに取得したデータをセットする
					article.setId((Long) map.get("id"));
					article.setName((String) map.get("name"));
					article.setTitle((String) map.get("title"));
					article.setTheme((String) map.get("theme"));
					article.setOverview((String) map.get("overview"));
							
					articleList.add(article);
					}
					
				return articleList;
	}
	
	// Articleテーブルを１件更新
	@Override
	public int updateOne(Article article) throws DataAccessException {
		
		// １件更新
		int rowNumber = jdbc.update(
				"UPDATE ARTICLE" + " SET" + " title = ?," + " theme = ?,"
						+ " overview = ?" + " WHERE name = ?",
					article.getTitle(), article.getTheme(), article.getOverview(), 
					article.getName());
		
		return rowNumber;
	}
	
	@Override
	public int deleteOne(String overview, String name) throws DataAccessException {
		
		// 	１件削除
		int rowNumber = jdbc.update("DELETE FROM article WHERE overview = ? AND name = ? ", overview, name);
		
		return rowNumber;
	}
	
}
