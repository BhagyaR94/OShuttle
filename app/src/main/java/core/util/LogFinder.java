package core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Chathun on 6/6/2016.
 */
public class LogFinder {

    public String getLogCat() {
        String log = "";
        try {
            ArrayList<String> commandLine = new ArrayList<String>();
            commandLine.add("logcat");
            commandLine.add("-d");
            commandLine.add("-v");
            commandLine.add("time");
            commandLine.add("-s");
            commandLine.add("tag:W");
            Process process = Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
            String line;
            log = "";
            while ((line = bufferedReader.readLine()) != null) {
                // log.append(line);
                // log.append("\n");
                log += line;
                log += "\n";
            }


        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return log;
    }
}
