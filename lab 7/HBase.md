# 1 HBase CLI
In this tutorial, you will discover the NoSQL database: Apache HBase. The
syntax used can be confusing, it’s Ruby.

## 1.1 First commands
### 1.1.1 Base commands
- Connect to HADOOP cluster using SSH.
- Initialize a Kerberos ticket.
- Type the command hbase shell in the terminal prompt.
- Type these commands:
- status
- version
- whoami
- list
- exit


### 1.1.2 Create your own namespace
The first step is to create your own namespace. The main benefit of having your
own namespace is to create boundaries between users. You can create one and
only one namespace, using your username.
- Find the command to create your namespace.

    create_namespace 'vserena'


### 1.1.3 Creating a table
- Create a table in your namespace.
- The tables must be called: library, and have thoses columns families:
- author with 2 versions.
- book with 3 versions.
- Describe the table structure.

Reminder: The families are like namespaces for the columns

    create 'vserena:library', {NAME=>'author', VERSIONS=>2}, {NAME=>'book', VERSIONS=>3}

    describe 'vserena:library'


## 1.1.4 Adding values
- Add thoses data inside the table (key, column, value):
- vhugo, author:lastname, Hugo
- vhugo, author:firstname, Victor
- vhugo, book:title, La l´egende des siècles
- vhugo, book:category, Poèmes
- vhugo, book:year, 1855
- vhugo, book:year, 1877
- vhugo, book:year, 1883
- jverne, author:lastname, Jules
- jverne, author:firstname, Verne
- jverne, book:publisher, Hetzel
- jverne, book:title, Face au drapeau
- jverne, book:year, 1896

You will find that adding data is very fast. Only the launch of the HBase shell is slow.

All of these instructions only created two tuples in total. The first is identified by vhugo and the second by jverne (identifiers very poorly chosen if it was necessary to add other books of these authors).

You can also see that these tuples do not all have the same columns.

    put 'vserena:library', 'vhugo', 'author:lastname', 'Hugo'
    put 'vserena:library', 'vhugo', 'author:firstname', 'Victor'
    put 'vserena:library', 'vhugo', 'book:title', "la l'égende des siècles"
    put 'vserena:library', 'vhugo', 'book:category', 'Poèmes'
    put 'vserena:library', 'vhugo', 'book:year', 1885
    put 'vserena:library', 'vhugo', 'book:year', 1877
    put 'vserena:library', 'vhugo', 'book:year', 1883

    put 'vserena:library', 'jverne', 'author:lastname', 'Verne'
    put 'vserena:library', 'jverne', 'author:firstname', 'Jules'
    put 'vserena:library', 'jverne', 'book:publisher', 'Hetzel'
    put 'vserena:library', 'jverne', 'book:title', 'Face au drapeau'
    put 'vserena:library', 'jverne', 'book:year', 1986


### 1.1.5 Counting values
HBase does not have many functions. It’s a huge hash table of pairs ¡key, value¿, extremely efficient, but that’s it. We can only count them and make research it.
- Find a command to count the tuples.
- When the table is huge, you must specify a cache size

count 'vserena:library', CACHE=>1000


### 1.1.6 Retrieving values
Write (don’t copy/paste) thoses commands to retrieve the values (don’t forget to change your namespace):
- get 'vserena:library', ’vhugo’
    ```
    COLUMN                     CELL
    author:firstname           timestamp=1605601300110, value=Victor
    author:lastname            timestamp=1605601295566, value=Hugo
    book:category              timestamp=1605601313182, value=Po\xC3\xA8mes
    book:title                 timestamp=1605601305718, value=la l'\xC3\xA9gende des si\xC3\xA8cles
    book:year                  timestamp=1605601324354, value=1883
    ```
- get 'vserena:library', ’vhugo’, ’author’
    ```
    COLUMN                     CELL
    author:firstname           timestamp=1605601300110, value=Victor
    author:lastname            timestamp=1605601295566, value=Hugo
    ```
- get 'vserena:library', ’vhugo’, ’author:firstname’
    ``` 
    COLUMN                     CELL
    author:firstname           timestamp=1605601300110, value=Victor
    ```
- get 'vserena:library', ’jverne’, COLUMN=>’book’
    ``` 
    COLUMN                     CELL
    book:publisher             timestamp=1605601329865, value=Hetzel
    book:title                 timestamp=1605601329880, value=Face au drapeau
    book:year                  timestamp=1605601331263, value=1986
    ```
- get 'vserena:library', ’jverne’, COLUMN=>’book:title’
    ``` 
    COLUMN                     CELL
    book:title                 timestamp=1605601329880, value=Face au drapeau
    ```
- get 'vserena:library', ’jverne’, COLUMN=>[’book:title’, ’book:year’,
’book:publisher’]
    ```
    COLUMN                     CELL
    book:publisher             timestamp=1605601329865, value=Hetzel
    book:title                 timestamp=1605601329880, value=Face au drapeau
    book:year                  timestamp=1605601331263, value=1986
    ```
- get 'vserena:library', ’jverne’, FILTER=>"ValueFilter(=, ’binary:Jules’)
    ``` 
    COLUMN                     CELL
    author:firstname           timestamp=1605601329850, value=Jules
    ```

We must provide the tuple identifier to the get command. It displays all the columns of the tuple.

Then, we can add properties such as the name of the family, the name of the column (with two possible syntaxes) or filters.

This filter means: what is the column whose the value is Jules.

