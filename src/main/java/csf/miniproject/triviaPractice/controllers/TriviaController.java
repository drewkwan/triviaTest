package csf.miniproject.triviaPractice.controllers;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import csf.miniproject.triviaPractice.models.Question;
import csf.miniproject.triviaPractice.services.TriviaService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Controller
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TriviaController {
    
    @Autowired
    private TriviaService triviaSvc;

    @GetMapping(path="/questions")
    @ResponseBody
    public ResponseEntity<String> getQuestions(@RequestBody String body) {
        //Instantiate list of questions
        List<Question> questions = new LinkedList<>();
        //get params from body
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject json = reader.readObject();
        String category = json.getString("category");
        String difficulty = json.getString("difficulty");
        Integer limit = 20;
        System.out.println(category);
        System.out.println(difficulty);

        //call external api
        JsonArray questionsJson = triviaSvc.getTriviaQuestions(category, difficulty, limit);
        for (int i =0; i < questionsJson.size(); i++) {
            questions.add(Question.create((JsonObject) questionsJson.get(i)));
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (int j=0; j < questions.size(); j++) {
            arrBuilder.add(questions.get(j).toJson());
        }
        JsonArray payload = arrBuilder.build();

        return ResponseEntity.ok(payload.toString());

    } 

    @GetMapping(path="/test")
    @ResponseBody
    public ResponseEntity<String> testRestAPI() {
        System.out.println("WHY?");
        return ResponseEntity.ok("HELP LAH");
    }

}
