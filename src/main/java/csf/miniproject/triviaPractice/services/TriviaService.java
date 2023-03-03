package csf.miniproject.triviaPractice.services;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonArray;

@Service
public class TriviaService {
    
    public JsonArray getTriviaQuestions(String category, String difficulty, int limit) {
        
        final String URL_TRIVIA = "https://the-trivia-api.com/api/questions";

        String triviaURL = UriComponentsBuilder.fromUriString(URL_TRIVIA)
                       .queryParam("categories", category)
                       .queryParam("limit", 20)
                       .queryParam("difficulty", difficulty)
                       .toUriString();
        System.out.println(triviaURL);
        RequestEntity<Void> req = RequestEntity.get(triviaURL)
                                        .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<JsonArray> response = template.exchange(req, JsonArray.class);
        JsonArray payload = response.getBody();

        System.out.println(payload);
        return payload;
        

    }

}
