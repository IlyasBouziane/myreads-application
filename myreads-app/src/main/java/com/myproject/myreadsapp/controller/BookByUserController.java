package com.myproject.myreadsapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.myproject.myreadsapp.model.BookByUser;
import com.myproject.myreadsapp.repository.BookByUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookByUserController {
    @Autowired
    private BookByUserRepository bookByUserRepository;

    private final String COVER_IMAGE_ROOT="https://covers.openlibrary.org/b/id/";


    @GetMapping
    public String getBooksByUser(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal, Model model){
        if(principal != null && principal.getAttribute("login") != null){
            String userId = principal.getAttribute("login");
            Slice<BookByUser> booksSlice = bookByUserRepository.findAllById(userId, CassandraPageRequest.of(0,100));
            List<BookByUser> booksList = booksSlice.getContent();
            booksList= booksList.stream().distinct().map(book->{
                String imageUrl;
                if(book.getCoverIds() != null) {
                    if(book.getCoverIds().size()>0 && book.getCoverIds()!=null){
                        imageUrl = COVER_IMAGE_ROOT+book.getCoverIds().get(0)+"-L.jpg";
                    }else{
                        imageUrl ="/images/no-image.jpg";
                    }
                } else {
                    imageUrl ="/images/no-image.jpg";
                }
                book.setCoverUrl(imageUrl);
                return book;    
            }).collect(Collectors.toList());
            model.addAttribute("books", booksList);
            return "home";

        } else {
            return "index";
        }
    }
    
}
