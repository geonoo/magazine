package com.geonoo.magazine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application-aws.properties") })
public class MagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazineApplication.class, args);
    }

}
