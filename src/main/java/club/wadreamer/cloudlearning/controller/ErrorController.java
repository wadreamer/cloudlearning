package club.wadreamer.cloudlearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ErrorController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/2 15:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("/error")
public class ErrorController {

    private String prefix = "error";

    @GetMapping("403")
    public String error403(){
        return prefix + "/403";
    }
}
