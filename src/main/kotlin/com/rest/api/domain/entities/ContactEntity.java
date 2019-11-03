package com.rest.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.api.domain.entities.audit.DateAudit;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "contacts")
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE contacts SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class ContactEntity extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private UserEntity user;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(name = "media_url")
    private String media_url;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false, name = "gender")
    private String gender;

    @Column(nullable = false, name = "deleted", columnDefinition = "boolean default false")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted = false;

    public ContactEntity() {
        super();
    }

    public ContactEntity(Long id, UserEntity user, String firstName, String lastName, String media_url, LocalDate birthDate, String gender) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.media_url = media_url;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String firstName) {
        this.lastName = firstName;
    }

    public String getMedia_url() { return media_url; }

    public void setMedia_url(String media_url) { this.media_url = media_url; }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public ContactEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public boolean isDeleted() { return deleted; }

    private void setDeleted() { this.deleted = true; }

    @PreRemove
    public void onDelete(){
        setDeleted();
    }
}