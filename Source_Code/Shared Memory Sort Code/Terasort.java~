import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Terasort							
{
	int chunk_size;
	String file_name;						/// Variable declaration.
	String Thread_Name;
	static int x=0;
	long chunks_size=200000;				// Chunk size to read 
	static long file_size=0;	
	long chunks_to_read;
	static int bytes_in_line=100;
	static int last_index=98;
	String rline;	
	
	public void divide_file(long file_size,String file_name)  	// This procedure will divide file in smaller chuncks for sorting
	{
		try
		{
			FileReader fr=new FileReader(file_name);							// Open file to sort.
			BufferedReader br=new BufferedReader(fr);			
			BufferedWriter bwriter=null;
			File f;
			String file_names;
			long no_of_lines_in_file=0;
			no_of_lines_in_file=(file_size/bytes_in_line);								// Count number of lines in file.
		System.out.println("no_of_lines_in_file are "+no_of_lines_in_file);			
chunks_to_read=(no_of_lines_in_file/chunks_size);					// count no if chunks to read.
			System.out.println("chunks_to_read are "+chunks_to_read);
			for(int j=1;j<=chunks_to_read;j++)									// create files with smaller chunks.
			{
				file_names="file_"+j+".txt";									
				f=new File(file_names);
				bwriter = new BufferedWriter( new FileWriter(file_names));
				for(int i=0;i<chunks_size;i++)									// Read chunk and write it to file.
				{
					rline = br.readLine();						
					bwriter.write(rline);						
					bwriter.newLine();						
				}
				bwriter.close();
			}	
		}
		catch(Exception e)														// Exception handling.
		{
			System.out.println(e);
		}
	}
	public long get_file_size(String file_name)									// This procedure will return size of file.
	{
		File file = new File(file_name);
		if (!file.exists() || !file.isFile()) {
        System.out.println("File does not exist");					// Print message if file does not exist.
        return -1;
      }
      return file.length();											// Get the file length.
	}	
	public void terasort_main(String f_name,int thread_number)						// This method will control all tera sort operations.
	{
		String file_name;				
		try
		{									
			file_name=f_name;					
			Terasort ts=new Terasort();								// create Terasort object
			file_size=ts.get_file_size(file_name);
			System.out.println("File_size_is:- "+file_size);
			long startTime = System.currentTimeMillis();						
			ts.divide_file(file_size,file_name);					// Call divide_file method.
			
			File_Sort fs=new File_Sort();
			fs.sort_files(ts.chunks_to_read,ts.chunks_size,f_name,thread_number);	// call	sort_files method of File_Sort class.											
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			
			String rline;			
			FileReader final_sorting =new FileReader("sorted_"+file_name);
			BufferedReader br=new BufferedReader(final_sorting);
			File new_final_sorting=new File("final_sorted_"+file_name);
			FileWriter fw=new FileWriter("final_sorted_"+file_name);
			BufferedWriter bw=new BufferedWriter(fw);	
			System.out.println("Time required to sort "+file_name+" (Milliseconds):- "+totalTime);		
			while(!((rline=br.readLine())==null))
			{
				bw.write(rline+'\r');
				bw.newLine();
			}
			br.close();
			bw.close();		
			File del_sorted_file =new File("sorted_"+file_name);
			del_sorted_file.delete();
			
		}	
		catch(Exception e)											// Exception handling.
		{
			System.out.println(e);
		}				
	}
}
