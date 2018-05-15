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
	private String title;
	private int note;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> tags = new ArrayList<>();
	
	public GoldenBookEntry()
	{
		this.createdAt = new Date();
		tags.add("");
		tags.add("");
		tags.add("");
		tags.add("");
		tags.add("");
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public GoldenBookEntry(final String author, final String content, int note)
	{
		this(author, content, note, new Date());
	}
	
	public GoldenBookEntry(final String author, final String title, final String content, int note)
	{
		this(author, content, note, new Date());
		this.title = title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public GoldenBookEntry(final String author, final String content, int note, final Date createdAt)
	{
		this.author = author;
		this.content = content;
		this.createdAt = createdAt;
		tags.add("");
		tags.add("");
		tags.add("");
		tags.add("");
		tags.add("");
	}
	
	public void cleanTags()
	{
		tags = tags.stream().filter(s -> s != null && !s.isEmpty()).distinct().collect(Collectors.toList());
	}
	
	/************************** GETTER / SETTERS ****************************/
	public Long getId()
	{
		return id;
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
