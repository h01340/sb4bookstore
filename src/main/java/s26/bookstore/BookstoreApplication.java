package s26.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s26.bookstore.domain.Book;
import s26.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// Lisätään demodataa h2-kantaan
	@Bean
	public CommandLineRunner demo(BookRepository bookRepository) {
		return (args) -> {
			Book book1 = new Book("Irja Kirjailija", "Kirjan nimi");
			bookRepository.save(book1);
			Book book2 = new Book("Mirja Kirjailija", "Näin kesytän JPAn");
			bookRepository.save(book2);

			// Haetaan kaikki kirjat
			for (Book book : bookRepository.findAll()) {
				System.out.println(book);
			}
		};

	}

}
