package com.youtube.rest.inventory;


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
import org.codehaus.jettison.json.JSONObject;

import com.youtube.dao.Oracle308tube;
import com.youtube.dao.Schema308tube;
import com.youtube.util.ToJSON;

@Path("/v3/inventory")
public class V3Inventory {
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts2(String incomingData) throws Exception {
	
		
		String returnString = null;
		JSONArray json = new JSONArray();
		JSONObject jobj = new JSONObject();
		Schema308tube dao = new Schema308tube();
		
		try {
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println("jsonData: " + partsData.toString());
			
			
			int http_code = dao.insertIntoPC_PARTS(
													partsData.optString("PC_PARTS_PK"), 
													partsData.optString("PC_PARTS_TITLE"), 
													partsData.optString("PC_PARTS_CODE"), 
													partsData.optString("PC_PARTS_MAKER"), 
													partsData.optString("PC_PARTS_AVAIL"), 
													partsData.optString("PC_PARTS_DESC"));
			
			if (http_code==200){
				jobj.put("HTTP_CODE", "200");
				jobj.put("MSG", "Item has been entered successfully, version 2");
				returnString = json.put(jobj).toString();
			} else { Response.status(500).entity("unable to access the item").build(); }
			
			System.out.println("returnString: " + returnString);
		}
		catch(Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}

}
