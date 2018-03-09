package com.example.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	BlogpostRepository blogpostRepository;

	@Autowired
	CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		blogpostRepository.save(new Blogpost("Jepen niksit", "asdkjfsglkasgkjölgkasgasfkljglöafjgöajaögjöalj", "jepe"));
		blogpostRepository.save(new Blogpost("Jepen niksit 2", "ölölölölölölölölölölölööllölölölölölöö", "jepe"));
		blogpostRepository.save(new Blogpost("Jepen niksit 3", "easdasfasrhfghasga", "jepe"));
		blogpostRepository.save(new Blogpost("Jepen niksit 4", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "jepe"));
		Blogpost post = new Blogpost("Jepen niksit 5", "heh", "jepe");
		blogpostRepository.save(post);

		commentRepository.save(new Comment(post, "Sä oot siis ihan vitun pelle", "joonas"));
		commentRepository.save(new Comment(post, "You disgrace our family son.", "dada"));
		commentRepository.save(new Comment(post, "lol ;)))", "pete"));
	}
}
