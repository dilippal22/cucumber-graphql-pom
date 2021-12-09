package com.graphql.pojo;


/**
 * This is the main POJO class for query and Json Variable
 * @author DilipP
 *
 */
public class GraphQLQuery {

	private String query;
	private Object queryVariables;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Object getQueryVariables() {
		return queryVariables;
	}

	public void setQueryVariables(Object queryVariables) {
		this.queryVariables = queryVariables;
	}

	

}
