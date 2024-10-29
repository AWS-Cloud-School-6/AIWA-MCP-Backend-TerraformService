package AIWA.McpBackend_Terraform.Controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/terraform")
    public String home() {
        return "home"; // Spring Boot는 static/index.html을 제공해야 함
    }
}