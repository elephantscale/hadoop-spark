# Running a Spark On YARN

## Working Directory
spark-yarn

## Depends On
[dataframe](../dataframe/README.md)

## Step 1 : Login to Sandbox
Follow instructions for your environment.

## Step 2 : Launch Spark Shell
```
    $   spark-shell
```


## Step 3 : Inspect YARN UI
Access 'Resource Manager UI' as follows:
* Login to AMBARI
* Click on YARN service
* From 'Quick Links' drop down menu on top access 'Resource Manager UI'

Inspect running applications.  
You won't see the Spark-shell application, as it is launched in 'local' mode.

In Spark Shell, try the following:
```
        sc.master
```

You will see 'local[*]' as master.

Quit the Spark shell by typing `exit`

## Step 4 : Connect Spark Shell To YARN
Let's launch Spark-shell and connect to YARN

```
    $  spark-shell --master yarn --deploy-mode client   \
       --driver-memory 512m --executor-memory 512m \
       --num-executors 2 --executor-cores 1 
```

Once the shell is running, try the following
```
    sc.master
```

Also if you need to disable logging...
```
    sc.setLogLevel("WARN")
```

Also inspect YARN Resource Manager UI.  Now you'd see the Spark Shell running as application.

#### Arguments Explained
* --master yarn : submitting to YARN cluster
* --deploy-mode client : Using client mode, recommended for interactive applications like Spark Shell
* --driver-memory 512m : specify how much memory the driver needs to use
* --executor-memory 512m : memory for executors in YARN
* --num-executors 2 : Use 2 executors for processing
* --executor-cores 1 : use only 1 CPU core

We are 'throttling down' the resource usage, as we are running on a small virtual machine.  


## Step 5 : Run a Job on YARN
Let's do a simple dataframe computation in this Spark Shell.

```scala

    val clickstream = sqlContext.read.json("/user/root/clickstream/in-json/clickstream.json")

    clickstream.count

    clickstream.show

    clickstream.registerTempTable("clickstream")

    // count traffic per domain from highest to lowest
    sqlContext.sql("select domain, count(*) as total from clickstream  group by domain order by total desc").show

    // now load the entire json dir
    val clickstream = sqlContext.read.json("/user/root/clickstream/in-json/")

    clickstream.count

    clickstream.registerTempTable("clickstream")
    
    sqlContext.sql("select domain, count(*) as total from clickstream  group by domain order by total desc").show

```