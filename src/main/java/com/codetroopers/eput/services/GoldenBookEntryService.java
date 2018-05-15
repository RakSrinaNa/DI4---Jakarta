package com.codetroopers.eput.services;

import com.codetroopers.eput.domain.GoldenBookEntryDAO;
import com.codetroopers.eput.domain.entities.GoldenBookEntry;
import com.codetroopers.eput.models.UserInfo;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * This class is annotated with the Stateless marker. It allows to automatically handle transactions.
 */
@Named
@Stateless
public class GoldenBookEntryService
{
	@Inject
	UserInfo userInfo;
	@Inject
	GoldenBookEntryDAO bookEntryDAO;
	@Inject
	EntityManager em;
	
	public void insertNewGoldenBookEntry(final GoldenBookEntry entry)
	{
		if(entry.getAuthor() == null)
			entry.setAuthor(userInfo.getName());
		entry.setCreatedAt(new Date());
		em.persist(entry);
	}
	
	@Produces
	@Named
	public List<GoldenBookEntry> loadGoldenBookEntries()
	{
		return bookEntryDAO.all();
	}
	
	public String deleteEntry(final GoldenBookEntry entry)
	{
		bookEntryDAO.delete(entry);
		return "";
	}
	
	public GoldenBookEntry getById(Long id)
	{
		return bookEntryDAO.getById(id);
	}
	
	public void updateBook(GoldenBookEntry book)
	{
		bookEntryDAO.update(book);
	}
}
