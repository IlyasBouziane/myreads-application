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

    @GetMapping(value = "/books/{bookId}")
    public String getBookById(@PathVariable String bookId, Model model){
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent()){
            model.addAttribute("book", book.get());
            System.out.println(book.get().getBookName());
            return "book/book";
        } else {
            return "book/book-not-found";
        }
    }
    
}
