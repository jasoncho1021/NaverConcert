package kr.or.connect.resv.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "kr.or.connect.resv.persistence" })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DBConfig {// implements TransactionManagementConfigurer {
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("kr.or.connect.resv.domain");

		Properties jpaProperties = new Properties();

		//Configures the used database dialect. This allows Hibernate to create SQL
		//that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.database-platform"));

		//Specifies the action that is invoked to the database when the Hibernate
		//SessionFactory is created or closed.
		//jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));

		//Configures the naming strategy that is used when Hibernate creates
		//new database objects and schema elements
		//jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));

		//If the value of this property is true, Hibernate writes all SQL
		//statements to the console.
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.show_sql"));

		//If the value of this property is true, Hibernate will format the SQL
		//that is written to the console.
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

/*	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}*/
}
