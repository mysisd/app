package Utils;

/**
 * Created by Administrator on 2018/3/12 0012.
 * 短信验证码所需要的类
 */

public class SmsUser {
    private String name;
    private String code;

    public SmsUser(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public SmsUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

