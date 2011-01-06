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

import org.apache.log4j.Logger;

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
	/** ldap infos*/
	private List<String> ldapinfos;
	
	/** Logger log4j */
	private static final Logger logger = Logger.getLogger(LdapAccessImpl.class);
	
	
	/**
	 * Initialize parameters
	 * @param env ldap environment
	 * @param LDAP_BASE_DN the base dn
	 * @param LDAP_SEARCH_FILTER the search filter
	 * @param ldapinfos the ldap informations
	 */
	public LdapAccessImpl(Hashtable<?,?> env, String LDAP_BASE_DN, String LDAP_SEARCH_FILTER, List<String> ldapinfos) {
		this.env=env;
		this.LDAP_BASE_DN = LDAP_BASE_DN;
		this.LDAP_SEARCH_FILTER = LDAP_SEARCH_FILTER;	
		this.ldapinfos = ldapinfos;
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
				logger.error("Ldap search error",e);
			}
			finally {
				try {
					answer.close();
					ctxtDir.close();
				} catch (NamingException e) {
					logger.error("Ldap close error",e);
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
										
				String email = ldapinfos.get(0)!=null ? searchResult.getAttributes().get(ldapinfos.get(0)).get().toString() : "";
				String firstname = ldapinfos.get(1)!=null ? searchResult.getAttributes().get(ldapinfos.get(1)).get().toString() : "";
				String lastname = ldapinfos.get(2)!=null ? searchResult.getAttributes().get(ldapinfos.get(2)).get().toString() : "";
				String profile = ldapinfos.get(3)!=null ? searchResult.getAttributes().get(ldapinfos.get(3)).get().toString() : "";
				String establishment = ldapinfos.get(4)!=null ? searchResult.getAttributes().get(ldapinfos.get(4)).get().toString() : "";
				String etpPrimaryCode = ldapinfos.get(5)!=null ? (searchResult.getAttributes().get(ldapinfos.get(5))!=null ? searchResult.getAttributes().get(ldapinfos.get(5)).get().toString() : "") : "";
				
				userInfos = new ArrayList<String>();
				userInfos.add(email!=null ? email : "");
				userInfos.add(firstname!=null ? firstname : "");
				userInfos.add(lastname!=null ? lastname : "");
				userInfos.add(profile!=null ? profile : "");
				userInfos.add(establishment!=null ? establishment : "");	
				userInfos.add(etpPrimaryCode!=null ? etpPrimaryCode : "");
			}
		}
		catch(NamingException e) {
			logger.error("Ldap search error",e);
		}

		return userInfos;
	}
}
