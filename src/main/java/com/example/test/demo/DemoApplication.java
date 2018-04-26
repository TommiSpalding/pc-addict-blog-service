package com.example.test.demo;

import com.example.test.demo.db.Blogpost;
import com.example.test.demo.db.BlogpostRepository;
import com.example.test.demo.db.Comment;
import com.example.test.demo.db.CommentRepository;
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

		Blogpost post = new Blogpost("Steamed hams", "Well, Seymour, I made it- despite your directions. Ah. Superintendent Chalmers. Welcome. - I hope you're prepared for an unforgettable luncheon. - Yeah. Oh, egads! My roast is ruined. But what if I were to purchase fast food and disguise it as my own cooking? Delightfully devilish, Seymour. Ah- Skinner with his crazy explanations The superintendent's gonna need his medication When he hears Skinner's lame exaggerations There'll be trouble in town tonight Seymour! Superintendent, I was just- uh, just stretching my calves on the windowsill. Isometric exercise. Care to join me? Why is there smoke coming out of your oven, Seymour? Uh- Oh. That isn't smoke. It's steam. Steam from the steamed clams we're having. Mmm. Steamed clams. Whew. Superintendent, I hope you're ready for mouthwatering hamburgers. I thought we were having steamed clams. D'oh, no. I said steamed hams. That's what I call hamburgers. You call hamburgers steamed hams? Yes. It's a regional dialect. - Uh-huh. Uh, what region? - Uh, upstate New York. Really. Well, I'm from Utica, and I've never heard anyone use the phrase \"steamed hams. \" Oh, not in Utica. No. It's an Albany expression. I see. You know, these hamburgers are quite similar to the ones they have at Krusty Burger. Oh, no. Patented Skinner burgers. Old family recipe. - For steamed hams. - Yes. Yes. And you call them steamed hams despite the fact that they are obviously grilled. Ye- You know, the- One thing I should- - Excuse me for one second. - Of course. Well, that was wonderful. A good time was had by all. I'm pooped. Yes. I should be- Good Lord! What is happening in there? - Aurora borealis. - Uh- Aurora borealis at this time of year at this time of day in this part of the country localized entirely within your kitchen? - Yes. - May I see it? No. Seymour. ! The house is on fire. ! No, Mother. It's just the northern lights. Well, Seymour, you are an odd fellow but I must say you steam a good ham. Help. ! Help.", "???");
        post.addComment(new Comment("xd", "edgelord"));
        post.addComment(new Comment("ihan jees", "make"));
        post.addComment(new Comment("kiva juttu hei, mut kuis se ny nii", "???"));
        blogpostRepository.save(post);

        post = new Blogpost("Jarmo-Jorman vitsit 1", "Lorem ipsum dolor sit amet, ex vero inimicus ius, ei ignota diceret tractatos pro. Vix cu mutat incorrupte scriptorem, mea stet eripuit at. Eam te similique adversarium. Cu usu quando intellegat forensibus, vel noster qualisque ad. Commune repudiare euripidis qui ut, graecis percipit similique mei ei. Ne odio ubique ius, usu labores legendos tincidunt no, no nec novum lucilius theophrastus.", "JJ");
        post.addComment(new Comment("mee kotiis", "ei näin"));
        post.addComment(new Comment("ei", "make"));
        blogpostRepository.save(post);

        System.out.println("Test the REST api with these commands!");
        System.out.println("GET all blogposts: curl -i -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X GET http://localhost:8080/blogposts");
        System.out.println("GET a blogpost: curl -i -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X GET http://localhost:8080/blogposts/1");
        System.out.println("GET all comments in a blogpost: curl -i -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X GET http://localhost:8080/blogposts/1/comments");
        System.out.println("GET a comment in a blogpost: curl -i -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X GET http://localhost:8080/blogposts/1/comments/1");
        System.out.println("Note Authorization headers in here!");
        System.out.println("POST a blogpost WILL NOT WORK: curl -v -H \"Content-Type: application/json\" -H \"Authorization: Basic WONTWORK\" -X POST -d \"{\\\"title\\\": \\\"test\\\", \\\"authorName\\\": \\\"john doe\\\", \\\"textBody\\\": \\\"my blog post\\\"}\" http://localhost:8080/blogposts");
        System.out.println("POST a blogpost WILL WORK: curl -v -H \"Content-Type: application/json\" -H \"Authorization: Basic YWRtaW46dGFpa2F2aWl0dGE=\" -X POST -d \"{\\\"title\\\": \\\"test\\\", \\\"authorName\\\": \\\"john doe\\\", \\\"textBody\\\": \\\"my blog post\\\"}\" http://localhost:8080/blogposts");
        System.out.println("DELETE a blogpost: curl -v -H \"Content-Type: application/json\" -H \"Authorization: Basic YWRtaW46dGFpa2F2aWl0dGE=\" -X DELETE http://localhost:8080/blogposts/1");

    }
}
