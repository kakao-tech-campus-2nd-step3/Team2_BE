package jeje.work.aeatbe.config;

import jeje.work.aeatbe.interceptor.JwtInterceptor;
import jeje.work.aeatbe.resolver.LoginUserArgumentResolver;
import jeje.work.aeatbe.service.TokenService;
import jeje.work.aeatbe.service.UserService;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserService userService;
    private final JwtInterceptor jwtInterceptor;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/article/likes/**")
                .excludePathPatterns("/api/article/likes/count/**")
                .addPathPatterns("/api/users/logout/**")
                .addPathPatterns("/api/wishlist/**")
                .addPathPatterns("/api/reviews/my/**")
                .addPathPatterns("/api/users/info/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserArgumentResolver(jwtUtil));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }


}
