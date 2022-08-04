package com.mycoiffeur.recommendation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ScriptRun  {
    public static void runScript() throws IOException {
        Logger logger = LoggerFactory.getLogger(ScriptRun.class);
        try{
            logger.info("Trying to generate Recommendation");
            ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir")+"\\src\\main\\java\\com\\mycoiffeur\\recommendation\\main.py");
            Process process = builder.start();
            logger.info("Done");
        }catch(Exception e){
            logger.error(e.toString());
        }
    }
}
