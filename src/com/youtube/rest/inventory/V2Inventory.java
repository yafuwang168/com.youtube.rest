package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
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
	
	/* eliminate the QueryParam format
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {
		return Response.status(400).entity("Error: please specify brand for this search").build();
	}
	*/
	
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand) throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();		
		
		try {
	
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

	@Path("/{brand}/{item_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(@PathParam("brand") String brand, @PathParam("item_number") int item_number) throws Exception {
	
		
		String returnString = null;
		JSONArray json = new JSONArray();		
		
		try {
	
			Schema308tube sch = new Schema308tube();
			
			json = sch.queryReturnBrandItemNumber(brand, item_number);
			returnString = json.toString();  // convert JSONArray to a JSON string
		}
		catch(Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
	
		
		String returnString = null;
		//JSONArray json = new JSONArray();
		Schema308tube dao = new Schema308tube();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			ObjectMapper mapper = new ObjectMapper();
			
			ItemEntry item = mapper.readValue(incomingData, ItemEntry.class);
			int http_code = dao.insertIntoPC_PARTS(item.PC_PARTS_PK, item.PC_PARTS_TITLE, item.PC_PARTS_CODE, 
					item.PC_PARTS_MAKER, item.PC_PARTS_AVAIL, item.PC_PARTS_DESC);
			
			if (http_code==200){
				returnString = "Item is inserted!";
			} else { Response.status(500).entity("unable to access the item").build(); }
		}
		catch(Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}


}


class ItemEntry {
	public String PC_PARTS_PK;
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER; 
	public String PC_PARTS_AVAIL; 
	public String PC_PARTS_DESC; 
}
