package javaway.god.proxy.domain;

import javaway.god.proxy.inter.UserInfoInterface;
import lombok.Data;

/**
 * 类说明: 用户信息
 *
 * @author zengpeng
 */
@Data
public class UserInfo implements UserInfoInterface {
    private String username;
    private Integer age;
    private Integer sex;


    public void print(){
        System.out.println("username: " + username + ",age: " + age + ",sex: " + sex);
    }
}
