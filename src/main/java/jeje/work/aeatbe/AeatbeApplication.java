package jeje.work.aeatbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AeatbeApplication {

  public static void main(String[] args) {
    SpringApplication.run(AeatbeApplication.class, args);
  }
}
