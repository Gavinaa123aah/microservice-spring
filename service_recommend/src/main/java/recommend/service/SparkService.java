package recommend.service;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.ALS;

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
//    public static String path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_review.json";

    public static String review_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/review_100000.json";
    public static String user_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_user.json";
    public static String business_path = "/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/yelp_academic_dataset_business.json";
    static{
        spark = SparkSession.builder().appName("test").master("local[*]").getOrCreate();
        reviewDataSet = spark.read().json(review_path);
        userDataSet = spark.read().json(user_path);
        businessDataSet = spark.read().json(business_path);

    }



    public static void main(String[] args) {
        // TODO 自动生成的方法存根

        //Dataset<Row> d1 =originDataSet;
     //   d1.printSchema();
//        SparkConf conf = new SparkConf().setMaster("local").setAppName("MyMp3");
//        JavaSparkContext jsc = new JavaSparkContext(conf);



//        val dfs = sqlContext.read.json("/root/wangbin/employee.json")

//        JavaRDD<String> originData = jsc.textFile("/Users/dreamhome/spark-2.3.1-bin-hadoop2.7/yelp_academic_dataset_user.json");
//
//        if(!originData.toString().isEmpty()){
//
//           System.out.println("spark="  + originData.toString());
//        }


//        JavaRDD<String> maleFilterData = originData.filter(new Function<String, Boolean>() {//过滤出性别为M的数据
//            @Override
//            public Boolean call(String s) throws Exception {
//                return s.contains("M");
//            }
//        });

        //Dataset<Row> a = d1.select("name");
        //a.show();
        //a.describe().show();

//        originDataSet = spark.read().json(data_path);
//        originDataSet.describe().show();
        //RDD<Rating> ratingRDD;

    }
    public static List<String> recommendRestaurantForUser(String user_id){
        Map<String, Integer> name_map = new HashMap<>();//save user_id and num
        Map<String, Integer> business_map = new HashMap<>();//save business_id and num
        JavaRDD<Row> userRow = userDataSet.select("user_id","name").toJavaRDD();
        JavaRDD<Row> businessRow = businessDataSet.select("business_id","name").toJavaRDD();
        JavaRDD<String> user_num_id_name = userRow.map(new Function<Row, String>() {
            int index = 0;
            @Override
            public String call(Row row) throws Exception {
                return String.valueOf(++index)+','+row.get(0).toString()+','+row.get(1).toString()+"\n";
            }
        });
        Map<Integer,String> user_num_name_map = new HashMap<>();//save usernum and name
        Map<Integer,String> business_num_name_map = new HashMap<>();//save businessnum and name
        for(String strs : user_num_id_name.collect()){
            String[] str = strs.split(",");
            name_map.put(str[1],Integer.parseInt(str[0]));
            user_num_name_map.put(Integer.parseInt(str[0]),str[2]);
        }
        JavaRDD<String> business_num_id_name = businessRow.map(new Function<Row, String>() {
            int index = 0;
            @Override
            public String call(Row row) throws Exception {
                return String.valueOf(++index)+','+row.get(0).toString()+','+row.get(1).toString()+"\n";
            }
        });
        for(String strs : business_num_id_name.collect()){
            String[] str = strs.split(",");
            business_map.put(str[1],Integer.parseInt(str[0]));
            business_num_name_map.put(Integer.parseInt(str[0]),str[2]);
        }
        JavaRDD<Row> data = reviewDataSet.select("user_id","business_id","stars").toJavaRDD();
        JavaRDD<Rating> rating = data.map(new Function<Row, Rating>() {
            @Override
            public Rating call(Row row) throws Exception {
                String[] sarray = new String[3];
                for(int i = 0;i < 3;i ++){
                    sarray[i] = row.get(i).toString();
                }
                return new Rating(name_map.get(sarray[0]),business_map.get(sarray[1]),
                        Double.parseDouble(String.valueOf(sarray[2])));
            }
        });
//        System.out.println(rating.collect());
        int rank = 10;
        int NumIterations = 10;
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(rating),rank,NumIterations,0.01);
        //recommend nth business for user-input
        int user_num = name_map.get(user_id);
        List<String> res_list = new ArrayList<>();
        Rating[] rec_res = model.recommendProducts(user_num,5);//23132
        for(Rating r : rec_res){
            res_list.add(business_num_name_map.get(r.product()).replace('\n',' '));
            System.out.println("for username:"+user_num_name_map.get(r.user()).replace('\n',' ')+
                    " recommend business "+business_num_name_map.get(r.product()).replace('\n',' ')+
                    " recommend stars "+r.rating());
        }

        //recommend nth user for business-input
//        Rating[] rec_user = model.recommendUsers(70679,5);
//        for(Rating r : rec_user){
//            System.out.println("for business:"+user_num_name_map.get(r.product()).replace('\n',' ')+
//                    " recommend users "+business_num_name_map.get(r.user()).replace('\n',' ')+
//                    " recommend stars "+r.rating());
//        }
        return res_list;
    }

}
