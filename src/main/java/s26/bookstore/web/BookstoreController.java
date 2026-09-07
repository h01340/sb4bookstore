package s26.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import s26.bookstore.domain.Book;
import s26.bookstore.domain.BookRepository;

@Controller
public class BookstoreController {
    private static final Logger log = LoggerFactory.getLogger(BookstoreController.class);

    // https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html
    private final BookRepository bookRepository;

    public BookstoreController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @GetMapping(value = { "index", "/" })
    public String showMainPage() {
        log.info("showMainPage...");
        return "index";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        log.info("getAllBooks...");
        model.addAttribute("kirjat", bookRepository.findAll());
        return "booklist";
    }

    // insert new book, first open addBook html page
    @GetMapping("/newBook")
    public String openAddBookForm(Model model) {
        log.info("Uuden kirjan tekoa...");
        model.addAttribute("book", new Book());
        return "newBook";
    }

    // save a new book
    @PostMapping("/saveBook")
    public String savebook(Book book) {
        log.info("CONTROLLER: Save book: " + book);
        bookRepository.save(book);
        return "redirect:/books";
    }

    // poista booklist-sivulta valittu kirja
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("Delete book which id = " + id);
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    // editoi booklist-sivulla valittua kirjaa (huom. ettei id:tä voi muokata)
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        log.info("Edit book which id = " + id);
        model.addAttribute("editBook", bookRepository.findById(id));
        return "editBook";
    }

    /** VALIDATION TOIMINNALLISUUDESTA ESIMERKKI */
    // insert new book AND validate it
    @GetMapping("/newBookWithValidation")
    public String newBookWithValidation(Model model) {
        log.info("Uuden kirjan tekoa validoinnin kera...");
        model.addAttribute("kirja", new Book());
        return "newBookValidate";
    }

    @PostMapping("saveAndValidateBook")
    public String saveAndValidateBook(@Valid @ModelAttribute("kirja") Book book,
            BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Save and validate the book: " + book);
        if (bindingResult.hasErrors()) {
            log.error("some validation error happened, book: " + book);
            model.addAttribute("kirja", book);
            return "newBookValidate";
        }
        log.info("tallenna kirja: " + book);
        bookRepository.save(book);
        return "redirect:/books";
    }

}