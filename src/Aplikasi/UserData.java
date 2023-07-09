package Aplikasi;

public class UserData{
    private static UserData instance;
    private String email;
    private String password;

    private UserData(){
    }

    public static UserData getInstance(){
        if(instance==null){
            instance = new UserData();
        }
        return instance;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }

}