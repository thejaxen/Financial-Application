package com.thejaxen.thejaxendemobank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title="The Banking Application App by Jaxen",
				description="Backend Rest APIs for banking app.",
				version = "v1.0",
				contact = @Contact(
						name="Mert Duyar",
						email="mertysfduyar@gmail.com",
						url="https://github.com/thejaxen"
				),
				license = @License(
						name="BOJA (Bank Of Jaxen)"
				)
		),

		externalDocs = @ExternalDocumentation(
				description = "BOJA (Bank of Jaxen) Documentation",
				url="https://github.com/thejaxen"
)

)
public class ThejaxendemobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThejaxendemobankApplication.class, args);
	}

}
