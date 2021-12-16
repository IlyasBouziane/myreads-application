package com.myproject.myreadsapp.controller;



import com.myproject.myreadsapp.model.SearchResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private WebClient webClient;
    
    public SearchController(WebClient.Builder wBuilder ){
        this.webClient = wBuilder
            .exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build())
            .baseUrl("http://openlibrary.org/search.json?")
            .build();
    }

    @GetMapping(value = "/search")
    public String getSearchBook(@RequestParam String query,Model model){
        SearchResult result = webClient.get()
            .uri("?q={query}",query)   
            .retrieve()
            .bodyToMono(SearchResult.class)
            .block();
        
        model.addAttribute("result", result);
        return "search";
    }
    
}
