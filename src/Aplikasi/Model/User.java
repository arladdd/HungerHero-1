package Aplikasi.Model;

public class User {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String name,String email, String password, String phoneNumber, String address){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
