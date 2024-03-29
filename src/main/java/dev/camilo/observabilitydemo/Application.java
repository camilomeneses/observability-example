package dev.camilo.observabilitydemo;

import dev.camilo.observabilitydemo.post.JsonPlaceholderService;
import dev.camilo.observabilitydemo.post.Post;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	// Create a proxy to the JsonPlaceholderService
	@Bean
	JsonPlaceholderService jsonPlaceholderService() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(JsonPlaceholderService.class);
	}


	@Bean
	@Observed(name = "posts.load-all-posts", contextualName = "posts.find-all")
	CommandLineRunner commandLineRunner(
			JsonPlaceholderService jsonPlaceholderService
			/*ObservationRegistry observationRegistry*/
	) {
		// return a observation at particular method call
		/*return args -> {
			Observation.createNotStarted("posts.load-all-posts", observationRegistry)
					.lowCardinalityKeyValue("author", "Camilo")
					.contextualName("posts.load-all-posts")
          .observe(() -> {
						List<Post> posts = jsonPlaceholderService.findAll();
						log.info("Loaded {} posts", posts.size());
						log.info("Posts: {}", posts);
					});
		};*/

		return args -> {
			jsonPlaceholderService.findAll();
		};
	}
}
