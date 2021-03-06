package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;


@Path("/v1/inventory")
public class V1Inventory {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		String returnString = null;
		Connection conn=null;
		Response rb = null;
		
		
		try {
			conn=Oracle308tube.Oracle308tubeConn().getConnection(); // create  connection
			query = conn.prepareStatement("select * from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON toJson = new ToJSON();
			JSONArray result = new JSONArray();
			
			result = toJson.toJSONArray(rs);
			
			query.close();  // close connection
					
			returnString = result.toString();  // convert JSONArray to a JSON string
			rb = Response.ok(returnString).build();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally { if (conn!=null) conn.close(); }
		
		return rb; 
	}


}
