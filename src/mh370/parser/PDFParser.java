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
 * PDF Parser
 * @author vincentlee
 * @since May 27, 2014
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import mh370.message.Message;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFParser {
	private static final boolean DEBUG = false;
	private PDDocument pdDocument;
	
	public PDFParser() {}
	
	public boolean openFile(String filename) {
		try {
			File file = new File(filename);
			pdDocument = PDDocument.load(file);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Message> parsePDF() {
		try {
			List<Message> messages = new ArrayList<Message>();
			
			//Open PDF
			PDFTextStripper textStripper = new PDFTextStripper();
			
			//Load entire PDF Text into string
			String text = textStripper.getText(pdDocument);
			
			//Split string into tupples
			String split[] = text.split(System.getProperty("line.separator"));
			
			//Loop untill all data lines are processed
			for (String str : split) {
				if (DEBUG) System.out.println(str);
				
				//Tokenize the Data
				List<String> tokens = new ArrayList<String>();
				Scanner scanner = new Scanner(str);
				while (scanner.hasNext()) tokens.add(scanner.next());
				scanner.close();
				if (DEBUG) System.out.println(tokens);
				
				
				
				///// Time /////
				//Parses this format => 7/03/201418:39:55.664
				SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyyHH:mm:ss.SSS");
				Date time = format.parse(tokens.remove(0) + tokens.remove(0));
				
				///// Channel Name /////
				String channel_name = tokens.remove(0);
				
				///// Ocean Region /////
				String ocean_region = tokens.remove(0);
				
				///// GES ID (octal) /////
				int ges_id = Integer.parseInt(tokens.remove(0));
				
				///// Channel Unit ID /////
				int channel_unit_id = Integer.parseInt(tokens.remove(0));
				
				///// Channel Type  /////
				String channel_type = tokens.remove(0) + " " + tokens.remove(0);
				
				///// Burst Frequency Offset (Hz) BFO /////
				///// Burst Timing Offset (microseconds) BTO /////
				int burst_frequency_offset = 0;
				int burst_timing_offset = 0;
				try {
					Integer.parseInt(tokens.get(tokens.size()-1));
					Integer.parseInt(tokens.get(tokens.size()-2));
					
					burst_timing_offset = Integer.parseInt(tokens.remove(tokens.size()-1));
					burst_frequency_offset = Integer.parseInt(tokens.remove(tokens.size()-1));
				} catch (Exception e) {}
				
				///// Burst Frequency Offset (Hz) BFO /////
				if (burst_frequency_offset == 0) {
					try {
						Integer.parseInt(tokens.get(tokens.size()-1));
						
						burst_frequency_offset = Integer.parseInt(tokens.remove(tokens.size()-1));
					} catch (Exception e) {}
				}
				
				///// SU Type /////
				String su_type = "";
				for (int i = 0; i < tokens.size(); i++) {
					su_type += tokens.get(i);
					if (i < tokens.size()-1)
						su_type += " ";
				}
				
				//Load into Message
				Message msg = new Message(time, channel_name, ocean_region, ges_id, channel_unit_id, 
						channel_type, su_type, burst_frequency_offset, burst_timing_offset);
				
				//Add to List
				messages.add(msg);
				if (DEBUG) System.out.println(msg);
			}
			
			return messages;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void toFile() {
		try {
			List<Message> messages = parsePDF();
			
			File log = new File("signalling_unit_log.csv");
			PrintWriter out = new PrintWriter(new FileWriter(log, true));
			
			out.append(messages.get(0).toHeader());
			for (Message instance : messages) {
				out.append("\n");
				out.append(instance.toCSV());
			}
			out.append("\n");
			out.close();
			
		} catch(Exception e) {
			System.err.println("error: signalling_unit_log.csv");
		}
	}
	
	public static void main(String[] args) {
		PDFParser parser = new PDFParser();
		parser.openFile("pdf/processed/mh370_data_communication_logs_appendix_2.pdf");
//		parser.parsePDF();
		parser.toFile();
	}
}
