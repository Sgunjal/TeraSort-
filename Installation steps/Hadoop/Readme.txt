// Execute below steps to Mount EBS Volume
lsblk
sudo file -s /dev/xvdb
sudo mkfs -t ext4 /dev/xvdb
sudo mkdir /sujay
sudo mount /dev/xvdb /sujay
sudo chmod 777 /sujay/

// Execute below steps to install java.
sudo apt-get update
sudo apt-add-repository ppa:webupd8team/java -y
sudo apt-get update
sudo apt-get install oracle-java8-installer -y

// Execute below steps for Ant installation
sudo apt-get install ant
sudo apt-get update

// Execute below steps for GCC installation
sudo apt-get install gcc -y
sudo apt-get update -y

//Open bashrc file and add the below details to the file. 
vi .bashrc 

export CONF=/home/ubuntu/hadoop-2.7.2/etc/hadoop
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export PATH=$PATH:$/home/ubuntu/hadoop-2.7.2/bin

//Copy code to TeraSort_Hadoop.java and save under hadoop-2.7.2 directory.
vi TeraSort_Hadoop.java

/// setup below veriables to compile TeraSort_Hadoop.java file.
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export PATH=${JAVA_HOME}/bin:${PATH}
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

// Copy and install Hadoop
wget http://www-us.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
tar -xzvf hadoop-2.7.2.tar.gz
chmod 777 hadoop-2.7.2

//  Install ssh
sudo apt-get install ssh -y

/// Compile sort progogram
bin/hadoop com.sun.tools.javac.Main TeraSort_Hadoop.java
jar cf TeraSort_Hadoop.jar *.class

// change core-site.xml, hadoop-env.sh, hdfs-site.xml, yarn-site.xml and mapred-site.xml as per below edit DNS of master in below files.
cd /home/ubuntu/hadoop-2.7.2/etc/hadoop
vi core-site.xml

<property>
<name>fs.default.name</name>
<value>hdfs://ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9000</value>
</property>
<property>
<name>hadoop.tmp.dir</name>
<value>/sujay</value>
<description>base location for other hdfs directories.</description>
</property>

vi hadoop-env.sh
set JAVA_HOME=/usr/lib/jvm/java-8-oracle

vi hdfs-site.xml

<property>
<name>dfs.replication</name>
<value>1</value>
</property>
<property>
<name>dfs.permissions</name>
<value>false</value>
</property>

vi yarn-site.xml

<property>
<name>yarn.nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property>
<property>
<name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
<value>org.apache.hadoop.mapred.ShuffleHandler</value></property>
<property>
<name>yarn.resourcemanager.resource-tracker.address</name>
<value>ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9025</value>
</property>
<property>
<name>yarn.resourcemanager.scheduler.address</name>
<value>ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9030</value>
</property>
<property>
<name>yarn.resourcemanager.address</name>
<value>ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9050</value>
</property>
<property>
<name>yarn.resourcemanager.webapp.address</name>
<value>ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9006</value>
</property>
<property>
<name>yarn.resourcemanager.admin.address</name>
<value>ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9008</value>
</property><!---->
<property>
<name>yarn.nodemanager.vmem-pmem-ratio</name>
<value>2.1</value>
</property>

cp /home/ubuntu/hadoop-2.7.2/etc/hadoop/mapred-site.xml.template /home/ubuntu/hadoop-2.7.2/etc/hadoop/mapred-site.xml

vi mapred-site.xml

<property>
<name>mapreduce.job.tracker</name>
<value>hdfs://ec2-52-35-173-51.us-west-2.compute.amazonaws.com:9001</value>
</property>
<property>
<name>mapreduce.framework.name</name>
<value>yarn</value>
</property>

/// Change Slaves and hosts file as per below.

cd hadoop2.7.2/etc/hadoop
vi slaves

///slave node will have masters and slaves node dns in slaves file.
///master node will have all slaves and master node dns in slaves file.

///for example masters slaves file:-
ec2-52-35-173-51.us-west-2.compute.amazonaws.com
ec2-52-33-164-101.us-west-2.compute.amazonaws.com
ec2-52-36-17-183.us-west-2.compute.amazonaws.com
ec2-52-37-129-190.us-west-2.compute.amazonaws.com
ec2-52-36-247-207.us-west-2.compute.amazonaws.com

//slave1:-
slaves file:-
ec2-52-35-173-51.us-west-2.compute.amazonaws.com
ec2-52-33-164-101.us-west-2.compute.amazonaws.com

sudo vi /etc/hosts
//slave node will have masters and slaves node private ip and dns in hosts file.
//master node will have all slaves and master node private ip and dns in hosts file.

//for example masters hosts file:-
172.31.5.141 ec2-52-35-173-51.us-west-2.compute.amazonaws.com
172.31.2.162 ec2-52-33-164-101.us-west-2.compute.amazonaws.com
172.31.15.215 ec2-52-36-17-183.us-west-2.compute.amazonaws.com
172.31.11.249 ec2-52-37-129-190.us-west-2.compute.amazonaws.com
172.31.5.181 ec2-52-36-247-207.us-west-2.compute.amazonaws.com

//hosts file
172.31.5.141 ec2-52-35-173-51.us-west-2.compute.amazonaws.com
172.31.2.162 ec2-52-33-164-101.us-west-2.compute.amazonaws.com


// Execute below six steps on all nodes.
eval "$(ssh-agent)"
chmod 400 hadoop.pem
ssh-add hadoop.pem
ssh-keygen -t rsa

//provide the masters DNS on all the slaves machine while executing below command.
ssh-copy-id -i ~/.ssh/id_rsa.pub ubuntu@ec2-52-35-173-51.us-west-2.compute.amazonaws.com
chmod 0600 ~/.ssh/authorized_keys


// execute below commands to start hadoop.
cd hadoop-2.7.2/
bin/hadoop namenode -format
ssh localhost 
cd sbin
./start-dfs.sh        
./start-yarn.sh
       
////Execute jps command to check if all name nodes and data nodes are up and running.
jps

// Aftre jps command you should see below processess running on master.
4963 Jps
4510 ResourceManager
3714 DataNode
4139 NameNode
4655 NodeManager

// Create and setup hdfs directory
cd
cd hadoop-2.7.2
bin/hadoop fs -mkdir /hdfs_sujay

cd /sujay
mkdir data
cd data

/// Download gensort application and generate file of 100Gb and copy that file to hdfs
cd /data
wget www.ordinal.com/try.cgi/gensort-linux-1.5.tar.gz
tar -xzvf gensort-linux-1.5.tar.gz
cd 64
./gensort -a 1000000000 100GB_input_file
cd 
cd hadoop-2.7.2
bin/hadoop dfs -copyFromLocal /sujay/data/64/100GB_input_file /hdfs_sujay
bin/hadoop dfs -ls /hdfs_sujay/
bin/hadoop dfs -rm -r -f /hdfs_sujay/output

/// Execute hadoop program to sort 100GB file.
bin/hadoop jar TeraSort_Hadoop.jar TeraSort_Hadoop /hdfs_sujay/100GB_input_file /hdfs_sujay/output

///Command to copy the file from hadoop file system to our instance EC2.
bin/hadoop dfs -get /hdfs_sujay/output/part-r-00000 /sujay

// Execute below command to check if file is properly sorted or not
cd /sujay/data/64
./valsort /sujay/part-r-00000

