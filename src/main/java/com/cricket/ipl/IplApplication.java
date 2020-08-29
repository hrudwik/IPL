package com.cricket.ipl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.processor.FeedPlayerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class IplApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(IplApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(IplApplication.class, args);

		/*UserDao userDao = applicationContext.getBean("userDaoImpl", UserDao.class);
		LOGGER.info("All users in db: {}", userDao.getAllUsers());*/

		FeedPlayerProcessor feedPlayerProcessor = applicationContext.getBean("feedPlayerProcessor", FeedPlayerProcessor.class);
		feedPlayerProcessor.run();
	}

}
