package com.myproject.myreadsapp.controller;

import java.time.LocalDate;

import com.myproject.myreadsapp.model.Book;
import com.myproject.myreadsapp.model.BookByUser;
import com.myproject.myreadsapp.model.UserBookTrack;
import com.myproject.myreadsapp.model.UserBookTrackPrimaryKey;
import com.myproject.myreadsapp.repository.BookByUserRepository;
import com.myproject.myreadsapp.repository.BookRepository;
import com.myproject.myreadsapp.repository.UserBookTrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserBookController {

    @Autowired
    private UserBookTrackRepository bookTrackRepository;

    @Autowired
    private BookByUserRepository bookByUserRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/addBookTrack")
    public ModelAndView addBookTrack(@AuthenticationPrincipal OAuth2User principal,@RequestBody MultiValueMap<String,String> dataFromForm){
        
        
        UserBookTrack bookTrack = new UserBookTrack();
        bookTrack.setStart_reading(LocalDate.parse(dataFromForm.getFirst("startDate")));
        bookTrack.setComplete_reading(LocalDate.parse(dataFromForm.getFirst("completeDate")));
        bookTrack.setReadingStatus(dataFromForm.getFirst("readingStatus"));
        bookTrack.setBookRating(Integer.parseInt(dataFromForm.getFirst("bookRating")));
        
        UserBookTrackPrimaryKey key = new UserBookTrackPrimaryKey();
        String bookId = dataFromForm.getFirst("bookId");
        key.setBookId(bookId);
        key.setUserId(principal.getAttribute("login"));
        bookTrack.setBookTrackByUserId(key);

        bookTrackRepository.save(bookTrack);

        // add the book to My Books
        Book book= bookRepository.findById(bookId).get();
        BookByUser booksByUser = new BookByUser();
        booksByUser.setid(principal.getAttribute("login"));
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getBookName());
        booksByUser.setCoverIds(book.getCoversIds());
        booksByUser.setAuthorNames(book.getAuthorsNames());
        booksByUser.setReadingStatus(dataFromForm.getFirst("readingStatus"));
        System.out.println(Integer.parseInt(dataFromForm.getFirst("bookRating")));
        booksByUser.setRating(Integer.parseInt(dataFromForm.getFirst("bookRating")));
        bookByUserRepository.save(booksByUser);
        return new ModelAndView("redirect:/books/"+bookId);
    }
    

}
