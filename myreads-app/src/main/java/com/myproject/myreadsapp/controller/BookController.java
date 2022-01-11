package com.myproject.myreadsapp.controller;

import java.util.Optional;

import com.myproject.myreadsapp.model.Book;
import com.myproject.myreadsapp.model.UserBookTrack;
import com.myproject.myreadsapp.model.UserBookTrackPrimaryKey;
import com.myproject.myreadsapp.repository.BookRepository;
import com.myproject.myreadsapp.repository.UserBookTrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookTrackRepository bookTrackRepository;

    private final String COVER_IMAGE_ROOT="https://covers.openlibrary.org/b/id/";

    @GetMapping(value = "/books/{bookId}")
    public String getBookById(@PathVariable String bookId, Model model,@AuthenticationPrincipal OAuth2User principal){
        Optional<Book> book = bookRepository.findById(bookId);
        String imageUrl;
        if(book.isPresent()){
            if(book.get().getCoversIds() != null) {
                if(book.get().getCoversIds().size()>0 && book.get().getCoversIds()!=null){
                    imageUrl = COVER_IMAGE_ROOT+book.get().getCoversIds().get(0)+"-L.jpg";
                }else{
                    imageUrl ="/images/no-image.jpg";
                }
            } else {
                imageUrl ="/images/no-image.jpg";
            }
           

            if(principal != null && principal.getAttribute("login") !=null ){
                model.addAttribute("loginId",principal.getAttribute("login"));
                UserBookTrackPrimaryKey key = new UserBookTrackPrimaryKey();
                key.setUserId(principal.getAttribute("login"));
                key.setBookId(bookId);
                Optional<UserBookTrack> bookTrack = bookTrackRepository.findById(key);
                if(bookTrack.isPresent()){
                    model.addAttribute("book_track", bookTrack.get());
                } else {
                    model.addAttribute("book_track", new UserBookTrack());
                }
            }
            model.addAttribute("coverImage", imageUrl );
            model.addAttribute("book", book.get());
            return "book/book";
        } else {
            return "book/book-not-found";
        }
    }
    
}
