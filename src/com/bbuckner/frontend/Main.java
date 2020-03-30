package com.bbuckner.frontend;

import com.bbuckner.backend.ForzaDataOutReader;
import com.bbuckner.backend.InfluxDatabase;
import com.bbuckner.relay.ForzaDataOutRelayReader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties configuration = new Properties();
        InputStream streamConfig;
        int listeningPort = 10001;
//        int targetPort = 10002;
//        InetAddress targetAddress = InetAddress.getByName("192.168.50.105");
        String filePath = Paths.get("").toAbsolutePath().toString();
        System.out.println("Loading Config");
        try{
            streamConfig = new FileInputStream(filePath+"/ForzaConfig.properties");
            configuration.load(streamConfig);
            listeningPort = Integer.parseInt(configuration.getProperty("listeningPort"));
//            targetPort = Integer.parseInt(configuration.getProperty("dataOutRelayTargetPort"));
//            targetAddress = InetAddress.getByName(configuration.getProperty("dataOutRelayTargetIP"));
//            System.out.println("Listening-Port: "+Integer.toString(listeningPort)+" Target: "+targetAddress.toString()+":"+Integer.toString(targetPort));
            System.out.println("Done.");
        } catch (Exception e){
            System.out.println("Could not load Config file: "+e.toString());
        }

//        InfluxDatabase db = new InfluxDatabase();
//        db.setDatabaseURL("http://192.168.50.221:8086");
//        db.testDB();
//        db.createDB("forza7", "one_day_only", db.getDatabaseURL());

        ForzaDataOutReader reader = new ForzaDataOutReader(configuration);
        reader.startListener(listeningPort);

//        ForzaDataOutRelayReader relayReader = new ForzaDataOutRelayReader();
//        relayReader.startRelay(listeningPort, targetAddress, targetPort);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
