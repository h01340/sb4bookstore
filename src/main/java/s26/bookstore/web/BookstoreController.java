package s26.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import s26.bookstore.domain.Book;
import s26.bookstore.domain.BookRepository;

@Controller
public class BookstoreController {

    public BookstoreController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private final BookRepository bookRepository;

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/newBook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "newBook";
    }

    @PostMapping("/saveNewBook")
    public String saveBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

}
