//Download spark on your machine.
wget www-eu.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz
cd spark-1.6.0-bin-hadoop2.6
cd ec2

//create security key on your machine and copy that in below commands.
export AWS_ACCESS_KEY_ID=AKIAIOTD7BCLNDQGVJSA
export AWS_SECRET_ACCESS_KEY=lIpFl5JyySzWzX7FA21H2K7QIy0hbOkFni1c4Ls/

// To launch one spark slave and master instance execute below command.
./spark-ec2 -k sparkeast -i /home/sujay/Desktop/PA2/sparkeast.pem -s 1 -t c3.large --spot-price=0.03 launch spark

// To launch 16 spark slaves and one master instance execute below command.
./spark-ec2 -k sparkeast -i /home/sujay/Desktop/PA2/sparkeast.pem -s 16 -t c3.large --spot-price=0.03 launch spark

// Login to spark master instance.
./spark-ec2 -k sparkeast -i /home/sujay/Desktop/PA2/sparkeast.pem login spark

//mount 400GB volume to master.

lsblk
sudo file -s /dev/xvds
sudo mkfs -t ext4 /dev/xvds
sudo mkdir /sujay
sudo mount /dev/xvds /sujay
sudo chmod 777 /sujay/
cd /sujay
//Download gensort and create 100GB input file.

wget www.ordinal.com/try.cgi/gensort-linux-1.5.tar.gz
tar -xzvf gensort-linux-1.5.tar.gz
cd 64
./gensort -a 1000000000 100GB_input_file

//Create directory in hdfs
cd 
cd ephemeral-hdfs
bin/hadoop fs -mkdir /sujay
// copy 100 Gb input file to hdfs.
bin/hadoop fs -Ddfs.replication=1 -put /sujay/64/100GB_input_file /sujay
// Check the content of hdfs.
bin/hadoop dfs -ls /sujay/
// Create output directory in hdfs.


// Create scala script to sort file.
cd spark
cd bin
vi sujay_spark_sort.scala

//Copy below below script to sujay_spark_sort.scala file

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

bin/hadoop fs -chmod 777 /tmp/hive
./spark-shell -i sujay_spark_sort.scala

//Copy file from hdfs to other location.
bin/hadoop dfs -getmerge /sujay/spark_sorted_100GB_output /sujay/100GB_final_spark_sorted_file