On the other hand, the filters are particularly difficult to write and do not work not always well, we will limit ourselves to a simple comparison as here.


### 1.1.7 Tuple browsing
The get command can only return a single tuple. Here is another way to recover the information, with the scan command.
>Write thoses commands:
- The first scan displays all the data in the table.
    ```
    scan 'vserena:library'
    ```
- The second only displays data from the book family.
    ```
    scan 'vserena:library', COLUMNS=>'book'
    ```
- The third scan only displays the year values of the book family present in the table.
    ```
    scan 'vserena:library', COLUMNS=>'book:year'
    ```
>It is possible to express finer conditions. Write thoses commands:
- The first scan scans the tuples by keys between a and n and the fields of the family author.
    ```
    scan 'vserena:library', STARTROW=> 'a', ENDROW=>'n', COLUMN=>'author'
    ```
- The second does exactly the same, but with a filter.
    ```
    scan 'vserena:library', {FILTER=>"RowFilter(>=,'binary:a') AND RowFilter(<, 'binary:n') AND FamilyFilter(=,'binary:author')"}
    ```
- The third scan displays the author values: firstname.
    ```
    scan 'vserena:library', {FILTER=>"ColunfPrefixFilter('firstname')"}
    or familyfilter + qualifierFilter
    ```
- The fourth scan searches for columns whose value equals the specified title.
    ```
    scan 'vserena:library', {FILTER=>"SingleColumnValueFilter('book','title',=,'binary:Face au drapeau')"}
    ```
- The fifth displays the tuples whose (latest version of the) book column:
date is less than or equal to 1890.
    ```
    scan 'vserena:library', {FILTER=>"SingleColumnValueFilter('book','year',<= ,'binary:1890', false, true)"}
    ```
- The last filter is more complex, it searches for tuples whose key begins
with jv and one of the values of which matches the regular expression.
    ```
    scan 'vserena:library', {FILTER=>"PrefixFilter('regexstring:jv*')"}
    ```

Note that all these scans return each time the complete tuple, all its columns, since we haven’t limited them with COLUMNS.

Be careful to spell the fields correctly, otherwise all the tuples are selected.

Do not mix a FILTER type condition with a COLUMNS condition, it does
not work at all.

Do not forget binary: in front of the constants to compare.


### 1.1.8 Updating a value
We will look at the versions of the data. The table was created to keep 2 versions of the values from the author family and 3 from the book family.
- put 'vserena:library', 'vhugo', 'author:lastname', 'HAGO'
- put 'vserena:library', 'vhugo', 'author:lastname', 'HUGO'
- put 'vserena:library', 'vhugo', 'author:firstname', 'Victor
Marie'
- put 'vserena:library', 'vhugo', 'author:lastname', 'Hugo'
- get 'vserena:library', 'vhugo', 'author'
- get 'vserena:library', 'vhugo', COLUMNS=>'author'
- get 'vserena:library', 'vhugo', COLUMNS=>'author', VERSIONS=>10

The versions are indicated with their timestamp. Unfortunately, it does not seem possible to manipulate as a readable date.


### 1.1.9 Deleting a value or a column
There are several types of information that can be deleted: value, column and family whole. If we delete a value, it also deletes the older values.

In the following example, you will need to enter the **timestamp** of the HUGO value for the lastname.

Look for it in the last get. Write thoses commands:
- The first deleteall deleted the value author:name=HUGO, but not the
other (unless you have entered the wrong timestamp).
    ```
    deleteall 'vserena:library', 'vhugo', 'author:lastname', 1605610790472
    ```
- The second deleted then all values for the column firstname.
    ```
    delete 'vserena:library', 'vhugo' 'author:firstname'
    delete 'vserena:library', 'jverne' 'author:firstname'
    ```
- The last deleteall deleted the entire tuple.
    ```
    deleteall 'vserena:library', 'vhugo'
    deleteall 'vserena:library', 'jverne'
    ```
- Use scan command to check the version 10.
    ```
    scan 'vserena:library', VERSIONS=>10
    ```

There is another command, delete, but instead of deleting information, it
places a special value marking it as deleted.

### 1.1.10 Deleting a table
Deleting a table first requires deactivating it on the region servers. Write thoses commands:
- Disable the table.
    ```
    disable 'vserena:library'
    ```
- Drop the table.
    ```
    drop 'vserena:library'
    ```

<br/>

## 1.2 Trees
You are going to work on the tree file, previously inserted in HBase.

### 1.2.1 Data insertion
The goal is to insert the trees.csv file in the form of an HBase table then do some simple questions.

It’s a CSV file, the header gives the fields names.
The **OBJECTID:12** field is the identifier of the tuples.
We will group the other information into three families (the index of the field is put after the name):
- GENRE:3,ESPECE:4,FAMILLE:5,NOM COMMUN:10,VARIETE:11 in the gender
family
- ANNEE PLANTATION:6,HAUTEUR:7,CIRCONFERENCE:8 in the information
family
- GEOPOINT:1,ARRONDISSEMENT:2,ADRESSE:9,NOM EV:13 in the address
family

The idea is to write a program that will insert the data. As the file is simple, you can use a scripting language like Python.
- Its principle is to read the file line by line, ignoring the first one.
- Each line is broken up into words.
- The words are grouped by families and inserted in the table.
- Usage must be like : hdfs dfs -cat /user/mhatoum/trees.csv | python
./trees2hbase.py | hbase shell.
