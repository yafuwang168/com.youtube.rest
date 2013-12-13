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
import com.youtube.dao.Schema308tube;
import com.youtube.util.ToJSON;


@Path("/v2/inventory")
public class V2Inventory {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();		
		
		try {
			
			if(brand==null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}
			
			Schema308tube sch = new Schema308tube();
			json = sch.queryReturnBrandParts(brand);
			returnString = json.toString();  // convert JSONArray to a JSON string
		}
		catch(Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}


}
