package vn.hoidanit.laptopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LaptopshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopshopApplication.class, args);

		// ApplicationContext dangthuc =
		// SpringApplication.run(LaptopshopApplication.class, args);

		// for (String name : dangthuc.getBeanDefinitionNames()) {
		// System.out.println(name);
		// }
	}

}
