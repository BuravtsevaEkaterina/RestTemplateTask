package app.resttemplate;

import app.resttemplate.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class RestTemplateApplication {

	private static final String USER_URL = "http://91.241.64.178:7081/api/users";
	private static final RestTemplate template = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateApplication.class, args);

		String cookies = getCookie();
		System.out.println("Cookies: " + cookies);

		String code = saveUser(cookies) + updateUser(cookies) + deleteUser(cookies);
		System.out.println("Code: " + code);

	}

	private static String getCookie() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return template
				.exchange(USER_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class)
				.getHeaders()
				.getFirst("set-cookie");
	}

	private static String saveUser(String cookies) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.COOKIE, cookies);

		return template
				.exchange(USER_URL, HttpMethod.POST, new HttpEntity<>(new User(3L, "James", "Brown", (byte) 10), headers), String.class)
				.getBody();
	}

	private static String updateUser(String cookies) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.COOKIE, cookies);

		return template
				.exchange(USER_URL, HttpMethod.PUT, new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 10), headers), String.class)
				.getBody();
	}

	private static String deleteUser(String cookies) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.COOKIE, cookies);

		return template
				.exchange(USER_URL + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class)
				.getBody();
	}


}
