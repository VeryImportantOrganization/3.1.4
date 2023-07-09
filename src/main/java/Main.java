import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String cookie = Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie")).get(0);
        System.out.println(cookie);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Cookie", cookie);

        User user = new User(3L, "James", "Brown", (byte)23);
        ResponseEntity<String> responseEntity1 = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(user, httpHeaders), String.class);

        String code1 = responseEntity1.getBody();

        User user1 = new User(3L, "Thomas", "Shelby", (byte)23);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(user1, httpHeaders), String.class);
        String code2 = responseEntity2.getBody();

        ResponseEntity<String> responseEntity3 = restTemplate.exchange(url + "/3",
                HttpMethod.DELETE, new HttpEntity<>(user, httpHeaders), String.class);

        String code3 = responseEntity3.getBody();

        System.out.println(code1 + code2 + code3);
    }
}
