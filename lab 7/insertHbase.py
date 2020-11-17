import csv
import happybase
import time

batch_size = 1000
host = "0.0.0.0"
file_path = "/user/vserena/trees.csv"
namespace = "vserena"
row_count = 0
table_name = "treesHbase"


def connect_to_hbase():

    conn = happybase.Connection(host=host,
                                table_prefix=namespace,
                                table_prefix_separator=":")
    conn.open()
    table = conn.table(table_name)
    batch = table.batch(batch_size=batch_size)
    return conn, batch


def insert_row(batch, row):

    batch.put(row[12], {
        "gender:genre": row[3], "gender:species": row[4], "gender:family": row[5], 
        "gender:name": row[10], "gender:variety": row[11],
        "info:year" : row[6], "info:height" : row[7], "info:circumference" : row[8],
        "address:geo" : row[1], "address:district" : row[2], "address:address": row[9],
        "address:env" : row[13],


    })


def read_csv():
    csvfile = open(file_path, "r")
    csvreader = csv.reader(csvfile)
    return csvreader, csvfile


conn, batch = connect_to_hbase()
csvreader, csvfile = read_csv()

try:
    for row in csvreader:
        row_count += 1
        if row_count == 1:
            pass
        else:
            insert_row(batch, row)

    batch.send()
finally:
    csvfile.close()
    conn.close()

