package jeje.work.aeatbe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeje.work.aeatbe.exception.TokenException;
import jeje.work.aeatbe.exception.TokenExpException;
import jeje.work.aeatbe.service.TokenService;
import jeje.work.aeatbe.service.UserService;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private JwtUtil jwtUtil;
    private UserService userService;
    private TokenService tokenService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (header == null || !header.startsWith("Bearer ")) {
            throw new TokenException("헤더에 올바른 인증 토큰이 필요합니다.(Bearer )");
        }

        String token = header.substring(7);

        if (tokenService.isInBlackList(token)) {
            throw new TokenException("이미로그아웃 된 사용자의 토큰입니다. 다시 로그인 해주세요");
        }

        if (jwtUtil.validTokenExpiration(token, true)) {
            throw new TokenExpException("만료된 토큰입니다.");
        }


        if (!userService.validateToken(token)) {
            throw new TokenException("올바르지 않은 토큰입니다.");
        }


        return true;


    }
}
