package com.debilla.mediqbank;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.Map;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.zaxxer.hikari.*;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Debilla";
	}

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	@RequestMapping("/db")
	String db(Map<String, Object> model) {
		try (Connection connection = dataSource.getConnection()) {
		  Statement stmt = connection.createStatement();
		  stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
		  stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
		  ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

		  ArrayList<String> output = new ArrayList<String>();
		  while (rs.next()) {
		    output.add("Read from DB: " + rs.getTimestamp("tick"));
		  }

		  model.put("records", output);
		  return "db";
		} catch (Exception e) {
		  model.put("message", e.getMessage());
		  return "error";
		}															
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
		  return new HikariDataSource();
		} else {
		  HikariConfig config = new HikariConfig();
		  config.setJdbcUrl(dbUrl);
		  return new HikariDataSource(config);
		}
	}

}