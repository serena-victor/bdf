# 1 Hive Beeline Client
In this tutorial, we will verify the connection to Hive using Beeline client.

## 1.1 Create a Connection to Beeline Client
- Connect to HADOOP cluster using SSH.
- Initialize a Kerberos ticket.
    
        kinit

- Type the command beeline in the terminal prompt, when prompted press enter as username and as password.
    > it changes the prompt to :
    
        0: jdbc:hive2://hadoop-master01.efrei.online:>

- Connect to the Hive using the command 

        !connect:jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.online:2181,hadoop-master03.efrei.online:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2

- Type help command for list of beeline commands.
    >here is a tail equivalent

        !sh                 Execute a shell command
        !sql                Execute a SQL command
        !tables             List all the tables in the database
        !typeinfo           Display the type map for the current connection
        !verbose            Set verbose mode on

- Which command allows you to view the jdbc connection used to connect
to HiveServer2?

        !list

        #0  open     jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.online:2181,hadoop-master03.efrei.online:2181/default;httpPath=cliservice;principal=hive/_HOST@EFREI.ONLINE;serviceDiscoveryMode=zooKeeper;ssl=true;transportMode=http;zooKeeperNamespace=hiveserver2
        #1  open     jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.online:2181,hadoop-master03.efrei.online:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2


- List all databases.
    
        show databases;
        +--------------------+
        |   database_name    |
        +--------------------+
        | aledeuf            |
        | apignerol          |
        | cloupec            |
        | comnes             |
        | cspatz             |
        | database_ccarayon  |
        | default            |
        | egueuret           |
        | mbury              |
        | psalvaudon         |
        | table_hive         |
        | table_test         |
        | temp               |
        | ttea               |
        | vgonnot            |
        +--------------------+



- If not exists, create a database using your username.
    
        create database vserena;

- Use your database.
    
        use vserena

- List the tables.
    
        show tables;
        +-----------+
        | tab_name  |
        +-----------+
        +-----------+

- Create table called temp with a column called col of String type.

        create table if not exists temp (col String);

- Confirm the table creation.

        show tables;
        or
        describe temp;
        +-----------+
        | tab_name  |
        +-----------+
        | temp      |
        +-----------+


- List the columns (name, data type, etc) of temp table.

        describe temp;
        +-----------+------------+----------+
        | col_name  | data_type  | comment  |
        +-----------+------------+----------+
        | col       | string     |          |
        +-----------+------------+----------+


- Remove the table.
        
        drop table temp;

- Type !quit to exit the beeline shell.



## 1.2 Create tables
You are going to write some Hive SQL queries on the remarkable trees of Paris using this <a href="https://github.com/makayel/hadoop-examples-mapreduce/blob/main/src/test/resources/data/trees.csv">dataset.</a>

- Create an external table called trees external.

        create external table if not exists treesExternal(geopoint String, district int, genre String, species String, family String, year int, height float, circumference float, address String, name String, variery String, id int, env String)
        comment 'Trees dataset' 
        row format delimited 
        fields terminated by ';'
        stored as textfile
        location '/user/vserena/hive/external'
        tblproperties ('skip.header.line.count' = '1');

    > then we load the data into the table and remove the first line (header) for some reason we can use delete from clause so we overwrite the table.

        load data inpath '/user/vserena/dataset/trees.csv' overwrite into table treesexternal;
     > we can also use the following command if we dont specify the tblproperties clause:
     
     > insert overwrite table treesexternal select * from treesexternal where id is not null;


- Create an internal table called trees internal.

        create table if not exists treesInternal (geopoint String, district int, genre String, species String, family String, year int, height float, circumference float, address String, name String, variery String, id int, env String)
        comment 'Trees dataset' 
        row format delimited 
        fields terminated by ';'
        stored as textfile
        location '/user/vserena/internal';

- Import data to the internal table using the external table.

        INSERT OVERWRITE TABLE treesinternal SELECT * FROM treesexternal;


- Verify that each table got the same lines count.

        select count(*) from treesinternal;
        select count(*) from treesexternal;
    >and both return:
   
        +------+
        | _c0  |
        +------+
        | 97   |
        +------+


## 1.3 Create queries
In this part, you are going to do the same queries as MapReduce ones using the
internal table created before. You will create queries that:

- displays the list of distinct containing trees;

        select distinct district from treesinternal;

        +-----------+
        | district  |
        +-----------+
        | 3         |
        | 4         |
            .....
        | 19        |
        | 20        |
        +-----------+


- displays the list of different species trees;

        select distinct species from treesinternal;


        +-----------------+
        |     species     |
        +-----------------+
        | araucana        |
        | atlantica       |
        | australis       |
            .........
        | ulmoides        |
        | virginiana      |
        | x acerifolia    |
        +-----------------+


- the number of trees of each kind;

        select species, count(species) from treesinternal group by species;

        +-----------------+------+
        |     species     | _c1  |
        +-----------------+------+
        | araucana        | 1    |
        | atlantica       | 2    |
        | australis       | 1    |
            ...............
        | ulmoides        | 1    |
        | virginiana      | 2    |
        | x acerifolia    | 11   |
        +-----------------+------+


- calculates the height of the tallest tree of each kind;

        select species,max(height) from treesinternal group by species;

        +-----------------+-------+
        |     species     |  _c1  |
        +-----------------+-------+
        | araucana        | 9.0   |
        | atlantica       | 25.0  |
        | australis       | 16.0  |
                .........
        | ulmoides        | 12.0  |
        | virginiana      | 14.0  |
        | x acerifolia    | 45.0  |
        +-----------------+-------+


- sort the trees height from smallest to largest;

        select height from treesinternal order by height;
        +---------+
        | height  |
        +---------+
        | NULL    |
        | 2.0     |
        | 13.0    |
          .....            
        | 40.0    |
        | 42.0    |
        | 45.0    |
        +---------+


- displays the district where the oldest tree is;

        select district,year from treesinternal where year is not null order by year limit 1;

        +-----------+-------+
        | district  | year  |
        +-----------+-------+
        | 5         | 1601  |
        +-----------+-------+


- displays the district that contains the most trees;

        select district, count(id) from treesinternal group by district order by count(id) desc limit 1;

        +-----------+------+
        | district  | _c1  |
        +-----------+------+
        | 16        | 36   |
        +-----------+------+



