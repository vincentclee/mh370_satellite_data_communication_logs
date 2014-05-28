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

package mh370.parser;

/**
 * MH370 Data Communication Logs Parser
 * @author vincentlee
 * @since May 27, 2014
 * @version 1.0
 */

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import mh370.dao.GlobalDB;
import mh370.message.Message;

public class DataParser {
	private GlobalDB global;
	private List<Message> messages;
	private String stage;
	
	public DataParser(String filename, String stage) {
		this.global = new GlobalDB();
		this.stage = stage;
		
		PDFParser pdf = new PDFParser();
		pdf.openFile(filename);
		this.messages = pdf.parsePDF();
	}
	
	public int lastInsertID() {
		try {
			int last_insert_id = 0;
			ResultSet rs = global.select_last_insert_id.executeQuery();
			if (rs.next()) last_insert_id = rs.getInt(1);
			
			return last_insert_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void tableToSQL() {
		try {
			global.openDBconnection();
			
			for (Message instance : messages) {
				//Channel Name
				int channel_name_id = 0;
				global.select_channel_name_where.setString(1, instance.getChannel_name());
				ResultSet rs = global.select_channel_name_where.executeQuery();
				if (rs.next()) channel_name_id = rs.getInt(1);
				
				if (channel_name_id == 0) {
					global.insert_channel_name.setString(1, instance.getChannel_name());
					global.insert_channel_name.execute();
					channel_name_id = lastInsertID();
				}
				
				//Ocean Region
				int ocean_region_id = 0;
				global.select_ocean_region_where.setString(1, instance.getOcean_region());
				rs = global.select_ocean_region_where.executeQuery();
				if (rs.next()) ocean_region_id = rs.getInt(1);
				
				if (ocean_region_id == 0) {
					global.insert_ocean_region.setString(1, instance.getOcean_region());
					global.insert_ocean_region.execute();
					ocean_region_id = lastInsertID();
				}
				
				//Channel Type
				int channel_type_id = 0;
				global.select_channel_type_where.setString(1, instance.getChannel_type());
				rs = global.select_channel_type_where.executeQuery();
				if (rs.next()) channel_type_id = rs.getInt(1);
				
				if (channel_type_id == 0) {
					global.insert_channel_type.setString(1, instance.getChannel_type());
					global.insert_channel_type.execute();
					channel_type_id = lastInsertID();
				}
				
				//SU Type
				int su_type = 0;
				global.select_su_type_where.setString(1, instance.getSu_type());
				rs = global.select_su_type_where.executeQuery();
				if (rs.next()) su_type = rs.getInt(1);
				
				if (su_type == 0) {
					global.insert_su_type.setString(1, instance.getSu_type());
					global.insert_su_type.execute();
					su_type = lastInsertID();
				}
				
				//Stage
				int stage_id = 0;
				global.select_stage_where.setString(1, stage);
				rs = global.select_stage_where.executeQuery();
				if (rs.next()) stage_id = rs.getInt(1);
				
				if (stage_id == 0) {
					global.insert_stage.setString(1, stage);
					global.insert_stage.execute();
					stage_id = lastInsertID();
				}
				
				global.insert_signalling_unit.setTimestamp(1, (instance.getTime() == null) ? null : new Timestamp(instance.getTime().getTime()));
				global.insert_signalling_unit.setInt(2, channel_name_id);
				global.insert_signalling_unit.setInt(3, ocean_region_id);
				global.insert_signalling_unit.setInt(4, instance.getGes_id());
				global.insert_signalling_unit.setInt(5, instance.getChannel_unit_id());
				global.insert_signalling_unit.setInt(6, channel_type_id);
				global.insert_signalling_unit.setInt(7, su_type);
				
				if (instance.getBurst_frequency_offset() == 0)
					global.insert_signalling_unit.setNull(8, java.sql.Types.INTEGER);
				else
					global.insert_signalling_unit.setInt(8, instance.getBurst_frequency_offset());
				
				if (instance.getBurst_timing_offset() == 0)
					global.insert_signalling_unit.setNull(9, java.sql.Types.INTEGER);
				else
					global.insert_signalling_unit.setInt(9, instance.getBurst_timing_offset());
				
				global.insert_signalling_unit.setInt(10, stage_id);
				global.insert_signalling_unit.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			global.closeDBconnection();
		}
	}
	
	public static void main(String[] args) {
		DataParser parser = new DataParser("pdf/processed/mh370_data_communication_logs_appendix_2.pdf", "Appendix 2");
		parser.tableToSQL();
	}
}
