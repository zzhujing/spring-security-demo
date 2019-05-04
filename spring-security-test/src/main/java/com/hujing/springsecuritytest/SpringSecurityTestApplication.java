package com.hujing.springsecuritytest;

import com.fasterxml.jackson.annotation.JsonView;
import com.hujing.springsecuritytest.domain.User;
import com.hujing.springsecuritytest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


//排除spring-security自动配置
@SpringBootApplication
@RestController
@Slf4j
@ComponentScan("com.hujing")
@EnableRedisHttpSession
public class SpringSecurityTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityTestApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @GetMapping("/test")
    @JsonView(User.UserInfoView.class)
    public ResponseEntity<User> getTest() {
        return ResponseEntity.ok(userRepository.findById(1).orElse(null));
    }

    @GetMapping("/test/{id}")
    @JsonView(User.UserDetailView.class)
    public User getTestById(@PathVariable Integer id) {
        return userRepository.getOne(id);
    }

    @PostMapping("/test")
    public User insert(@Validated({User.Insert.class}) @RequestBody(required = false) User user) {
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(error->{
//                FieldError fieldError = (FieldError) error;
//                log.warn("get arguments valid message  fieldName: {} , error Msg : {}",fieldError.getField(),error.getDefaultMessage());
//            });
//        }
        return userRepository.save(user);
    }


    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        try {
            File localFile = new File(System.currentTimeMillis() + ".txt");
            file.transferTo(localFile);
            return localFile.getAbsolutePath();
        } catch (Exception e) {
            log.warn("【文件上传失败】");
            return null;
        }
    }


    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) {
        try (
                InputStream is = new FileInputStream(new File("D:\\workspace\\spring-security-demo\\spring-security-test\\src\\test\\java\\com\\hujing\\1556686696558.txt"));
                OutputStream output = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition","attachment:filename=test.txt");
            IOUtils.copy(is, output);
            output.flush();
        } catch (Exception e) {
            log.warn("【文件下载失败】");
        }
    }

    /**
     * 注册方法
     * @param user
     * @return
     */
    @PostMapping("/user/register")
    public String register(User user,HttpServletRequest request) {
        //将注册信息和第三方授权信息绑定在一起添加到数据库中
        providerSignInUtils.doPostSignUp(user.getUsername(), new ServletWebRequest(request));
        return "注册成功";
    }
}
