package org.ulpmm.univrav.dao;

import java.util.List;

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
	 */
	public SearchResult searchInLdap(String searchFilter);
		
	/**
	 * Gets user's parameters from the ldap
	 * @param login user's login
	 */
	public List<String> getLdapUserInfos(String login);
}
