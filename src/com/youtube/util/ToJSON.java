package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;

import org.owasp.esapi.ESAPI;

public class ToJSON {

	/**
	 * This will convert...
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {
		
		JSONArray json = new JSONArray();
		String temp = null;
	
		
		try {
			java.sql.ResultSetMetaData rsmd= rs.getMetaData();
			
			while (rs.next()) {
				int numColumns = rsmd.getColumnCount();
				
				JSONObject obj = new JSONObject();
				
				for (int i=1; i<numColumns+1; i++) {
					
					String colName = rsmd.getColumnName(i) ;
					
					if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
						obj.put(colName,  rs.getArray(colName));
						/*Debug*/System.out.println("ToJSON: ARRAY");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
						obj.put(colName,  rs.getInt(colName));
						/*Debug*/System.out.println("ToJSON: BIGINT");						
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
						obj.put(colName,  rs.getBoolean(colName));
						/*Debug*/System.out.println("ToJSON: BOOLEAN");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
						obj.put(colName,  rs.getBlob(colName));
						/*Debug*/System.out.println("ToJSON: BLOB");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
						obj.put(colName,  rs.getDouble(colName));
						/*Debug*/System.out.println("ToJSON: DOUBLE");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
						obj.put(colName,  rs.getFloat(colName));
						/*Debug*/System.out.println("ToJSON: FLOAT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
						obj.put(colName,  rs.getInt(colName));
						/*Debug*/System.out.println("ToJSON: INTEGER");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
						obj.put(colName,  rs.getString(colName));
						/*Debug*/System.out.println("ToJSON: NVARCHAR");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
						temp = rs.getString(colName);
						temp = ESAPI.encoder().canonicalize(temp);
						temp = ESAPI.encoder().encodeForHTML(temp);
						obj.put(colName,  temp);
						//obj.put(colName,  rs.getString(colName));
						/*Debug*///System.out.println("ToJSON: VARCHAR");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
						obj.put(colName,  rs.getInt(colName));
						/*Debug*/System.out.println("ToJSON: TINYINT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
						obj.put(colName,  rs.getInt(colName));
						/*Debug*/System.out.println("ToJSON: SMALLINT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
						obj.put(colName,  rs.getDate(colName));
						/*Debug*/System.out.println("ToJSON: DATE");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
						obj.put(colName,  rs.getTimestamp(colName));
						/*Debug*/System.out.println("ToJSON: TIMESTAMP");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
						//System.out.println(colName);
						obj.put(colName,  rs.getBigDecimal(colName));
						/*Debug*/System.out.println("ToJSON: NUMERIC");
					}
					else {
						/*Debug*/System.out.println("ToJSON: Unsupport Type");						
					}

				}// end of for statement
				json.put(obj);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;		
	}
}
