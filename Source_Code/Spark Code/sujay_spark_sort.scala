/** Start timer */
val timer_start = System.currentTimeMillis()
/** Give input file name  */
val file_input=sc.textFile("hdfs:////sujay/100GB_input_file")
/** seperate key and value pair  */
val file_to_sort=file_input.map(line => (line.take(10), line.drop(10)))
/** Sort the input by key  */
val sort = file_to_sort.sortByKey()
/** Map key and value  */
val lines=sort.map {case (key,value) => s"$key $value"}
/** Set the output file location  */
lines.saveAsTextFile("/sujay/spark_sorted_100GB_output")
/** Get the end time  */
val timer_end = System.currentTimeMillis()
/** print total time of execution */
println ("Time taken to sort file :-" + (timer_end - timer_start) + "In Milliseconds")
