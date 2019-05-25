package com.tensorflow.siamese;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConfigClass.class})
public class SiameseSpringBootTfApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiameseSpringBootTfApplication.class, args);
	}

}
