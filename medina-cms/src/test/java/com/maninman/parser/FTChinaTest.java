package com.maninman.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.impl.FTChinaFeedParser;
import com.maninman.wordpress.inner.parser.impl.FTChinaParser;

public class FTChinaTest {

	public static void main(String[] args) throws Exception {
		testFtChinaFeedParser();
	}

	public static void testFtChinaFeedParser() {
		FTChinaFeedParser parser = new FTChinaFeedParser();
		FTChinaParser p = new FTChinaParser();
		List<String> list = parser.parse("http://www.ftchinese.com/rss/feed");

		for (String s : list) {
			Article a = p.parse(s);
			if (a != null){
				System.out.println(a.getTitle());
				insertArticle(a);
			}
		}

	}

	public static void testFtChinaParser() {
		FTChinaParser parser = new FTChinaParser();
		System.out.println(parser.parse(
				"http://www.ftchinese.com/story/001037078").getAuthor());

	}

	public static void testJsoup() throws Exception {

		Document doc = null;

		doc = Jsoup.connect("http://www.ftchinese.com/story/001037078?page=1")
				.get();
		Elements es = doc.select("div >p");

		StringBuilder author = new StringBuilder();

		// 只取第一个描述
		for (Element e : es) {
			author.append(e.text());
		}
		System.out.println(author.toString());
	}
	
	
	public static void insertArticle(Article article){
		try
	     {
	      // Load the database driver
	      Connection conn = getConnection();

	      // Print all warnings
	      for( SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning() )
	         {
	          System.out.println( "SQL Warning:" ) ;
	          System.out.println( "State  : " + warn.getSQLState()  ) ;
	          System.out.println( "Message: " + warn.getMessage()   ) ;
	          System.out.println( "Error  : " + warn.getErrorCode() ) ;
	         }

	      String sql = "insert into t_cms_article" +
	      		"(channel, " +
	      		"title," +
	      		"author," +
	      		"content," +
	      		"create_time," +
	      		"create_user," +
	      		"src_site," +
	      		"src_url) " +
	      		" values(?,?,?,?,?,?,?,?)";
	      // Get a statement from the connection
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      
	      pstmt.setString(1, article.getChannel());
	      pstmt.setString(2, article.getTitle());
	      pstmt.setString(3, article.getAuthor());
	      pstmt.setString(4, article.getContent());
	      pstmt.setDate(5, article.getCreateTime());
	      pstmt.setString(6, article.getCreateUser());
	      pstmt.setString(7, article.getSrcSite());
	      pstmt.setString(8, article.getSrcUrl());
	      
	 
	      pstmt.execute();
	      
	      pstmt.close() ;
	      conn.close() ;
	     }
	  catch( SQLException se )
	     {
	      System.out.println( "SQL Exception:" ) ;

	      // Loop through the SQL Exceptions
	      while( se != null )
	         {
	          System.out.println( "State  : " + se.getSQLState()  ) ;
	          System.out.println( "Message: " + se.getMessage()   ) ;
	          System.out.println( "Error  : " + se.getErrorCode() ) ;

	          se = se.getNextException() ;
	         }
	     }
	  catch( Exception e )
	     {
	      System.out.println( e ) ;
	     }
	 }

	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName( "com.mysql.jdbc.Driver" ) ;

	      // Get a connection to the database
	      Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/maninman?user=root&password=colinsage" ) ;
		return conn;
	}
	
}
