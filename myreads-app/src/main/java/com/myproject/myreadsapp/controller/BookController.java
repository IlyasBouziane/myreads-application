package com.myproject.myreadsapp.controller;

import java.util.Optional;

import com.myproject.myreadsapp.model.Book;
import com.myproject.myreadsapp.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    private final String COVER_IMAGE_ROOT="https://covers.openlibrary.org/b/id/";

    @GetMapping(value = "/books/{bookId}")
    public String getBookById(@PathVariable String bookId, Model model){
        Optional<Book> book = bookRepository.findById(bookId);
        String imageUrl;
        if(book.isPresent()){
            if(book.get().getCoversIds().size()>0 && book.get().getCoversIds()!=null){
                imageUrl = COVER_IMAGE_ROOT+book.get().getCoversIds().get(0)+"-L.jpg";
            }else{
                imageUrl ="/images/no-image.jpg";
            }
            model.addAttribute("coverImage", imageUrl );
            model.addAttribute("book", book.get());
            return "book/book";
        } else {
            return "book/book-not-found";
        }
    }
    
}
