package br.com.starter;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.starter.admin.entity.Log;
import br.com.starter.admin.repository.LogRepository;

@Configuration
public class HttpRequestInterceptorConfigurer implements WebMvcConfigurer {

	@Autowired
	private LogRepository logRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {

			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {

				try {
					Log log = new Log();
					log.setStatus(response.getStatus());
					log.setMetodo(request.getMethod());
					log.setUri(request.getRequestURI());
					log.setIp(request.getRemoteAddr());
					log.setMetodoJava(handler.toString());
					log.setUser(request.getUserPrincipal().getName());
					log.setDtEvento(LocalDateTime.now());
					logRepository.save(log);
				} catch (Exception e) {

				}

				return true;
			}

			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
			}

		});

	}

}