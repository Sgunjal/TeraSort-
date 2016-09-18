import java.io.IOException;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;

// Main Sort class which is responsible for handling all processing.
public class TeraSort_Hadoop
{
        //Declaration of TeraSort_Hadoop default constructor.
         public TeraSort_Hadoop()
         {
                // Make a call to super class constructor
                     super();
         }
        // Declaration of main method           
         public static void main(String[] args) throws Exception
         {
                long startTime_counter=0;
                long endTime_counter=0;
                //Start timer of operation.
            startTime_counter = System.currentTimeMillis();
                //create Configuration object.
            Configuration conf = new Configuration();
                // Create sort_job object 
            Job sort_job = new Job(conf, "Tera Sort");
            sort_job.setCombinerClass(My_Reducing_Class.class);
                // Set reducer class for job.
            sort_job.setReducerClass(My_Reducing_Class.class);
                // Set class for out_put_key
            sort_job.setOutputKeyClass(Text.class);
                //Set class for value 
            sort_job.setOutputValueClass(Text.class);
            sort_job.setJarByClass(TeraSort_Hadoop.class);
                // Set mapper class for job.
            sort_job.setMapperClass(My_Mapping_Class.class);
            //Set input file path
            FileInputFormat.addInputPath(sort_job, new Path(args[0]));
            //Set output file path
            FileOutputFormat.setOutputPath(sort_job, new Path(args[1]));

            System.exit(sort_job.waitForCompletion(true) ? 0 : 1);
            // Stop timer.
            endTime_counter = System.currentTimeMillis();
            System.out.println("Total Time taken for Execution :" + (endTime_counter-startTime_counter)/1000);
        }
}
class My_Reducing_Class extends Reducer<Text, Text, Text, Text>
{
                 public void reduce(Text in_key, Text in_value, Context context_writer) // Reduce the function
                 {
                   try
                   {
                           Text key_name = new Text();
                           Text value_name = new Text();
                           key_name.set(in_key.toString() + in_value.toString());
                           value_name.set("");
                           context_writer.write(key_name, value_name);
                   }
                  catch(Exception e)            // Exception handling code.
                  {
                        System.out.println(e);
                  }
                }
}
class My_Mapping_Class extends Mapper<Object, Text, Text, Text>
{
         public void map(Object in_key, Text in_value, Context context_writer)  // Map function to map key and value to sort.
         {
                String  r_line=null;        /// Variable declaration section
                String  key1_val=null;
                String  val=null;
                Text key_name = new Text();
                Text value_name = new Text();
                try
                {
                    r_line = in_value.toString();
                    key1_val = r_line.substring(0, 10);
                    val = r_line.substring(10);
                    key_name.set(key1_val);
                    value_name.set(val);
                    context_writer.write(key_name, value_name);
                 }
                catch(Exception e)                      // Exception handling code
                {
                        System.out.println(e);
                }
        }
}
                                                             
