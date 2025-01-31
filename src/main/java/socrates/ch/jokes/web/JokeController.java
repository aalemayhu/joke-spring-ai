package socrates.ch.jokes.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JokeController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;


    public JokeController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;


        List<Document> documents = List.of(
                new Document("Perché il libro di matematica è triste? Perché ha troppi problemi!"),  // Italian dad joke
                new Document("Sai perché il pomodoro non si fa mai i fatti suoi? Perché è sempre troppo cotto!"),  // Italian dad joke
                new Document("Cosa fa un cavallo quando è stanco? Si mette a riposo!"),  // Italian dad joke
                new Document("Perché il computer è così bravo a fare amicizia? Perché è sempre in rete!"),  // Italian dad joke
                new Document("Come si chiama un dinosauro con una grande testa? Un cervellone!"),  // Italian dad joke
                new Document("Cosa fa un elettricista quando è arrabbiato? Si carica!"),  // Italian dad joke
                new Document("Qual è il colmo per un pescatore? Avere le mani in acqua e non prendere mai niente!")  // Italian dad joke
        );

        vectorStore.add(documents);

    }

    @GetMapping("/joke.html")
    public String getJoke() {
        String userInput = "Tell us a dad joke ";


        return this.chatClient.prompt()
                .user(userInput)
                .advisors(
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(this.vectorStore))
                .call()
                .content();
//        Joke joke = new Joke("Why don't scientists trust atoms? Because they make up everything!");
//        return "<p>" + joke.getContent() + "</p>";
    }
}

