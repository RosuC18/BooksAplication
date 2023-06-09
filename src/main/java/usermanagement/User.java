package usermanagement;

public class User {
    private String email; // username = email
    private String pwd;
    private String confirmPwd;
    private boolean accept;

    private int id;

    public User(String email, String pwd, String confirmPwd, boolean accept) {
        this.email = email;
        this.pwd = pwd;
        this.confirmPwd = confirmPwd;
        this.accept = accept;

    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                ", accept=" + accept +
                ", id=" + id +
                '}';
    }
}
