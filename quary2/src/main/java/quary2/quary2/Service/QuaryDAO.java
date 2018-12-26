package quary2.quary2.Service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import quary2.quary2.InformationBean.Information;

import java.util.List;

@Service
public class QuaryDAO {

    String filename="/Users/dreamhome/Desktop/learn/云计算/yelp_dataset的副本/id_business.json";

    public SparkSession getSession(){

        SparkSession session=SparkSession
                .builder()
                .appName("Java Decition Tree Example")
                .master("local")
                .getOrCreate();
        return session;
    }

    public List<Information> getBaseInfoBycon(String key, String addr, String state, double stars, String city){

        Dataset<Row> df=null;
        if (key!=null&&key!="")
            df=getlistsWithkey(key);
        else
            df=getSession().read().json(this.filename);
        if(addr!=null && addr!="") {
            addr="'"+addr+"'";
            df=df.filter("address="+addr);
        }
        if(state!=null && state!="") {
            state="'"+state+"'";
            df=df.filter("state="+state);
        }
        if(city!=null && city!="") {
            city="'"+city+"'";
            df=df.filter("city="+city);
        }

        double s=stars-0.01;
        String ts=""+s;
        df=df.filter("stars >"+ts);
        Dataset<Information> d=df.select("name","business_id","address","stars").as(Encoders.bean(Information.class));
        List<Information> info=d.collectAsList();
//        List<StructField> fields = new ArrayList<>();
//        fields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
//        fields.add(DataTypes.createStructField("user_id", DataTypes.StringType, true));
//        StructType schema = DataTypes.createStructType(fields);
//        List<Information> info = getSession().createDataFrame(d.toJavaRDD(), schema)
//                .as(Encoders.bean(Information.class))
//                .collectAsList();
        return info;
    }

    public Dataset<Row> getlistsWithkey(String key){
        Dataset<Row> df=getSession().read().json(this.filename);

        df.createOrReplaceTempView("restaurant");

        //去除关键字中可能含有" ' "
        if (key.contains("'"))
            key=key.replace("'","%");

        Dataset<Row> res=getSession().sql("Select name , business_id, address,stars,state,city from restaurant where restaurant.name like '%" + key+"%' ");
        return res;
    }

    public List<Information> getBaseInfoByKey(String key){

        Dataset<Row> df=getSession().read().json(this.filename);

        df.createOrReplaceTempView("restaurant");

        //去除关键字中可能含有" ' "
        if (key.contains("'"))
            key=key.replace("'","%");

        Dataset<Information> d=getSession().
                sql("Select name , business_id, address,stars from restaurant where restaurant.name like '%" + key+"%' ").
                as(Encoders.bean(Information.class));
        List<Information> info=d.collectAsList();
//        List<StructField> fields = new ArrayList<>();
//        fields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
//        fields.add(DataTypes.createStructField("user_id", DataTypes.StringType, true));
//        StructType schema = DataTypes.createStructType(fields);
//        List<Information> info = getSession().createDataFrame(d.toJavaRDD(), schema)
//                .as(Encoders.bean(Information.class))
//                .collectAsList();
        return info;
    }

}
