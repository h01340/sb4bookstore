package s26.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s26.bookstore.domain.Book;
import s26.bookstore.domain.BookRepository;
import s26.bookstore.domain.Category;
import s26.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// Lisätään demodataa h2-kantaan
	@Bean
	public CommandLineRunner demo(BookRepository bookRepository,
			CategoryRepository categoryRepository) {
		return (args) -> {

			System.out.println("Save some categories");
			Category category1 = new Category("dekkari");
			Category category2 = new Category("dokkari");

			categoryRepository.save(category1);
			categoryRepository.save(category2);

			System.out.println("Save some books");
			Book book1 = new Book("Irja Kirjailija", "Kirjan nimi");
			book1.setCategory(category2);
			bookRepository.save(book1);
			Book book2 = new Book("Mirja Kirjailija", "Näin kesytän JPAn");
			book2.setCategory(category2);
			bookRepository.save(book2);

			// Haetaan kaikki kirjat
			for (Book book : bookRepository.findAll()) {
				System.out.println(book);
			}
		};

	}

}
