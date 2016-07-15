package x

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._ // *** must have for rdd operations ***
import org.apache.spark.SparkConf
import org.apache.spark.rdd._

object Clickstream {
  def main(args: Array[String]) {
    if (args.length < 1) {
      println ("need input location")
      System.exit(1)
    }

    val input = args(0) // can be wildcard  'dir/*.log'

    // ## Create a SparkContext & SQLContext
    val conf = new SparkConf().setAppName("Clickstream")
    //conf.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val clickstream = sqlContext.read.json(input)

    // ## Count # of records
    val count =  clickstream.count
    println("### total clickstream records " + count)

    // ## Find top-10 domains
    clickstream.registerTempTable("clickstream")
    val top10 = sqlContext.sql("select domain, count(*) as total from clickstream  group by domain order by total desc")
    println ("### top domains : \n" + top10.show)

    // ## Bonus Lab : more things to try
   //  1 - extract attributes from top10 DF 
   //  2 - find the 'views / clicks' ratio for each domain in top10


  }
}
