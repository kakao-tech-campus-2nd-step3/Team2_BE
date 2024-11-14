package jeje.work.aeatbe.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.exception.TokenException;
import jeje.work.aeatbe.exception.TokenExpException;
import jeje.work.aeatbe.service.TokenService;
import jeje.work.aeatbe.service.UserService;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/article/likes")) {
            String token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                if (tokenService.isInBlackList(token)) {
                    throw new TokenException("이미로그아웃 된 사용자의 토큰입니다. 다시 로그인 해주세요");
                }

                if (jwtUtil.validTokenExpiration(token, true)) {
                    throw new TokenExpException("만료된 토큰입니다.");
                }


                if (!userService.validateToken(token)) {
                    throw new TokenException("올바르지 않은 토큰입니다.");
                }

                LoginUserInfo loginUserInfo = jwtUtil.getLoginUserInfo(token);
                return loginUserInfo;
            }

            return new LoginUserInfo(-1L,"123");
        }



        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            LoginUserInfo loginUserInfo = jwtUtil.getLoginUserInfo(token);
            return loginUserInfo;

        }
        throw new TokenException("권한이 없습니다.");
    }
}
