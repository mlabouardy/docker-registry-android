package labouardy.com.dockerregistry.model;

/**
 * Created by mlabouardy on 20/02/16.
 */
public class Authorization {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
