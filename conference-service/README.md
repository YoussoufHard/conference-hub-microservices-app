# Service de Conférence

Ce service fait partie de l'application microservices Conference Hub et est responsable de la gestion des conférences et des avis associés.

## Fonctionnalités

- Gestion des conférences (CRUD)
- Gestion des avis sur les conférences (CRUD)
- Recherche de conférences par mot-clé
- Filtrage des conférences par statut, date, organisateur, etc.
- Calcul de la note moyenne des conférences
- Documentation d'API avec Swagger/OpenAPI

## Prérequis

- Java 21
- Maven 3.8+
- Spring Boot 3.2.5
- Spring Cloud 2023.0.0
- H2 Database (en mémoire)

## Configuration

Le fichier `application.properties` contient les configurations suivantes :

- Port du serveur : 8082
- Base de données H2 en mémoire
- Console H2 activée à l'URL `/h2-console`
- Intégration avec Eureka Server pour la découverte de services

## Démarrer l'application

```bash
mvn spring-boot:run
```

## Accès à l'API

- **Swagger UI** : http://localhost:8082/swagger-ui.html
- **H2 Console** : http://localhost:8082/h2-console
  - JDBC URL: jdbc:h2:mem:conference_db
  - User Name: sa
  - Password: (vide)

## Endpoints principaux

### Conférences

- `GET /api/conferences` - Récupérer toutes les conférences
- `GET /api/conferences/{id}` - Récupérer une conférence par son ID
- `POST /api/conferences` - Créer une nouvelle conférence
- `PUT /api/conferences/{id}` - Mettre à jour une conférence
- `DELETE /api/conferences/{id}` - Supprimer une conférence
- `GET /api/conferences/upcoming` - Récupérer les prochaines conférences
- `GET /api/conferences/search?keyword={keyword}` - Rechercher des conférences
- `GET /api/conferences/organizer/{organizer}` - Récupérer les conférences par organisateur

### Avis (Reviews)

- `GET /api/reviews` - Récupérer tous les avis
- `GET /api/reviews/{id}` - Récupérer un avis par son ID
- `POST /api/reviews` - Créer un nouvel avis
- `PUT /api/reviews/{id}` - Mettre à jour un avis
- `DELETE /api/reviews/{id}` - Supprimer un avis
- `GET /api/reviews/conference/{conferenceId}` - Récupérer les avis d'une conférence
- `GET /api/reviews/user/{userId}` - Récupérer les avis d'un utilisateur
- `GET /api/reviews/conference/{conferenceId}/average` - Obtenir la note moyenne d'une conférence

## Structure du projet

```
conference-service/
├── src/
│   ├── main/
│   │   ├── java/net/youssouf/conferenceservice/
│   │   │   ├── config/           # Configurations (Swagger, Feign, etc.)
│   │   │   ├── controller/       # Contrôleurs REST
│   │   │   ├── dto/              # Objets de transfert de données
│   │   │   ├── entity/           # Entités JPA
│   │   │   ├── exception/        # Exceptions personnalisées
│   │   │   ├── mapper/           # Mappers pour la conversion DTO <-> Entité
│   │   │   ├── repository/       # Interfaces de repository JPA
│   │   │   └── service/          # Couche de service
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Tests unitaires et d'intégration
└── pom.xml
```

## Tests

Pour exécuter les tests :

```bash
mvn test
```

## Déploiement

Le service peut être empaqueté en un fichier JAR exécutable avec la commande :

```bash
mvn clean package
```

Le fichier JAR généré se trouvera dans le dossier `target/`.

## Licence

MIT
