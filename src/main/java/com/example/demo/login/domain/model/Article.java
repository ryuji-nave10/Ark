package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Article {
	
	public static int page = 0;
	
	private Long id;
	private String name; // ユーザー名
	private String title; // 作品タイトル
	private String theme; // 作品テーマ
	private String overview; // 作品概要

}
