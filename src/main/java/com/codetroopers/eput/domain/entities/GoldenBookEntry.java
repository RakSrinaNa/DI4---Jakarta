package com.codetroopers.eput.domain.entities;

import javax.persistence.*;
import java.util.Date;


@Entity
public class GoldenBookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String author;
    private String content;
    private int note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public GoldenBookEntry() {
        this.createdAt = new Date();
    }

    public GoldenBookEntry(final String author, final String content, int note) {
        this(author, content, note, new Date());
    }

    public GoldenBookEntry(final String author, final String content, int note, final Date createdAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    /************************** GETTER / SETTERS ****************************/
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
    
    public int getNote()
    {
        return note;
    }
    
    public void setNote(int note)
    {
        this.note = note;
    }
}
