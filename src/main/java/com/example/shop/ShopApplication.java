package com.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);

		// String[] beansNames = ctx.getBeanDefinitionNames();
		// if(ctx.containsBeanDefinition("authenticationManager")){
		// 	System.out.println(ctx);
		// }
		// Arrays.sort(beansNames);
		// for(String beanName : beansNames){
		// 	System.out.println(beanName);
		// }
	}



	// @Bean
	// public CommandLineRunner commandLineRunner(
	// 		AuthenticationServices service
	// ) {
	// 	return args -> {
	// 		var admin = RegisterRequest.builder()
	// 				.first_name("Admin")
	// 				.last_name("Admin")
	// 				.email("admin@mail.com")
	// 				.password("password")
	// 				.role(ADMIN)
	// 				.build();
	// 		System.out.println("Admin token: " + service.register(admin).getAccessToken());

	// 		var manager = RegisterRequest.builder()
	// 				.first_name("Admin")
	// 				.last_name("Admin")
	// 				.email("manager@mail.com")
	// 				.password("password")
	// 				.role(MANAGER)
	// 				.build();
	// 		System.out.println("Manager token: " + service.register(manager).getAccessToken());

	// 	};
	// }

}
