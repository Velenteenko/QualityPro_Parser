package vza.vaz.readdbf.parser;

import org.apache.commons.configuration.XMLConfiguration;
import java.util.ArrayList;

/**
 * Created by velenteenko on 03.03.15.
 */
public class ParserDBF {

    private XMLConfiguration conf;
    private final static String CONFIG_ROOT = "./config/";

    private String pathDbf;
    private ArrayList<String> collumns;
    private String collumn;

    public ArrayList<String> getCollumns() {
        return collumns;
    }

    //default
    public ParserDBF() {
       // saveConfig();
       // loadConfig();
    }

    // select all collumns from table in path
    public ParserDBF(String path) {
        this();
        this.setPathDbf(path);
    }

    public ParserDBF(String path, String collumn) {
        this(path);
        this.setCollumn(collumn);
    }

    public ParserDBF(String path, ArrayList<String> collumns) {
        this(path);
        this.setCollumns(collumns);
    }


    public String getPathDbf() {
        return pathDbf;
    }

    public void setPathDbf(String pathDbf) {
        this.pathDbf = pathDbf;
    }

    public void setCollumns(ArrayList<String> collumns) {
        this.collumns = collumns;
    }

    public String getCollumn() {
        return collumn;
    }

    public void setCollumn(String collumn) {
        this.collumn = collumn;
    }


//    @Override
//    public void Configure() {
//
//    }
//
//    @Override
//    public ArrayList<String> loadConfig() {
//        return null;
//    }
//
//
//    @Override
//    public void saveConfig() {
//        if (conf == null) {
//            conf = new XMLConfiguration();
//            conf.setEncoding("UTF-8");
//            conf.setFileName(CONFIG_ROOT + "file.xml");
//            conf.setRootElementName("xml-settings-file");
//            conf.addProperty("path", "/home/velenteeno/libs/");
//            conf.addProperty("table.column", "MN");
//            conf.addProperty("table.column", "MN_1");
//            conf.addProperty("table.column", "MN_342");
//            conf.addProperty("table.column", "MN_5641");
//            conf.addProperty("table.column", "MN_4261");
//            conf.addProperty("table.column", "MN_1345");
//            conf.addProperty("table.column", "MN_12322222222234343400--34034034");
//            try {
//                conf.save();
//            } catch (ConfigurationException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
