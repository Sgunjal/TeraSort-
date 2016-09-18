import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;
public class File_Sort extends Thread
{
	String thread_name;
	static String final_file_name;
	static volatile long count_files=0;
	static long chunks_to_read=0,chunks_size=0;
	String file_name;
	static long total_passes=0;
	FileReader fr;
	BufferedReader br;
	BufferedReader br1,br2;
	static int bytes_in_line=99;
	static int last_index=98;
	static long bytes_in_file=0;
	static boolean file_reader_flag=true;
	String rline;
	Thread[] t=new Thread[10];
	ArrayList<String> arraylist=new ArrayList<String>();
	HashMap<String,String> hashmap=new HashMap<String,String>(); 
	File_Sort()
	{
	}
	File_Sort(String thread_name)
	{
		this.thread_name=thread_name;
	}
	synchronized public void copy_to_arraylist()
	{
		long var;
		try
		{
		//arraylist.clear();
		//hashmap.clear();
		synchronized(File_Sort.class)
		{
			var=++count_files;	
		}
		file_name="file_"+var+".txt";
		
		//System.out.println("file_name:- "+file_name+" count_files:- "+count_files);
		if(count_files<=chunks_to_read)
		{
			System.out.println("count_files:- "+var);
			fr=new FileReader(file_name);							
			br=new BufferedReader(fr);
			for(int i=0;i<chunks_size;i++)
			{
				rline = br.readLine();						
				arraylist.add(rline.substring(0,10));
				hashmap.put(rline.substring(0,10),rline.substring(10,last_index));
			}
			MergeSort test = new MergeSort(arraylist);
			test.sort_array_list();
			arraylist=test.create();
			create_sorted_files(arraylist,hashmap,chunks_size,"sort_"+var+".txt");
			File rm_file=new File(file_name);
			rm_file.delete();
			arraylist.clear();
			hashmap.clear();			
		}
		else
		{
			file_reader_flag=false;
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
	public synchronized void merge_sorted_files(long chunks_to_read,long chunks_size) throws Exception
	{	
		try
		{
			File old_sorted_file= new File("sorted_"+final_file_name);
			old_sorted_file.delete();
		FileReader fr1,fr2;	
		String new_rline;
		File f;
		//BufferedReader br1,br2;
		long f1_length=0,f2_length=0;
		int file_read=0;
		ArrayList<String> arraylist1;		
		ArrayList<String> file_name_array=new ArrayList<String>();
		HashMap<String,String> hashmap1; 		
		file_name_array.add("sort_"+0+".txt");
		for(int i=1;i<=chunks_to_read;i++)
		{
			file_name_array.add("sort_"+i+".txt");
		}
		int flag1=0,flag2=0;
		while(file_read<file_name_array.size())
		{				
			file_read++;	
			File f1=new File(file_name_array.get(file_read));			
			f1_length=f1.length();
			f1_length=(f1_length/bytes_in_line);			
			System.out.println("f1_length "+f1_length);		
			//f1.close();
			fr1=new FileReader(file_name_array.get(file_read));							// read Server_list.txt 
			br1=new BufferedReader(fr1);			
			file_read++;
			
			File f2=new File(file_name_array.get(file_read));
			f2_length=f2.length();	
			f2_length=(f2_length/bytes_in_line);	
			System.out.println("f2_length "+f2_length);		
			//f2.close();
			fr2=new FileReader(file_name_array.get(file_read));							// read Server_list.txt 
			br2=new BufferedReader(fr2);
			FileWriter fwn=new FileWriter("pass_"+file_read+".txt");
			BufferedWriter bwn=new BufferedWriter(fwn);
			long f1_records_read=0;
			long f2_records_read=0;
			String f1_line=null;
			String f2_line=null;
			String f1_line_key=null;
			String f2_line_key=null;
			f1_line=br1.readLine();
			f1_line_key=f1_line.substring(0,10);
			f2_line=br2.readLine();	
			f2_line_key=f2_line.substring(0,10);
			//System.out.println("Reached here");
			f1_records_read++;
			f2_records_read++;
			
			while(((f1_records_read<=f1_length) && (f2_records_read<=f2_length)))
			{	
				flag1=flag2=0;					
				if((f1_line_key.compareTo(f2_line_key))<=0)
				{
					bwn.write(f1_line);
					bwn.newLine();
					f1_records_read++;
					if(f1_records_read<=f1_length)					
					f1_line=br1.readLine();
					f1_line_key=f1_line.substring(0,10);	
					flag1=1;				
				}
				else
				{
					bwn.write(f2_line);
					bwn.newLine();
					f2_records_read++;
					if(f2_records_read<=f2_length)					
					f2_line=br2.readLine();
					f2_line_key=f2_line.substring(0,10);
					flag2=1;
				}									
			}
			//System.out.println("f1_records_read before :- "+f1_records_read);	
			//System.out.println("f2_records_read before :- "+f2_records_read);		
			//System.out.println("Flag1 "+flag1+"falg2 "+flag2);
			//System.out.println("End While");	
			if(flag1==0)
			{	
				bwn.write(f1_line);
				bwn.newLine();	
				f1_records_read++;								
			}
			while(f1_records_read<=f1_length)
			{				
				f1_line=br1.readLine();				
				bwn.write(f1_line);
				bwn.newLine();
				f1_records_read++;				
			}
			//bwn.write(br1.readLine());
			//bwn.newLine();
			//System.out.println("f1_records_read:- "+f1_records_read);	
			if(flag2==0)
			{			
				bwn.write(f2_line);
				bwn.newLine();	
				f2_records_read++;							
			}
			while(f2_records_read<=f2_length)
			{
				f2_line=br2.readLine();					
				bwn.write(f2_line);
				bwn.newLine();
				f2_records_read++;				
			}
			//bwn.write(br2.readLine());
			//bwn.newLine();
			//System.out.println("f2_records_read:- "+f2_records_read);	
			br1.close();
			br2.close();
			bwn.close();
			System.out.println("Array List Size is:- "+file_name_array.size());
			f=new File(file_name_array.get(file_read-1));
			f.delete();			
			f=new File(file_name_array.get(file_read));
			f.delete();			
			file_name_array.add("pass_"+file_read+".txt");
			file_name="pass_"+file_read+".txt";
		}
		file_name_array.clear();
		}
		catch(Exception e)
		{
			/*File x=new File("tp");
			String path=x.getAbsolutePath();
			System.out.println(path);
			path=path.replace("tp", "");	*/
				
			//System.out.println("Exception!!");
			File un_sorted_file=new File(final_file_name);			
			un_sorted_file.delete();			
			br1.close();
			br2.close();
			File oldName = new File(file_name);
			File newName = new File("sorted_"+final_file_name);
			if(oldName.renameTo(newName)) 
			{
				System.out.println("renamed");
			} 
			else 
			{
				System.out.println("Error");
			} 
			//e.printStackTrace();			
		}		
	}
	public synchronized void create_sorted_files(ArrayList<String> sorted_arraylist,HashMap<String,String> hashmap,long chunks_size,String sorted_file_name)
	{
		try
		{
		//System.out.println("ArrayList "+sorted_arraylist.size());
		//System.out.println("HashMap "+hashmap.size());
		String wline=null;
		String arraylist_value=null;
		BufferedWriter bwriter=null;
		//String new_file_name=file_name+"1";
		File f = new File(sorted_file_name);
		//f.delete();
		bwriter = new BufferedWriter( new FileWriter(sorted_file_name,true));		
		
		for(int i=0;i<chunks_size;i++)
		{
			arraylist_value=sorted_arraylist.get(i);
			wline=arraylist_value+hashmap.get(arraylist_value);
			bwriter.write(wline);
			bwriter.newLine();			
		}
		bwriter.close();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	synchronized public void run()											// This method is called by every thread.
	{
		try
		{			
			while(file_reader_flag==true)
			{				
				copy_to_arraylist();
			}			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void sort_files(long chunks_to_read,long chunks_size,String final_f_name,int thread_number)
	{
		long divident=0;
		try
		{
		
		final_file_name=final_f_name;
		divident=chunks_to_read;
		bytes_in_file=98*chunks_size;
		for(int i=1;i<=chunks_to_read;i++)
		{
			divident=(divident/2);
			if(divident>0)
			{
				total_passes++;
			}
			else
				break;
		}	
		System.out.println("Total Passes required to get final sorted file:- "+total_passes);
		this.chunks_to_read=chunks_to_read;
		this.chunks_size=chunks_size;
		
		for(int i=1;i<=thread_number;i++)					// create multiple threds to send file to slaves.
		{
			t[i]=new File_Sort("Thread"+i);
			t[i].start(); 
		}
		//System.out.println("Wait Started !!");
		for (int i=1;i<=thread_number;i++) 							// Wait till all files send to slaves.
		{
			t[i].join();															// wait till thread completes its execution.
		}
		System.out.println("Done");			
		merge_sorted_files(chunks_to_read,chunks_size);			
		System.out.println("Execution complete !!!");				
		/*File del_file;
		for(int i=1;i<=chunks_to_read;i++)
		{
			del_file=new File("file_"+i+".txt");
			del_file.delete();			
		}*/
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
