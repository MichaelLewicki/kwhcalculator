package com.api.kwhcalculator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KwhcalculatorApplication {

	//para usar la dependencia Model Mapper, primero se tiene que registrar la clase y colocarle la anotación @Bean
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(KwhcalculatorApplication.class, args);
	}

}
