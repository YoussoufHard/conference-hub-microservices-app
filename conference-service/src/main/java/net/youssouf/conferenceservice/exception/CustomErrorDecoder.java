package net.youssouf.conferenceservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // Gestion personnalisée des erreurs en fonction du code de statut HTTP
        if (response.status() == 400) {
            return new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Requête incorrecte envoyée au service distant"
            );
        }
        
        if (response.status() == 404) {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Ressource non trouvée dans le service distant"
            );
        }
        
        if (response.status() >= 500) {
            return new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur interne du service distant"
            );
        }
        
        // Pour les autres cas, utiliser le décodeur par défaut
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
