//package jeje.work.aeatbe.controller;
//
//import jeje.work.aeatbe.service.HaccpService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.view.RedirectView;
//
//
///**
// * haccp api 호출용 컨트롤러
// */
//@RestController
//@RequestMapping("/api/test")
//public class HaccpController {
//
//    private final HaccpService haccpService;
//
//    @Autowired
//    public HaccpController(HaccpService haccpService) {
//        this.haccpService = haccpService;
//    }
//
//    /**
//     * api 호출
//     */
//    @GetMapping
//    public void redirectToHaccpApi() {
////        String apiUrl = haccpService.getProductApiUrl(); // 생성된 URL 가져오기
////        System.out.println("Redirecting to: " + apiUrl); // 리다이렉트할 URL 로그 출력
//
//        haccpService.getProductApi();
////        return new RedirectView(apiUrl); // 해당 URL로 리다이렉트
//    }
//}
