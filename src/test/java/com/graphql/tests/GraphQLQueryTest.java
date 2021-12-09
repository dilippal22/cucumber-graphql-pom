package com.graphql.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.graphql.pojo.GraphQLQuery;
import com.graphql.pojo.QueryVariable;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author DilipP
 * @URI https://api.spacex.land/graphql
 * 		https://graphql.github.com/graphql/proxy
 */

public class GraphQLQueryTest {
	
	//Getting SpaceX Mission Detail Info
	
	@Test
	public void getSpacexMissionNameTest() {

		RestAssured.baseURI = "https://api.spacex.land";
		String query = "\"query($order: String!) {\\n  launchesPast(limit: 10, order: $order) {\\n    mission_name\\n    launch_date_local\\n    launch_site {\\n      site_name_long\\n    }\\n    links {\\n      article_link\\n      video_link\\n    }\\n    rocket {\\n      rocket_name\\n      first_stage {\\n        cores {\\n          flight\\n          core {\\n            reuse_count\\n            status\\n          }\\n        }\\n      }\\n      second_stage {\\n        payloads {\\n          payload_type\\n          payload_mass_kg\\n          payload_mass_lbs\\n        }\\n      }\\n    }\\n    ships {\\n      name\\n      home_port\\n      image\\n    }\\n    id\\n  }\\n}\\n\"";		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(query)
				.when().log().all()
					.post("/graphql")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.launchesPast[0].mission_name", equalTo("Starlink-15 (v1.0)"));
	}
	
	//Getting SpaceX Mission Name and ID only
	
	@Test
	public void getAllSpacexMissionNameTest() {
		//https://api.spacex.land/graphql
		
		RestAssured.baseURI = "https://api.spacex.land";
		String query = "\"query ($order: String!) {\\n  launchesPast(limit: 10, order: $order) {\\n    mission_name\\n    id\\n  }\\n}\\n\"";
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(query)
				.when().log().all()
					.post("/graphql")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.launchesPast[0].mission_name", equalTo("Starlink-15 (v1.0)"));
		
	}
	
	//Data Provider Method
	
	@DataProvider
	public Object[][] getQueryData() {
		
		return new Object[][] {{"10"}, {"ASC"}};
	}
	

	//Getting SpaceX Mission Name with Data Parameterization
	
	@Test
	public void getAllSpacexMissionNameWithDataTest(String limit, String order) {
		//https://api.spacex.land/graphql
		
		RestAssured.baseURI = "https://api.spacex.land";
		String query = "{\n  launchesPast(limit: "+limit+", order: \""+order+"\") {\n    mission_name\n    id\n  }\n}\n";
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(query)
				.when().log().all()
					.post("/graphql")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.launchesPast[0].mission_name", equalTo("Starlink-15 (v1.0)"));
		
	}
	
	
	@Test
	public void getAllSpaceXMissionPojoTest() {
		RestAssured.baseURI = "https://api.spacex.land";
		
		GraphQLQuery query = new GraphQLQuery();
		query.setQuery("{\r\n" + 
				"  launchesPast(limit: 10, order: \"ASC\") {\r\n" + 
				"    id\r\n" + 
				"    mission_name\r\n" + 
				"    ships {\r\n" + 
				"      name\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}");
		
		QueryVariable variable = new QueryVariable();
		variable.setLimit(5);
		
		query.setQueryVariables(variable);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(query)
				.when().log().all()
					.post("/graphql")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.launchesPast[0].ships[0].name", equalTo("GO Ms Tree"));
	}
}
