package org.ulpmm.univrav.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * Service implementation methods for LdapAccess.
 * 
 * @author morgan
 *
 */
public class LdapAccessImpl implements ILdapAccess {
	
	/** The context directory */
	private DirContext ctxtDir;
	/** the base dn*/
	private String LDAP_BASE_DN;
	/** the search filter */
	private String LDAP_SEARCH_FILTER;
	
	
	/**
	 * Initialize parameters
	 * @param dc the context directory
	 * @param LDAP_BASE_DN the base dn
	 * @param LDAP_SEARCH_FILTER the search filter
	 */
	public LdapAccessImpl(DirContext dc, String LDAP_BASE_DN, String LDAP_SEARCH_FILTER) {
		this.LDAP_BASE_DN = LDAP_BASE_DN;
		this.LDAP_SEARCH_FILTER = LDAP_SEARCH_FILTER;	
		this.ctxtDir=dc;
	}
	
		
	/**
	 * Search something in the LDAP
	 * @param login user's login
	 * @throws NamingException 
	 */
	public SearchResult searchInLdap(String login) throws NamingException {

		SearchResult entry = null;
			
		// Check the connection
		if(ctxtDir!=null) {

			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> answer = ctxtDir.search(LDAP_BASE_DN, "(&("+LDAP_SEARCH_FILTER+"="+login+"))", searchCtls);

			// We take the first entry because login is unique
			if (answer.hasMore()) {
				entry = (SearchResult) answer.next();
			} 

		}

		return entry;
	}
	
		
	/**
	 * Gets user's parameters from the ldap
	 * @param login user's login
	 * @throws Exception 
	 */
	public List<String> getLdapUserInfos(String login) throws Exception {

		List<String> userInfos = null;

			// LDAP Search user infos
			SearchResult searchResult = searchInLdap(login);

			if(searchResult!=null) {

				String email = searchResult.getAttributes().get("mail").get().toString();
				String firstname = searchResult.getAttributes().get("givenName").get().toString();
				String lastname = searchResult.getAttributes().get("sn").get().toString();
				String profile = searchResult.getAttributes().get("eduPersonAffiliation").get().toString();
				String establishment = searchResult.getAttributes().get("supannetablissement").get().toString();

				userInfos = new ArrayList<String>();
				userInfos.add(email!=null ? email : "");
				userInfos.add(firstname!=null ? firstname : "");
				userInfos.add(lastname!=null ? lastname : "");
				userInfos.add(profile!=null ? profile : "");
				userInfos.add(establishment!=null ? establishment : "");			
		}

		return userInfos;

	}
}
