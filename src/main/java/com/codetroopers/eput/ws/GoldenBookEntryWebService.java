package com.codetroopers.eput.ws;

import com.codetroopers.eput.domain.entities.GoldenBookEntry;
import com.codetroopers.eput.domain.entities.User;
import com.codetroopers.eput.services.GoldenBookEntryService;
import com.codetroopers.eput.services.UserService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sample REST WebService, it will be under "ws" app path
 */
//tag::class[]
@ApplicationPath("ws") // <1>
@Path("/goldenbook") // <2>
@Produces(MediaType.APPLICATION_JSON) // <3>
public class GoldenBookEntryWebService extends Application
{
	@Inject
	GoldenBookEntryService entryService;
	
	@Inject
	UserService userService;
	
	class RESTBook extends RESTBookFail
	{
		private final Long entryId;
		
		public RESTBook(GoldenBookEntry book)
		{
			super(book.getTitle(), book.getContent(), userService.getByName(book.getAuthor()).id);
			this.entryId = book.getId();
		}
		
		public Long getEntryId()
		{
			return entryId;
		}
	}
	
	class RESTBookFail
	{
		private final String title;
		private final String body;
		private final Long userId;
		
		public RESTBookFail(String title, String body, Long userId)
		{
			this.title = title;
			this.body = body;
			this.userId = userId;
		}
		
		public String getTitle()
		{
			return title;
		}
		
		public String getBody()
		{
			return body;
		}
		
		public Long getUserId()
		{
			return userId;
		}
	}
	
	@GET // <4>
	public List<RESTBook> entries()
	{
		return entryService.loadGoldenBookEntries().stream().map(RESTBook::new).collect(Collectors.toList());
	}
	
	@POST // <4>
	public RESTBookFail create(@QueryParam("title") String title, // <5>
			@QueryParam(value = "body") String body, @QueryParam(value = "userId") Long userId)
	{
		try
		{
			User user = userService.getById(userId);
			if(user != null)
			{
				GoldenBookEntry entry = new GoldenBookEntry(user.name, title, body, 0);
				entryService.insertNewGoldenBookEntry(entry);
				return new RESTBook(entry);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new RESTBookFail(title, body, userId);
	}
	
	@Path("/rating")
	@PUT
	public Object create(@QueryParam("entryId") Long entryID, // <5>
			@QueryParam(value = "rating") Integer note, @QueryParam(value = "userId") Long userId)
	{
		try
		{
			User user = userService.getById(userId);
			if(note <= 10 && note >= 0)
			{
				if(user != null)
				{
					GoldenBookEntry book = entryService.getById(entryID);
					if(book != null)
					{
						if(Objects.equals(book.getAuthor(), user.name))
						{
							book.setNote(note);
							entryService.updateBook(book);
							return new Object()
							{
								public Long entryId = entryID;
								public int rating = note;
								
								public Long getEntryId()
								{
									return entryId;
								}
								
								public int getRating()
								{
									return rating;
								}
							};
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new Object(){
			public int rating = note;
		};
	}
	
	@DELETE
	public int delete(@QueryParam("entryId") Long entryID, // <5>
			@QueryParam(value = "userId") Long userId)
	{
		try
		{
			User user = userService.getById(userId);
			if(user != null)
			{
				GoldenBookEntry book = entryService.getById(entryID);
				if(book != null)
				{
					if(Objects.equals(book.getAuthor(), user.name))
					{
						entryService.deleteEntry(book);
						return 200;
					}
					else
						return 403;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 404;
	}
}
//end::class[]
