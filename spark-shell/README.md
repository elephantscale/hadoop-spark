# Spark Shell

## Working Directory
spark-shell

## Depends On
[Hadoop setup](../hadoop-setup/README.md)

## Step 1 : Login to Sandbox
Here is the command
```
    $    ssh -l root -p 2222  127.0.0.1
```

## Step 2 : Start Spark shell

```
    $    spark-shell
```

## Step 3 : Set the log level to WARN
```
    scala>    sc.setLogLevel("WARN")
```

## Step 4 : Inspect the Shell UI
Go to http://localhost:4040 in the browser.


## Step 5 : Load a file 
Let's load  `/etc/hosts` file in Spark Shell.
Issue the following commands in Spark-shell

```
        val f = sc.textFile("file:///etc/hosts")

        # count how many lines are there
        f.count

        # print the first line
        f.first

        # print all lines
        f.collect
```

** => Inspect the Spark Shell UI (port 4040) ** 

## Step 6 : Load a HDFS file
Let's load  a sample clickstream data from `/user/root/clickstream/in/clickstream.csv'.
Try the following in Spark-shell

```
    val h = sc.textFile("/user/root/clickstream/in/clickstream.csv")

    # count the lines
    h.count

    # print the lines
    h.collect

```

Now let's load all data in `clickstream/in` directory.

```
    val h = sc.textFile("/user/root/clickstream/in/")

    # count the lines
    h.count
```

** => Inspect the Spark Shell UI (port 4040) ** 
