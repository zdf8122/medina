package com.maninman.crawler.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.maninman.crawler.entity.RawArticle;

public class RawArticleDao {

	private PreparedStatement pstmt;

	public RawArticleDao() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/maninman?user=root&password=colinsage");

			String sql = "insert into t_cwl_html" + "(url, " + "content,"
					+ "createtime) " + " values(?,?,?)";
			// Get a statement from the connection
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean save(RawArticle rawArticle) {
		try {
			pstmt.clearParameters();
			pstmt.setString(1, rawArticle.getUrl());
			pstmt.setString(2, rawArticle.getContent());
			pstmt.setTimestamp(3, rawArticle.getCreateTime());

			return pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
