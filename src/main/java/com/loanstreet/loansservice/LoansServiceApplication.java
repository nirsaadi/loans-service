package com.loanstreet.loansservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LoansServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansServiceApplication.class, args);
	}

}
