package com.youtube.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;
import javax.ws.rs.core.Response;

import com.youtube.util.ToJSON;

public class Oracle308tube {
	private static DataSource Oracle308tube = null;
	private static Context context = null;
	
	public static DataSource Oracle308tubeConn() throws Exception {
		
		if (Oracle308tube != null) {
			return Oracle308tube;
		}
		
		try {
			if (context == null) {
				context=new InitialContext();
			}
			Oracle308tube = (DataSource)context.lookup("yafuRestOracle");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return Oracle308tube; 
	}
	
	protected static Connection oraclePcPartsConnection()  {
		Connection conn=null;
		try {
			conn=Oracle308tubeConn().getConnection(); // create  connection
			return conn;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
