package com.youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

public class Schema308tube extends Oracle308tube {

	public int insertIntoPC_PARTS (	String PC_PARTS_PK, 
									String PC_PARTS_TITLE, 
									String PC_PARTS_CODE, 
									String PC_PARTS_MAKER, 
									String PC_PARTS_AVAIL, 
									String PC_PARTS_DESC  ) throws Exception {
		
		PreparedStatement query = null;
		Connection conn=null;
	
		try {
			/* if this was a real application, you should do a validation on the data */
			conn=oraclePcPartsConnection(); // create  connection
			query = conn.prepareStatement("Insert into YAFU.PC_PARTS (PC_PARTS_PK, PC_PARTS_TITLE,PC_PARTS_CODE,PC_PARTS_MAKER,PC_PARTS_AVAIL,PC_PARTS_DESC) values(?, ?, ?, ?, ?, ?)");
			
			query.setInt(1, Integer.parseInt(PC_PARTS_PK));
			query.setString(2, PC_PARTS_TITLE);
			query.setString(3, PC_PARTS_CODE);
			query.setString(4, PC_PARTS_MAKER);

			query.setInt(5, Integer.parseInt(PC_PARTS_AVAIL));
			query.setString(6, PC_PARTS_DESC);

		    query.executeUpdate();
		    
		}
		catch(Exception e) {
			e.printStackTrace();
			return 500;  // Error
		}
		finally { if (conn!=null) conn.close(); }
		return 200;  // Success
	}
	
	public JSONArray queryReturnBrandParts(String brand) throws Exception {
		
		PreparedStatement query = null;
		Connection conn=null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn=oraclePcPartsConnection(); // create  connection
			query = conn.prepareStatement("select PC_PARTS_PK,PC_PARTS_TITLE,PC_PARTS_CODE,PC_PARTS_MAKER,PC_PARTS_AVAIL,PC_PARTS_DESC from PC_PARTS where UPPER(PC_PARTS_MAKER) = ?");
			
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();
				
			json = converter.toJSONArray(rs);
			
			query.close();  // close connection
					
			}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally { if (conn!=null) conn.close(); }
		return json; 
	}

	public JSONArray queryReturnBrandItemNumber(String brand, int itemNumber) throws Exception {
		
		PreparedStatement query = null;
		Connection conn=null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn=oraclePcPartsConnection(); // create  connection
			query = conn.prepareStatement("select PC_PARTS_PK,PC_PARTS_TITLE,PC_PARTS_CODE,PC_PARTS_MAKER,PC_PARTS_AVAIL,PC_PARTS_DESC from PC_PARTS where UPPER(PC_PARTS_MAKER) = ?" +
											"and PC_PARTS_CODE = ?");
			
			query.setString(1, brand.toUpperCase());
			query.setInt(2, itemNumber);
			ResultSet rs = query.executeQuery();
				
			json = converter.toJSONArray(rs);
			
			query.close();  // close connection
					
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally { if (conn!=null) conn.close(); }
		return json; 
	}

}
