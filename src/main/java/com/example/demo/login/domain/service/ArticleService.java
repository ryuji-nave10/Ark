package com.example.demo.login.domain.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Article;
import com.example.demo.login.domain.repository.ArticleDao;

@Transactional
@Service
public class ArticleService {

	@Autowired
	@Qualifier("ArticleDaoJdbcImpl")
	ArticleDao dao;
	
	
	// insert用メソッド
	public boolean insert(Article article) {
		
		// insert実行
		int rowNumber = dao.insertOne(article);
		
		// 判定用変数
		boolean result = false;
		
		if (rowNumber > 0) {
			//insert成功
			result = true;
		}
		return result;
	}
	
	// カウント用メソッド
	public int count() {
		return dao.count();
	}
	
	// 全件取得用メソッド
	public List<Article> selectMany() {
		// 全件取得
		return dao.selectMany();
	}
	
	public List<Article> pageTopSelect(){
		
		return dao.pageTopSelect();
	}
	
	Article article;
	
	public List<Article> pageNextSelect(int page){
		if (page == 0) {
			return dao.pageTopSelect();
		}else {
			return dao.pageNextSelect(page);
		}
	}
	
	// １件取得用メソッド
	public Article selectOne(Long id) {
		// selectOne実行
		return dao.selectOne(id);
	}
	
	// １件更新用メソッド
	public boolean updateOne(Article article) {
		// 判定用変数
		boolean result = false;
		// １件更新
		int rowNumber = dao.updateOne(article);
		
		if (rowNumber > 0) {
			// update成功
			result = true;
		}
		return result;
	}
	
	// １件削除用メソッド
	public boolean deleteOne(String overview, String name) {
		// １件削除
		int rowNumber = dao.deleteOne(overview, name);
		// 判定用変数
		boolean result = false;
		
		if (rowNumber > 0) {
			// delete成功
			result = true;
		}
		return result;
	}
}
