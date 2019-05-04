package com.hujing.springsecuritytest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.hujing.springsecuritytest.annotation.MyConstrain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hj
 * @create 2019-05-01 8:05
 */
@Entity
@Table(name = "tb_test")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    private static final long serialVersionUID = -7197993639182568146L;

    public interface UserInfoView {}
    public interface UserDetailView extends UserInfoView {}

    public interface Insert{};
    public interface Update{};
    @JsonView(UserInfoView.class)
    @NotEmpty(message = "用户名不能为空")
    @MyConstrain(message = "用户名一定要为hi~",groups = Update.class)
    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(UserDetailView.class)
    @NotEmpty(message = "密码不能为空")
    private String password;

    @Past(message = "出生日期不能为未来时间")
    private Date birthday;

}
