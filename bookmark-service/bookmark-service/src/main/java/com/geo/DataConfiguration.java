package com.geo;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.geo.bo.Bookmark;
import com.geo.dao.BookmarkRepository;

public class DataConfiguration {

	@Bean(destroyMethod = "close")
	@Profile("prod")
	public DataSource mysqlDatasource() {
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/boot");
		ds.setUsername("hibernateuser");
		ds.setPassword("hibernateuser");
		ds.setInitialSize(5);
		ds.setMaxActive(10);
		ds.setMaxIdle(5);
		ds.setMinIdle(2);
		return ds;
	}

	@Bean(destroyMethod = "shutdown")
	@Profile("dev")
	public DataSource h2Datasource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:/sql/schema.sql")
				.build();
	}

	@Bean
	@Profile({"dev"})
	public CommandLineRunner init(BookmarkRepository bookmarkRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				List<String> list = Arrays.asList("mstine", "jlong");
				for (String n : list) {
					bookmarkRepository.save(new Bookmark(n,
							"http://some-other-host" + n + ".com/",
							"A description for " + n + "'s link", n));
				}

			}
		};
		/*
		 * return args -> { bookmarkRepository.deleteAll();
		 * 
		 * Arrays.asList("mstine", "jlong").forEach(n ->
		 * bookmarkRepository.save(new Bookmark(n, "http://some-other-host" + n
		 * + ".com/", "A description for " + n + "'s link", n))); };
		 */
	}

}
