package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsaProjekatApplication {

	public static void main(String[] args)
	{
		System.setProperty("server.servlet.context-path", "/ISA");
		SpringApplication.run(IsaProjekatApplication.class, args);
	}
}
