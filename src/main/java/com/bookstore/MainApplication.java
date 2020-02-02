package com.bookstore;
 
import com.bookstore.repository.AuthorRepository.AuthorNameAge;
import java.util.List;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            List<AuthorNameAge> authors = bookstoreService.fetchFirst2ByBirthplace();

            System.out.println("Number of authors:" + authors.size());

            for (AuthorNameAge author : authors) {
                System.out.println("Author name: " + author.getName() 
                        + " | Age: " + author.getAge());
            }
        };
    }
}

/*
*DTO Via Spring Data Projections (Projection Interface In Repository Interface)

Note: You may also like to read the recipe, "How To Enrich DTO With Virtual Properties Via Spring Projections"

Description: Fetch only the needed data from the database via Spring Data Projections (DTO). The projection interface is defined as a static interface (can be non-static as well) in the repository interface.

Key points:

write an interface (projection) containing getters only for the columns that should be fetched from the database
write the proper query returning a List<projection>
if is applicable, limit the number of returned rows (e.g., via LIMIT) - here, we can use query builder mechanism built into Spring Data repository infrastructure
Note: Using projections is not limited to use query builder mechanism built into Spring Data repository infrastructure. We can fetch projections via JPQL or native queries as well. For example, in this application we use a JPQL.

Output example (select first 2 rows; select only "name" and "age"):
*
*/