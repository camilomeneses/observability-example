package dev.camilo.observabilitydemo.post;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface JsonPlaceholderService {

  // goto jsonplaceholder.typicode.com/posts
  @GetExchange("/posts")
  List<Post> findAll();

  // goto jsonplaceholder.typicode.com/posts/1
  @GetExchange("/posts/{id}")
  Post findById(@PathVariable Integer id);
}
