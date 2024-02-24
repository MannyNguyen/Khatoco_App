package khatoco.tvc.com.khatoco.ui.objects;

/**
 * Created by prosoft on 9/16/16.
 */
public class UserInfo extends BaseModelObject {

    private String email;
    private String password;

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private void update() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getKeyPath() {
        return "user";
    }


}
