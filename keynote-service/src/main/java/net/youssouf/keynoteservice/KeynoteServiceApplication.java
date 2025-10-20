package net.youssouf.keynoteservice;

import net.youssouf.keynoteservice.entity.Keynote;
import net.youssouf.keynoteservice.repository.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(KeynoteRepository repository) {
        return args -> {
            // Vérifier si la base est vide
            if (repository.count() == 0) {
                // Créer des keynotes de test
                Keynote keynote1 = Keynote.builder()
                        .nom("Dupont")
                        .prenom("Jean")
                        .email("jean.dupont@email.com")
                        .fonction("Expert en Microservices")
                        .build();

                Keynote keynote2 = Keynote.builder()
                        .nom("Martin")
                        .prenom("Sophie")
                        .email("sophie.martin@email.com")
                        .fonction("Architecte Cloud")
                        .build();

                Keynote keynote3 = Keynote.builder()
                        .nom("Dubois")
                        .prenom("Pierre")
                        .email("pierre.dubois@email.com")
                        .fonction("Développeur Full Stack")
                        .build();

                // Sauvegarder les keynotes
                repository.saveAll(Arrays.asList(keynote1, keynote2, keynote3));
                
                System.out.println("Données de test chargées avec succès !");
            }
        };
    }
}
