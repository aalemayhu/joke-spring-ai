import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokeController {

    @GetMapping("/joke.html")
    public String getJoke() {
        Joke joke = new Joke("Why don't scientists trust atoms? Because they make up everything!");
        return "<p>" + joke.getContent() + "</p>";
    }
}