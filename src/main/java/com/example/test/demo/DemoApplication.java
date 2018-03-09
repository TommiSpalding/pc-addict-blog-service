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

		Blogpost post = new Blogpost("Jepen niksit redux", "oon homo", "jeppe");
        post.addComment(new Comment("vitun homo tapa ittes xd", "edgelord"));
        post.addComment(new Comment("ihan jees", "make"));
        post.addComment(new Comment("kiva juttu hei, mut kuis se ny nii", "???"));
        blogpostRepository.save(post);

        post = new Blogpost("Jarmo-Jorman paskat vitsit 1", "minä ainakin voitan leipää", "JJ");
        post.addComment(new Comment("mee kotiis", "ei näin"));
        post.addComment(new Comment("ei vittu mitä paskaa :DD", "make"));
        blogpostRepository.save(post);
	}
}
