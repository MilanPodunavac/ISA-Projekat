package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IsaProjekatApplication {

	public static void main(String[] args)
	{
		System.setProperty("server.servlet.context-path", "/ISA");
		SpringApplication.run(IsaProjekatApplication.class, args);
	}

}
