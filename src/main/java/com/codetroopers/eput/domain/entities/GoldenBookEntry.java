package com.codetroopers.eput.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class GoldenBookEntry
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String author;
	private String content;
	private int note;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@ElementCollection
	private List<String> tags;
	
	public GoldenBookEntry()
	{
		this.createdAt = new Date();
		this.tags = new ArrayList<>(3);
		this.tags.add("");
		this.tags.add("");
		this.tags.add("");
	}
	
	public GoldenBookEntry(final String author, final String content, int note)
	{
		this(author, content, note, new Date());
	}
	
	public GoldenBookEntry(final String author, final String content, int note, final Date createdAt)
	{
		this.author = author;
		this.content = content;
		this.createdAt = createdAt;
		this.tags = new ArrayList<>(3);
	}
	
	/************************** GETTER / SETTERS ****************************/
	public Long getId()
	{
		return id;
	}
	
	public String getTagsAsList()
	{
		return tags.stream().map(s -> "#" + s).collect(Collectors.joining(", "));
	}
	
	public void setId(final Long id)
	{
		this.id = id;
	}
	
	public Date getCreatedAt()
	{
		return createdAt;
	}
	
	public void setCreatedAt(final Date createdAt)
	{
		this.createdAt = createdAt;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(final String content)
	{
		this.content = content;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(final String author)
	{
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
	
	public List<String> getTags()
	{
		return tags;
	}
	
	public void addTag(String tag)
	{
		this.tags.add(tag);
	}
	
	public void removeTag(String tag)
	{
		this.tags.remove(tag);
	}
	
	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}
}
