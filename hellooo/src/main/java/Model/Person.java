package Model;

import java.util.Date;

public class Person {

        private String username;
        private final Date birth;
        private String sex;

    public Person(String username, String sex, Date birth) {
        this.username = username;
        this.sex = sex;
        this.birth = birth;
    }

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }

    public Date getbirth() {
        return birth;
    }

    public void setUsername() {
        this.username = username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

