package recommend.service;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.ALS;
import scala.Int;

import java.util.*;


/**
 * @Auther: wangming
 * @Date: 2018/12/19 17:23
 * @Description:
 */
public class SparkService {



    public static SparkSession spark;
    public static Dataset<Row> userDataSet;
    public static Dataset<Row> businessDataSet;

//    public templates String user_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_user.json";
    public static String business_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_business.json";
    static{
        spark = SparkSession.builder().appName("test").master("local[*]").getOrCreate();
//        userDataSet = spark.read().json(user_path);
        businessDataSet = spark.read().json(business_path);

    }

    public static List<String> recommendHotRestaurant(){

        List<Row> list = businessDataSet.select("business_id","name","stars","city").sort("stars").takeAsList(10);
        List<String> res_list = new ArrayList<>();
        for(int i = 0;i <list.size();i ++){
            res_list.add(list.get(i).get(1).toString());
        }
        return res_list;
    }


}
