# 1 MapReduce JAVA

In this tutorial, we will use write MapReduce jobs using JAVA. Before that, we
will install prerequirements, if you already have them, skip thoses parts.
<br/><br/>

## 1.1 Install OpenJDK 8
### 1.1.1 Microsoft Windows

Download and install OpenJDK for Windows from <a href="https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot" target_="blank">here</a>.
Start the installer and configure it to install everything
<br/><br/>

## 1.2 Git
### 1.2.1 Microsoft Windows
Download and install 64-bit Git for Windows Setup from <a href="https://git-scm.com/download/win" target_="blank">here</a>.
<br/><br/>

## 1.3 Visual Studio Code
for this tp I already have vsc along with maven so I won't be using intelliJ
<br/><br/>


## 1.4 Clone the project
<a href="https://github.com/makayel/hadoop-examples-mapreduce" target_="blank">Here</a> is a JAVA project hosted on github to start your lab.
<br/><br/>


## 1.5 Import the project
clone the repository using : 
    
    git clone https://github.com/makayel/hadoop-examples-mapreduce

Generate the JAR using maven lifecycle package using:

    mvn pakcage

On the bottom of the window, you will see the building process, wait for an
[INFO] BUILD SUCCESS message

You will see a new folder target, inside this folder you will find the JARs. We will use the JAR *-with-dependencies.jar.
<br/><br/>


## 1.6 Send the JAR to the edge node
### 1.6.1 Microsoft Windows
Download and install FileZilla client for Windows from <a href="https://download.filezilla-project.org/client/FileZilla_3.51.0_win64_sponsored-setup.exe" target_="blank">here</a>. Connect to the
edge node using thoses parameters:
- Host: sftp://hadoop-edge01.efrei.online
- Username: ssh username
- Password: ssh password
- Port: 22

You can slide files from left (your directories) to the right (edge node).


### 1.6.2 Run the job
Start the wordcount job using the JAR that we send to the edge node. Referer
to previous labs for the command.

    yarn jar /home/vserena/hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar wordcount /user/vserena/book.txt /user/vserena/test

<br/>


## 1.7 How will the lab work?
### 1.7.1 Important 1
This lab will last at least 2 weeks and there are all kinds of exercises, easy and hard. So start with all the easy exercises, you will do the hard ones next time.

If you start with the difficult ones and you have no experience, you are going to have problems. You do as you want, it’s up to you. 

For each of these projects, if you can’t do everything that is asked for, try
to get closer. The scoring takes into account your attempts.

### 1.7.2 Important 2
Don’t forget to write unit test if you want bonus points. <a href="http://tutorials.jenkov.com/java-unit-testing/index.html" target_="blank">here</a> some documentation about it.

### 1.7.3 Important 3
If you don’t have a <a href="https://github.com/" target_="blank">GitHub</a> account, you must create one. You must clone the
<a href="https://github.com/makayel/hadoop-examples-mapreduce" target_="blank">base repository</a> following this <a href="https://docs.github.com/en/free-pro-team@latest/github/creating-cloning-and-archiving-repositories/cloning-a-repository" target_="blank">tutorial</a> (keep your repository public).

At the end of each lab session, you must add on Moodle a file with your commit ID:

\<name1>-\<name2>-\<commit-id>.md
<br/><br/>

## 1.8 Remarkable trees of Paris
You are going to write some MapReduce jobs on the remarkable trees of Paris
using this <a href="https://github.com/makayel/hadoop-examples-mapreduce/blob/main/src/test/resources/data/trees.csv" target_="blank">dataset</a> .

Download the file and put it in your HDFS home directory.
Remember to ignore the first row in every mapper on this dataset.

    I used filezilla to put the tree.csv file into my home directory, and in my hdfs directory with the following command:
    hdfs dfs -put trees.csv

### 1.8.1 Districts containing trees (very easy)
Write a MapReduce job that displays the list of distinct containing trees in this file. Obviously, it’s twenty or less different arrondissements, but exactly how many?

You just need to put rounding as a key and put any value or NullWritable
as a value, output from the mapper.

The reducer will just have to output the keys and values it receives, it doesn’t even have to loop through the values since it ignores them.

With the command:

    yarn jar /home/vserena/hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar districts /user/vserena/trees.csv /user/vserena/lab5/1.8.1

I found the resulting:

    hdfs dfs -cat lab5/1.8.1/part-r-00000
    11
    12
    13
    14
    15
    16
    17
    18
    19
    20
    3
    4
    5
    6
    7
    8
    9
<br>


>for the following question regarding species I took the liberty of choosing the "espece" column of the csv as the tp is a bit unclear on bit point (species vs genre)

### 1.8.2 Show all existing species (very easy)
Write a MapReduce job that displays the list of different species trees in this
file.

with the command

    hdfs dfs -cat lab5/1.8.2/part-r-00000 | head -5
> 
    araucana
    atlantica
    australis
    baccata
    bignonioides
<br>

### 1.8.3 Number of trees by species (easy)
Write a MapReduce job that calculates the number of trees of each species.

reminder to run the map reduce I do:

    yarn jar /home/vserena/hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar nbTrees /user/vserena/trees.csv /user/vserena/lab5/1.8.3
   > and this to display the result (without the tail first to check for anomalies)

    hdfs dfs -cat lab5/1.8.3/part-r-00000 |tail -5
    
    species         number of trees

    tomentosa       2
    tulipifera      2
    ulmoides        1
    virginiana      2
    x acerifolia    11

### 1.8.4 Maximum height per specie of tree (average)
Write an MapReduce job that calculates the height of the tallest tree of each kind.
For example, the tallest Acer is 16m, the tallest Platanus is 45m, etc.

and here are the result I found (still with a cat |tail-5)

    species         height

    tomentosa       20.0
    tulipifera      35.0
    ulmoides        12.0
    virginiana      14.0
    x acerifolia    45.0

### 1.8.5 Sort the trees height from smallest to largest (average)
Write an MapReduce job that sort the trees height from smallest to largest.

here is my result:  (head -3 and tail-3)

    objectid    height

    3       2.0
    89      5.0
    62      6.0
    ..........
    40      40.0
    90      42.0
    21      45.0

### 1.8.6 District containing the oldest tree (difficult)
Write a MapReduce job that displays the district where the oldest tree is.

The mapper must extract the age and district of each tree.

The problem is, this information can’t be used as keys and values (why?). You
will need to define a subclass of Writable to contain both information.

The reducer should consolidate all this data and only output district.

My result is as such:

    The oldest tree can be find in district 5


### 1.8.7 District containing the most trees (very difficult)
Write a MapReduce job that displays the district that contains the most trees.

The problem is that the program will almost certainly display a list of pairs
(district number, number of trees) not ordered by number.

If we apply this program to real big data not limited to the arrondissements
of Paris, we would recover a huge list, unusable and the classification could take
hours. How do you keep only the best answer?

The solution probably goes through a second MapReduce phase, in which the
Mapper retrieves the pairs (district, number) from the first phase, and makes
them pairs whose key is unimportant (NullWritable) and the values themselves
are the same input pairs.

The Reducer receives them all and must keep the best pair.

I ultimately found this result: 

    district containing the most trees:     16
