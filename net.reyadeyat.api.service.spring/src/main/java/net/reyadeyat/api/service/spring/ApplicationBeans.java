package net.reyadeyat.api.service.spring;

import net.reyadeyat.api.library.http.MainFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationBeans {
    @Autowired
    private Environment environment;
    
    @Bean
    public DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite(environment.getProperty("net.reyadeyat.api.library.cookie.same_site"));
        return serializer;
    }
    
    @Bean
    public FilterRegistrationBean<MainFilter> customRequestFilter() {
        FilterRegistrationBean<MainFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MainFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all URL patterns
        return registrationBean;
    }
    
    /*@ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
            // Handle the exception and return an appropriate response
            String errorMessage = "Maximum upload size exceeded. Please upload a smaller file.";
            return ResponseEntity.status(HttpStatusCode.valueOf(413)).body(errorMessage);
        }
    }*/
    
    /*@ControllerAdvice
    public class FileUploadExceptionAdvice {

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request, HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView("file");
            modelAndView.getModel().put("message", "File too large!");
            return modelAndView;
        }
    }*/
    
    @GetMapping("/")
    @ResponseBody
    public String running() {
        return "Running";
    }
    
    @ModelAttribute("MappedList")
    public Map<String, String> getListMapped(@RequestParam(value = "list", required = false) String[] list,
            HttpServletRequest request, HttpServletResponse response) {
        if (list == null) {
            return new HashMap<String, String>();
        }
        String[] list_list = request.getParameterValues("list");
        CustomObject customObject = new CustomObject(list);
        return customObject.getMap();
    }

    //https://localhost:19000/list?list=v1&list=v2&list=v3
    @GetMapping("/list")
    @ResponseBody
    public Map<String, String> getListMappedRequest(@ModelAttribute("MappedList") Map<String, String> map) {
        return map;
    }
    
    @ModelAttribute("Decrypt")
    public String getDecrypted(@RequestParam(value = "decrypt", required = false) String message,
            HttpServletRequest request, HttpServletResponse response) {
        return message + " - 123";
    }
    
    //https://localhost:19000/decrypt?decrypt=some_text
    @GetMapping("/decrypt")
    @ResponseBody
    public String getDecryptedRequest(@ModelAttribute("Decrypt") String message) {
        return message + " - ABCD";
    }
}

class CustomObject {
    HashMap<String, String> map;
    CustomObject(String[] list) {
        map = new HashMap<>();
        for (String string : list) {
            map.put(string, string);
        }
    }
    
    Map<String, String> getMap() {
        return map;
    }
    
}