package com.omicron.ctest;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user2")
public class User implements Serializable {

    private static final String THUMBNAIL = "thumbnail.jpg";
    
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Column
    @NotNull
    @Size(min = 2, max = 30)
    private String surname;

    @Column
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/mm/yyyy")
    private String dob;

    @Column
    @NotNull
    @Size(min = 2, max = 60)
    private String email;

    @Column(nullable = true, length = 64)
    private String photo;

    protected User() {

    }

    protected User(Long id, String name, String surname, String dob, String email, String photo) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photo == null) {
            return null;
        }

        return "/user-photos/" + id + "/" + photo;
    }
    
    @Transient
    public String getThumbnailPhotosImagePath() {
        if (photo == null) {
            return null;
        }

        return "/user-photos/" + id + "/" + User.THUMBNAIL;
    }
}
