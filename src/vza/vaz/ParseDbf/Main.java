package vza.vaz.ParseDbf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vza.vaz.XmlSettings.ParseXmlSettings;

public class Main {

	public static void main(String[] args) {
		
		 // TODO code application logic here
        String r1 ="(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*";
        String gost = "ГОСТ(\\s[0-9]+(\\-+[0-9]+)+)";
       // String r3 = "^[А-Яа-я]+(\\s)+.?";
        String r3 = "^([А-Яа-я]+\\s+)+|([А-Яа-я]+\\-+[А-Яа-я]+)+|([(]([А-Яа-я]+\\s+[А-Яа-я]+)[)])";
        String beginString = "^([А-Яа-я]+\\s+)+";
        String beginStringNextString = "^([А-Яа-я]+\\s+)+([А-Яа-я]+\\-+[А-Яа-я]+)+";
        String beginStringNextStringWithBrachet = "^([А-Яа-я]+\\s+)+([(]([А-Яа-я]+\\s+[А-Яа-я]+)[)])";
        String beginStringNexStringOrDigit = "^(([А-Яа-я]+\\s+)+|([А-Яа-я]+\\-+[А-Яа-я]+)+)*(\\d+\\.\\d+\\s+)([А-Яа-я]+\\d+)";
        String xz = "^([А-Яа-я]+\\s+)(\\d+\\D\\d+)+([х]+\\d+)+";
        
String s = "адреса эл.почты:mymail@tut.by и rom@bsu.by";
String s2 = "Круг круг кругл крук ккеп-ццуцуыа 25.0 М2 ГОСТ 23456-09 / ДКХМЛ М2 ТУ 3444-0392-222-1 ГОСТ 23456-079-98980-8768907";
String s3 = "Круг 25.0 М2 ДСТУ 4322:2323 (ГОСТ 23456-09) / ДКХМЛ М2 ТУ 3444-0392-222-1";
String s4 = "Круг 25.0 М2 ГОСТ 23456-09 / ДКХМЛ М2 ОСТ 3343-2828001 ТУ 3444-0392-222-1";
String s5 = "Лента 1.1х300.0 М2 ГОСТ 1173-93 / М2 ГОСТ 1173-77";
String s6 = "Медь сернокислая (купорос медный) ГОСТ 1713-79";
String s7 = "any thing //line comment\\n/*mycomment*/ any thing";
String s8 = "Лист 1.5х70.0х1.400 ГОСТ 19904-90 / 17Х18Н9Н ТУ 14-1-2186-77";

Pattern p2 = Pattern.compile(xz);

Matcher m2 = p2.matcher(s8);

while (m2.find())
     {
        System.out.println("Line: " + m2.group(0));
     }

     HashMap<String, ArrayList<String>> readConfigs = new HashMap<>(ParseXmlSettings.readXML("./config/config-settings.xml","const", "name", "con"));
     
        for (Map.Entry<String, ArrayList<String>> entrySet : readConfigs.entrySet()) {
            String key = entrySet.getKey();
            ArrayList<String> value = entrySet.getValue();
            System.out.println("Key: "+key+" Value: "+value.toString());
        }

    }    

}
