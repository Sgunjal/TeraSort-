import java.util.ArrayList;
import java.util.Scanner;
 
public class MergeSort 
{
 
	   public ArrayList<String> Key;
	ArrayList<String> mergeSort(ArrayList<String> whole)
    	{
       	 ArrayList<String> left_side = new ArrayList<String>();
		int array_list_center;
       	 ArrayList<String> right_side = new ArrayList<String>();
       		  
        	if(whole.size()==1)    
            		return whole;
        else
        {
            array_list_center = whole.size()/2;
							
            for(int j=0; j<array_list_center; j++)		// Copy left half of arraylist.
            {
                    left_side.add(whole.get(j));
            }
            
            for(int i=array_list_center; i<whole.size(); i++)  //copy right half of arraylist.
            {
                    right_side.add(whole.get(i));
            }
             
            left_side  = mergeSort(left_side);  		// sort left and right part.
            right_side = mergeSort(right_side); 
            final_merge(left_side,right_side,whole);  
        }
	        return whole;
    }
	MergeSort(ArrayList<String> user_input)
	{
	    Key = new ArrayList<String>();
	        
		for(int k=0; k<user_input.size(); k++)
	        {
                Key.add(user_input.get(k));
	        }
	    }    		 
    
		 public void sort_array_list()
    		{
     		   Key=mergeSort(Key);
    		}
	 void final_merge(ArrayList<String> left_side, ArrayList<String> right_side, ArrayList<String> whole) 
	{
 	
        	int l_position=0 ,r_position=0,overall_position = 0;
       
        while (l_position < left_side.size() && r_position < right_side.size())
        {
           if ((left_side.get(l_position).compareTo(right_side.get(r_position) ) <0))
           {
           whole.set(overall_position,left_side.get(l_position));
           l_position++;
           }
           else
           {
           whole.set(overall_position, right_side.get(r_position));
           r_position++;
           }
           overall_position++;
        }
 	int remaining_position;
        ArrayList<String>remaining;
        
        if (l_position >= left_side.size()) 
	{            
         remaining = right_side;
         remaining_position = r_position;
        }
        else 
	{            
        remaining = left_side;
        remaining_position = l_position;
        }
        int x=remaining_position;       
        while (x<remaining.size()) 
	{
            whole.set(overall_position, remaining.get(x));
            overall_position++;
	    x++;
        }
    }
    public ArrayList<String> create()
    {       
return Key;     
    } 
}
