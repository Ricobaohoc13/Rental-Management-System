package Model;

import java.util.Date;

public class Person {
    private String username;
    private Date birthday;
    private String gender;

    public String getUsername() {
        return username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
