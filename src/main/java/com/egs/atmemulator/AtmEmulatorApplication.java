package com.egs.atmemulator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ATM Emulator API", version = "1.0", description = "Manage bank operations"))
public class AtmEmulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmEmulatorApplication.class, args);
    }

}
