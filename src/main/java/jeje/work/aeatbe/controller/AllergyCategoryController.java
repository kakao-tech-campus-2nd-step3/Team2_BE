package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.service.AllergyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 알러지 카테고리를 조회하는 컨트롤러
 * 조회 이외의 목적을 가지고 개발되지 않았으니, 이를 사용하고지 한다면 문의 바랍니다.
 *
 * @author jjh4450git@gmail.com
 * @see AllergyCategoryService
 */
@RestController
@RequestMapping("/api/allergyCategorie")
public class AllergyCategoryController {

    @Autowired
    private AllergyCategoryService allergyCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<AllergyCategoryDTO>> all() {
        return ResponseEntity.ok().body(allergyCategoryService.getAllAllergyCategory());
    }
}
