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

package mh370.message;

/**
 * Message Tupple
 * @author vincentlee
 * @since May 27, 2014
 * @version 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private Date time;
	private String channel_name, ocean_region, channel_type, su_type;
	private int ges_id, channel_unit_id, burst_frequency_offset, burst_timing_offset;
	
	/**
	 * Constructor
	 * @param time
	 * @param channel_name
	 * @param ocean_region
	 * @param ges_id
	 * @param channel_unit_id
	 * @param channel_type
	 * @param su_type
	 * @param burst_frequency_offset
	 * @param burst_timing_offset
	 */
	public Message(Date time, String channel_name, String ocean_region,
			int ges_id, int channel_unit_id, String channel_type,
			String su_type, int burst_frequency_offset, int burst_timing_offset) {
		this.time = time;
		this.channel_name = channel_name;
		this.ocean_region = ocean_region;
		this.ges_id = ges_id;
		this.channel_unit_id = channel_unit_id;
		this.channel_type = channel_type;
		this.su_type = su_type;
		this.burst_frequency_offset = burst_frequency_offset;
		this.burst_timing_offset = burst_timing_offset;
	}
	
	/** @return the time */
	public Date getTime() {return time;}
	
	/** @return the channel_name */
	public String getChannel_name() {return channel_name;}
	
	/** @return the ocean_region */
	public String getOcean_region() {return ocean_region;}
	
	/** @return the channel_type */
	public String getChannel_type() {return channel_type;}
	
	/** @return the su_type */
	public String getSu_type() {return su_type;}
	
	/** @return the ges_id */
	public int getGes_id() {return ges_id;}
	
	/** @return the channel_unit_id */
	public int getChannel_unit_id() {return channel_unit_id;}
	
	/** @return the burst_frequency_offset */
	public int getBurst_frequency_offset() {return burst_frequency_offset;}
	
	/** @return the burst_timing_offset */
	public int getBurst_timing_offset() {return burst_timing_offset;}
	
	public String toString() {
		return "Message [time=" + time + ", channel_name=" + channel_name
				+ ", ocean_region=" + ocean_region + ", ges_id=" + ges_id
				+ ", channel_unit_id=" + channel_unit_id + ", channel_type="
				+ channel_type + ", su_type=" + su_type
				+ ", burst_frequency_offset=" + burst_frequency_offset
				+ ", burst_timing_offset=" + burst_timing_offset + "]";
	}
	
	public String toHeader() {
		return "time,channel_name,ocean_region,ges_id,channel_unit_id,"
				+ "channel_type,su_type,burst_frequency_offset,burst_timing_offset";
	}
	
	public String toCSV() {
		SimpleDateFormat sdf=new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");
		
		return "" + sdf.format(time) + "," + channel_name
				+ "," + ocean_region + "," + ges_id
				+ "," + channel_unit_id + ","
				+ channel_type + "," + su_type
				+ "," + ((burst_frequency_offset == 0) ? null : burst_frequency_offset)
				+ "," + ((burst_timing_offset == 0) ? null : burst_timing_offset);
	}
}
