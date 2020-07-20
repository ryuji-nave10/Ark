package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ArticleupForm {

	// 必須入力
	@NotBlank(groups = ValidGroup1.class, message = "{require_check}")
	private String name; // ユーザー名

	// 必須入力
	@NotBlank(groups = ValidGroup1.class, message = "{require_check}")
	private String title; // 作品タイトル

	// 必須入力
	@NotBlank(groups = ValidGroup1.class, message = "{require_check}")
	private String theme; // 作品テーマ
	// 必須入力
	@NotBlank(groups = ValidGroup1.class, message = "{require_check}")
	@Length(min = 4, max = 200, groups = ValidGroup2.class, message = "{length_check}")
	private String overview; // 作品概要
}
