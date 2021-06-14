import java.io.*;

public class Help

{

	public static String help_msg[] = {
			"Q | Terminates editing and updates the edit \fle with the editing changes.\n"
			
			,"X | Terminates editing and does NOT update the edit \fle. The changes are ignored.\n"
			
			, "T | Positions CLN to the top of the \fle (to line 1).\n"
			
			, "E | Positions CLN to the end/bottom of the \fle (to last line).\n"
			
			, "N | Moves CLN number of lines forward/down the \fle (from CLN).\n"
			
			, "B | Moves CLN number of lines backwards/up the \fle (from CLN).\n"
			
			, "W | Prints \\At Edit File Line x\" where x is the value of CLN.\n"
			
			, "C | Prints \\Total Edit File Lines: x\" where x is number of lines in the edit \fle.\n"
			
			, "L | Lists/Prints number of \fle lines starting at CLN (and leaves CLN at last line printed).\n"
			
			, "S | Shows/Prints number of \fle lines starting at CLN (and DOES NOT change CLN!).\n"
			
			, "D | Deletes the number of \fle lines starting at CLN and leaves CLN positioned to the line after the last line deleted.\n"
			
			, "A | Adds lines (typed/entered) to the edit \fle AFTER the CLN line number. Enter/type a null\n"
			+ "line (i.e. just hit return on a new line) by itself to terminate entry. CLN is positioned to the last line entered.\n"
					
			, "F | Finds and prints the line, or lines staring at CLN, that contain one or more occurrences of the\n"
			+ "string. The \frst and last characters of the string must be the same to delimit the string and are not\n"
			+ "considered during string matching. CLN is positioned to the last line a match was attempted.\n"
			
			, "R | Finds and replaces all occurrences of the old string with the new string starting at CLN. Any\n"
			+ "line that is changed is printed. The \frst and last characters of each string must be the same to delimit\n"
			+ "the string and are not considered during string matching or replacement. Note: If the old string was\n"
			+ "\"xBBx\", the new string was \"yBy\", the line being changed was \"BBBBBB\", then the updated line would contain \"BBB\".\n"
			
			, "Y | Yanks and COPIES the lines starting at CLN to the internal line buer. Y replaces the previous\n"
			+ "contents of the internal line buer with the newly yanked line(s). CLN is positioned to the last line yanked.\n"
			
			, "Z | Yanks and DELETES the lines starting at CLN to the internal line buer. Z replaces the previous\n"
			+ "contents of the internal line buer with the newly deleted line(s). CLN is positioned to the line after\n"
			+ "the last line deleted.\n"
			
			, "P | Puts the entire contents of the internal line buer after the CLN and leaves CLN positioned to\n"
			+ "the last line put. The internal line buer remains unaltered after the put. If the internal line buer is\n"
			+ "empty then P has no eect on the edit \fle.\n"
			
			, "I | Indexes the keywords at the top of the edit \fle (i.e. those lines appearing at the top of the \fle\n"
			+ "beginning with the character ''. An internal table is created with edit \fle line numbers of the edit \fle\n"
			+ "lines that contain each keyword. Warning: if other commands are used to change the \fle, then this\n"
			+ "command should be used again to re-index the \fle. This command does not aect CLN.\n"
			
			, "K | Keyword prints the line numbers from the keyword table created by the I(ndex) command for\n"
			+ "the speci\fed keyword. The \frst and last characters of the keyword must be the same to delimit the\n"
			+ "string and are not considered part of the keyword. If I has not yet been issued before K, then K will\n"
			+ "simply report that the keyword was not found. This command does not aect CLN.\n"
			
			, "O | Orders/Sorts the lines L-H lexicographically starting at the CLN and leaves CLN positioned to\n"
			+ "the last line sorted. Note that trailing blanks at the end of lines does aect sorting.\n"
			
			, "M | Sets column margins/window (default starting values are 1 and 80) for use with the F, R & O\n"
			+ "commands. That is, these commands will search/sort only within the de\fned margins/window. This\n"
			+ "command does not aect CLN.\n"
			
			, "H | By itself provides a short list of the editor comamnds and with an argument (which must be the\n"
			+ "letter of a valid command) provides a short description of the command.\n"
	};
  //***************************************************************************
  public static void General()
  {
	  for (int i = 0; i < help_msg.length; ++i)
		{
			Msg.wLMsg(help_msg[i]);
		}
  }

  //***************************************************************************
  public static void Command(char cmd)
  {
	  for (int i = 0; i < help_msg.length; i++)
		{
			if (help_msg[i].charAt(0) == cmd)
			{
				Msg.wLMsg(help_msg[i]);
				break;
			}
		}
  }

} // EndClass Help 
