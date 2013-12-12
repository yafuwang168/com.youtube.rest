package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;


@Path("/v2/inventory")
public class V2Inventory {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();

		PreparedStatement query = null;
		Connection conn=null;
		Response rb = null;
		
		
		try {
			conn=Oracle308tube.Oracle308tubeConn().getConnection(); // create  connection
			query = conn.prepareStatement("select * from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON toJson = new ToJSON();
				
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
