package urlshortener.common.domain;

import java.sql.Date;

public class User {

    private String name;
    private String pass;
    private String email;
    private Boolean admin;
    private Date created;

    public User(String name, String pass, String email, Boolean admin, Date created) {
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.admin = admin;
        this.created = created;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreated() {
        return created;
    }

    public Boolean getAdmin() {
        return admin;
    }

}
