package com.cricket.ipl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IplApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(IplApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(IplApplication.class, args);
		UserDao userDao = applicationContext.getBean("userDaoImpl", UserDao.class);

		/*User user = new User("1", "hru", "h@sd.com", "123");
		userDao.insert(user);*/
		LOGGER.info("All users in db: {}", userDao.getAllUsers());
	}

}
