package com.bbuckner.backend;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;

public class InfluxDatabase {
    public String databaseURL;
    public  InfluxDB influxDB;

    public void testDB(){
        influxDB = InfluxDBFactory.connect("http://192.168.50.221:8086");
        Pong response = this.influxDB.ping();
        influxDB.close();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
        }else {
            System.out.println(response);
        }
    }

    public void createDB(String databaseName, String retentionPolicyName, String databaseURL){
        this.databaseURL = databaseURL;
        influxDB = InfluxDBFactory.connect(databaseURL);
        if(!influxDB.query(new Query("SHOW DATABASES")).getResults().toString().contains("forza7")) {
            influxDB.query(new Query("CREATE DATABASE " + databaseName));
            influxDB.setDatabase(databaseName);

            influxDB.setRetentionPolicy(retentionPolicyName);

            influxDB.enableBatch(BatchOptions.DEFAULTS);
            System.out.println(databaseName + " database created!");
        }else{
            System.out.println(databaseName + " exists!");
        }
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }
}
