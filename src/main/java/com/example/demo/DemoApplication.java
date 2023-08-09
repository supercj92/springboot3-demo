package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@SpringBootApplication
//@ComponentScan(basePackages = {"org.springframework.session.config.annotation.web.http"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		System.out.println("hello1234");
		return "hello";
	}

	@GetMapping("/world")
	@ResponseBody
	public String world(HttpSession httpSession, HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("key123", "value123");
		return "world";
	}

	@GetMapping("/sse")
	@CrossOrigin(origins = "http://localhost:63343")
	public SseEmitter sseEmitter() {
		SseEmitter emitter = new SseEmitter();
		// 设置超时时间
		//emitter.setTimeout(60_000L);
		// 发送数据
		int i = 0;
		while (true) {
			i++;
			try {
				emitter.send(SseEmitter.event()
					.id("_" + i)
					.data("_Data " + i).name("_name " + i)
					.build());
				Thread.sleep(1000L);
			} catch (Exception e) {
				emitter.completeWithError(e);
				return emitter;
			}
		}
		// 完成
		//emitter.complete();
		//return emitter;
	}

}
