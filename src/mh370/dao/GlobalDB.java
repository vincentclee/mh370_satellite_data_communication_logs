/*
 * Copyright (c) 2014, vincentclee <ssltunnelnet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package mh370.dao;

/**
 * Data Access Object
 * @author vincentlee
 * @since May 28, 2014
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class GlobalDB {
	//Signalling Unit
	public PreparedStatement insert_signalling_unit;
	
	//Channel Name
	public PreparedStatement insert_channel_name;
	public PreparedStatement select_channel_name_where;
	
	//Ocean Region
	public PreparedStatement insert_ocean_region;
	public PreparedStatement select_ocean_region_where;
	
	//Channel Type
	public PreparedStatement insert_channel_type;
	public PreparedStatement select_channel_type_where;
	
	//SU Type
	public PreparedStatement insert_su_type;
	public PreparedStatement select_su_type_where;
	
	//Stage
	public PreparedStatement insert_stage;
	public PreparedStatement select_stage_where;
	
	//Tools
	public PreparedStatement select_last_insert_id;
	
	private Connection conn;
	private UUID uuid;
	
	public GlobalDB() {}
	
	public void openDBconnection() {
		try {
			//Connect to database
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mh370", "root", "");
			uuid = UUID.randomUUID();
			System.out.println("<MySQL " + uuid.toString().substring(0, 8) + " Connected>");
			
			//Prepared statements used to query database
			
			//Signalling Unit
			insert_signalling_unit = conn.prepareStatement("INSERT INTO signalling_unit(time,channel_name_id,ocean_region_id,ges_id,channel_unit_id,channel_type_id,su_type_id,burst_frequency_offset,burst_timing_offset,stage_id) VALUES(?,?,?,?,?,?,?,?,?,?)");
			
			//Channel Name
			insert_channel_name = conn.prepareStatement("INSERT INTO channel_name(name) VALUES(?)");
			select_channel_name_where = conn.prepareStatement("SELECT id FROM channel_name WHERE name=?");
			
			//Ocean Region
			insert_ocean_region = conn.prepareStatement("INSERT INTO ocean_region(name) VALUES(?)");
			select_ocean_region_where = conn.prepareStatement("SELECT id FROM ocean_region WHERE name=?");
			
			//Channel Type
			insert_channel_type = conn.prepareStatement("INSERT INTO channel_type(name) VALUES(?)");
			select_channel_type_where = conn.prepareStatement("SELECT id FROM channel_type WHERE name=?");
			
			//SU Type
			insert_su_type = conn.prepareStatement("INSERT INTO su_type(name) VALUES(?)");
			select_su_type_where = conn.prepareStatement("SELECT id FROM su_type WHERE name=?");
			
			//Stage
			insert_stage = conn.prepareStatement("INSERT INTO stage(name) VALUES(?)");
			select_stage_where = conn.prepareStatement("SELECT id FROM stage WHERE name=?");
			
			//Tools
			select_last_insert_id = conn.prepareStatement("select LAST_INSERT_ID()");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void closeDBconnection() {
		try {
			conn.close();
			System.out.println("<MySQL " + uuid.toString().substring(0, 8) + " Disconnected>");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
