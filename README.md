#Signalling Unit Log for (9M-MRO) Flight MH370

This set of notes and accompanying tables is intended to provide a readable summary of the data communication logs from the Inmarsat satellite system provided to the authorities in connection with the ongoing investigation and search and rescue activities relating to MH370.

* All times are in UTC
* The table below is a readable form of the logfile recorded at the ground earth station for the communications with the terminal on MH370 from 16:00 to the last Handshake Request (Log On Interrogation) attempt at 01:16 UTC
* Some columns which contain no material information have been removed from the tables below for readability

An example of a complete message for a single log entry:

> Field Headings:  
> Time, AES ID, DP AES Owner, Channel Name, Ocean Region, GES ID (octal), Channel Bearer, Channel Unit ID, Channel Type, Superframe Number, Frame Number, Slot Number, SU Number, SU Type, SU Type Code, SU Contents, Q Number, Reference Number, Ack Control, SDM Figure, CRC Correct, Missed T-Channel Burst, Broadcast, Rx Power (dBm), C/No, Frequency Offset (Hz), Estimated BER, Burst Timing Offset (microseconds)

> Example message:  
> 7/03/2014 16:42:04.408,35200217,SITADP (3047),IOR-R1200-0-36ED,IOR,305,Rx Primary,4,R-Channel RX,12184,,5,,0x62 -Acknowledge User Data (R-channel),62,1F D0 62 75 00 8F C5 7A 4E 00 00 00 00 00 00 00 00 7F 89 ,D,0,E,S31,Yes,No,No,-57.29,40.09,146,0,14920

**Understanding the Burst Timing Offset (BTO) values:**

* The round trip time for a message is a combination of:
	1. Time from the ground station → satellite → aircraft → satellite → ground station.
	2. Processing time within the ground station, satellite and aircraft terminal, which are constant
* The BTO is a value (in microseconds) relative to a terminal at a nominal fixed location. Only R-Channel messages are used.
* The BTO therefore allows the determination of the distance between the satellite and the aircraft. It does not provide the actual aircraft location

**Understanding the Burst Frequency Offset (BFO) values:**

* The transmission signal path from the aircraft has two components affected by Doppler shift; between the satellite and aircraft, and between the satellite and ground station
* The following need to be considered when computing the expected frequency offset:
	* Inmarsat Classic Aero mobile terminals are designed to correct for aircraft Doppler effects on their transmit signals. The terminal type used on MH370 assumes a stationary satellite at a fixed orbital position
	* An individual aircraft terminal will have a fixed frequency bias
	* Satellite, terminal and ground station oscillator stability
	* The correction applied by the Automatic Frequency Control (AFC) system in the ground station partially compensates for the satellite to ground station Doppler
