package com.myproject.myreadsapp.controller;



import java.util.List;
import java.util.stream.Collectors;

import com.myproject.myreadsapp.model.SearchResult;
import com.myproject.myreadsapp.model.SearchResultBooks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class SearchController {

    private WebClient webClient;
    private final String COVER_IMAGE_ROOT="https://covers.openlibrary.org/b/id/";

    
    public SearchController(WebClient.Builder wBuilder ){
        this.webClient = wBuilder
            .exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build())
            .baseUrl("http://openlibrary.org/search.json?")
            .build();
    }

    @GetMapping(value = "/search")
    public String getSearchBook(@RequestParam String query,Model model){
        SearchResult response = webClient.get()
            .uri("?q={query}",query)   
            .retrieve()
            .bodyToMono(SearchResult.class)
            .block();

        List<SearchResultBooks> results = response.getDocs()
                                            .stream()
                                            .limit(10)
                                            .map(res -> {
                                                res.setKey(res.getKey().replace("/works/", ""));
                                                String cover = res.getCover_i();
                                                if(StringUtils.hasText(cover)){
                                                    res.setCover_i(COVER_IMAGE_ROOT+cover+"-M.jpg");
                                                }else{
                                                    res.setCover_i("/images/no-image.jpg");
                                                }
                                                return res;
                                            })
                                            .collect(Collectors.toList());
        
        model.addAttribute("results", results);
        return "search";
    }
    
}
