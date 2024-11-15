package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.service.AllergyCategoryService;
import jeje.work.aeatbe.service.FreeFromCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/freefromCategories")
public class FreeFromCategoryController {

    @Autowired
    private FreeFromCategoryService freeFromCategoryService;

    /**
     * 프리프롬 카테고리 전부 반환하는 기능
     * @return 프리프롬 카테고리 dto
     */
    @GetMapping("/all")
    public ResponseEntity<List<FreeFromCategoryDTO>> all() {
        return ResponseEntity.ok().body(freeFromCategoryService.getAllFreeFromCategory());
    }
}
