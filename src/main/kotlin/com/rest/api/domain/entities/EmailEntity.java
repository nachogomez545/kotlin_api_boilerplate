package com.rest.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.api.domain.entities.audit.DateAudit;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "emails")
@Inheritance(strategy = InheritanceType.JOINED)
public class EmailEntity extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private ContactEntity contact;

    @Column(nullable = false, name = "value", unique = true)
    private String value;

    public EmailEntity() {
        super();
    }

    public EmailEntity(ContactEntity contact, String value) {
        this.contact = contact;
        this.value = value;
    }

    public EmailEntity(Long id, ContactEntity contact, String value) {
        this.id = id;
        this.contact = contact;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactEntity getContact() {
        return contact;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}