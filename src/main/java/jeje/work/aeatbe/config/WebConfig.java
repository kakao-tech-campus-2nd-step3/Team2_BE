package jeje.work.aeatbe.config;

import java.util.List;
import jeje.work.aeatbe.interceptor.JwtInterceptor;
import jeje.work.aeatbe.resolver.LoginUserArgumentResolver;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private JwtInterceptor jwtInterceptor;

    private JwtUtil jwtUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/users/**")
            .addPathPatterns("/api/article/likes/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserArgumentResolver(jwtUtil));
    }

}
