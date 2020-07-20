package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Article;

public interface ArticleDao {

	// Articleテーブルの件数を取得
	public int count() throws DataAccessException;
	
	// Articleテーブルにデータを１件insert
	public int insertOne(Article article) throws DataAccessException;
	
	// Articleテーブルのデータを１件取得
	public Article selectOne(String name) throws DataAccessException;
	
	// Articleテーブルの全データを取得
	public List<Article> selectMany() throws DataAccessException;
	
	// Articleテーブルを１件更新
	public int updateOne(Article article) throws DataAccessException;
	
	// Articleテーブルを１件削除
	public int deleteOne(String name) throws DataAccessException;
}
