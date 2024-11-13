package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.service.SearchShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test2")
public class SearchShopController {

    private final SearchShopService searchShopService;

    @Autowired
    public SearchShopController(SearchShopService searchShopService) {
        this.searchShopService = searchShopService;
    }

    @GetMapping
    public void getSellerApi() {
//        searchShopService.getShopApi(); // 단순 호출 테스트
        searchShopService.fetchProductsFromApi();
    }

}
