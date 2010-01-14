package org.ulpmm.univrav.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * Service implementation methods for LdapAccess.
 * 
 * @author morgan
 *
 */
public class LdapAccessImpl implements ILdapAccess {
	
	/** the base dn*/
	private String LDAP_BASE_DN;
	/** the search filter */
	private String LDAP_SEARCH_FILTER;
	/** Ldap environment */
	private Hashtable<?,?> env;
	
	
	/**
	 * Initialize parameters
	 * @param env ldap environment
	 * @param LDAP_BASE_DN the base dn
	 * @param LDAP_SEARCH_FILTER the search filter
	 */
	public LdapAccessImpl(Hashtable<?,?> env, String LDAP_BASE_DN, String LDAP_SEARCH_FILTER) {
		this.env=env;
		this.LDAP_BASE_DN = LDAP_BASE_DN;
		this.LDAP_SEARCH_FILTER = LDAP_SEARCH_FILTER;	
	}
	
		
	/**
	 * Search something in the LDAP
	 * @param login user's login
	 */
	public SearchResult searchInLdap(String login) {
		
		SearchResult entry = null;
		DirContext ctxtDir = null;
		NamingEnumeration<SearchResult> answer = null;
		
		if(env!=null) {

			try {
				//open ldap connection
				ctxtDir = (DirContext) new InitialDirContext(env);

				// Check the connection
				if(ctxtDir!=null) {

					SearchControls searchCtls = new SearchControls();
					searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
					answer = ctxtDir.search(LDAP_BASE_DN, "(&("+LDAP_SEARCH_FILTER+"="+login+"))", searchCtls);

					// We take the first entry because login is unique
					if (answer.hasMore()) {
						entry = (SearchResult) answer.next();
					} 
				}
			}
			catch(NamingException e) {
				e.printStackTrace();
			}
			finally {
				try {
					answer.close();
					ctxtDir.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}

		return entry;
	}
	
		
	/**
	 * Gets user's parameters from the ldap
	 * @param login user's login
	 */
	public List<String> getLdapUserInfos(String login){

		List<String> userInfos = null;

		try {
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
		}
		catch(NamingException e) {
			e.printStackTrace();
		}

		return userInfos;
	}
}
