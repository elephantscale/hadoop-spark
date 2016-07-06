# Hive And Spark

## Working Directory
Hive Spark

## Depends On
[Hadoop setup](../hadoop-setup/README.md)

## Step 1 : Login to Sandbox
Follow instructions for your environment.

We will run Hive Shell and Spark Shell simultaneously.  So we would need at least 2 terminals.  So create at least 2 login terminals.


## Step 2 : In Terminal 1 : Start Hive Shell
```
    $   hive
```

Inspect the tables and run a query on 'clickstream' table.

```sql
    hive>
            show tables;

            select * from clickstream limit 10;

            select action, count(*) as total from clickstream group by action;
```


## Step 3 : In Terminal 2 Start Spark shell

```
    $    spark-shell
```

Type this in Spark Shell
```
    sc.setLogLevel("WARN")
```

Go to http://localhost:4040 in the browser.


## Step 4 : Inspect Hive Tables
Do this in Spark-Shell

```
    scala>

        sqlContext.tableNames

        val t = sqlContext.table("clickstream")

        t.printSchema

        t.show

        sqlContext.sql("select * from clickstream limit 10").show

        sqlContext.sql("select action, count(*) as total from clickstream group by action").show
```

