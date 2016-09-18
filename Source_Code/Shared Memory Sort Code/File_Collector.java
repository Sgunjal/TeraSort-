import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class File_Collector
{		
	public static void main(String args[])			// This method will control all sorting operations.
	{
		try
		{
		BufferedReader sc_input=new BufferedReader(new InputStreamReader(System.in)); 		
		System.out.println("Enter the name of file to sort:- ");		 			// read name of file to divide.
		String file_name_to_sort=sc_input.readLine();											//Close socket.		
		System.out.println("Enter the no of threads:- ");		 			// read name of file to divide.
		int thread_number=Integer.parseInt(sc_input.readLine());		
		Terasort ts=new Terasort();				
				
		ts.terasort_main(file_name_to_sort,thread_number);	 // Call terasort_main method of Terasort class.						
		}
		catch(Exception e)						// Exception handling.
		{
			e.printStackTrace();
		}
	}
}
