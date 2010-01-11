package org.ulpmm.univrav.dao;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

/**
 * Interface for service implementation methods
 * 
 * @author morgan
 *
 */
public interface ILdapAccess {
			
	/**
	 * Search something in the LDAP
	 * @param searchFilter the search filter
	 * @throws NamingException 
	 */
	public SearchResult searchInLdap(String searchFilter) throws NamingException;
		
	/**
	 * Gets user's parameters from the ldap
	 * @param login user's login
	 * @throws Exception 
	 */
	public List<String> getLdapUserInfos(String login) throws Exception;
}
