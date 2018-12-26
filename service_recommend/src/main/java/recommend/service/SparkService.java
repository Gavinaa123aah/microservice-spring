package recommend.service;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.ALS;
import scala.Function1;
import scala.Int;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: wangming
 * @Date: 2018/12/19 17:23
 * @Description:
 */
public class SparkService {



    public static SparkSession spark;
    public static Dataset<Row> reviewDataSet;
    public static Dataset<Row> userDataSet;
    public static Dataset<Row> businessDataSet;
//    public templates String path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_review.json";

    public static String review_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/review_100000.json";
    public static String user_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/id_user.json";
    public static String business_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/id_business.json";
    static{
        spark = SparkSession.builder().config("spark.network.timeout.","300").appName("test").master("local[*]").getOrCreate();
        reviewDataSet = spark.read().json(review_path);
        userDataSet = spark.read().json(user_path);
        businessDataSet = spark.read().json(business_path);

    }



    public static void main(String[] args) {

    }
    public static List<String> recommendRestaurantForUser(String user_id) throws IOException {
        userDataSet.createOrReplaceTempView("user");
        reviewDataSet.createOrReplaceTempView("review");
        businessDataSet.createOrReplaceTempView("business");
        Dataset<Row> review_list = spark.sql("select user.id,business.id,review.stars from review,user,business " +
                "where review.user_id = user.user_id and review.business_id=business.business_id");

        Dataset<Row> users = userDataSet.select("id","user_id","name");
        Dataset<Row> businesses = businessDataSet.select("id","business_id","name","stars");


        JavaRDD<Row> data = review_list.toJavaRDD();
        JavaRDD<Rating> rating = data.map(new Function<Row, Rating>() {
            @Override
            public Rating call(Row row) throws Exception {

                return new Rating(Integer.parseInt(row.get(0).toString()),Integer.parseInt(row.get(1).toString()),
                        Double.parseDouble(String.valueOf(row.get(2))));
            }
        });
        int rank = 10;
        int NumIterations = 10;
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(rating),rank,NumIterations,0.01);
        //recommend nth business for user-input
        int user_num = 0;

        List<Row> id_list = users.select("id","name").where("user_id = "+"'"+user_id+"'").collectAsList();
        String username = id_list.get(0).get(1).toString();
        user_num = Integer.parseInt(id_list.get(0).get(0).toString());
        List<String> res_list = new ArrayList<>();
        List<String> resBandS_list = new ArrayList<>();
        Rating[] rec_res = model.recommendProducts(user_num,10);//23132
        String review_line = "";
        String business_line = "";

        for(Rating r : rec_res){
            System.out.println(r.user()+","+r.product());
            List<Row> business_list = businesses.select("name","business_id","stars").where("id = "+r.product()).collectAsList();
            String business_name = business_list.get(0).get(0).toString();

            review_line+="{\"user_id\":"+r.user()+",\"business_id\":"+r.product()+",\"stars\":"+r.rating()+"},"+'\n';
            business_line+="{\"id\":"+r.product()+",\"business_id\":\""+business_list.get(0).get(1).toString()+"\""+
                    ",\"name\":\""+business_name+"\",\"stars\":"+business_list.get(0).get(2)+"},\n";

            res_list.add(business_name);
            resBandS_list.add("for username:"+username+
                    " recommend business "+business_name+
                    " recommend stars "+r.rating());
            System.out.println("for username:"+username+
                    " recommend business "+business_name+
                    " recommend stars "+r.rating());
        }
//        System.out.println(review_line);
        String review_file ="/Users/dreamhome/Desktop/review_file.json";
        String business_file = "/Users/dreamhome/Desktop/business_file.json";
        FileOutputStream fileWriter = new FileOutputStream(review_file);
        OutputStreamWriter writer = new OutputStreamWriter(fileWriter,"UTF-8");
//        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(review_file.getName(),true));
        writer.write(review_line);
        writer.close();

        fileWriter.close();
        FileOutputStream fileWriter1 = new FileOutputStream(business_file);
        OutputStreamWriter writer1 = new OutputStreamWriter(fileWriter1,"UTF-8");
        writer1.write(business_line);
        writer1.close();

        return res_list;
    }


}
