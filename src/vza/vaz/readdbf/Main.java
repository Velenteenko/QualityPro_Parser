package vza.vaz.readdbf;

import nl.knaw.dans.common.dbflib.*;
import vza.vaz.readdbf.parser.ParserDBF;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String db_path = "/home/velenteenko/WorkDir/m1.DBF";

//        ArrayList<String> list = new ArrayList<String>();
//        list.add("sadsdas");
//        list.add("sadsdasdasd");

 //       ParserDBF parser = new ParserDBF("sasasa", list);
//        parser.saveConfig();
//        parser.saveConfig();

   //     Table table = new Table(new File(db_path),"CP1251");
//        try {
      //      table.open(IfNonExistent.ERROR);
//            List<Field> fields = table.getFields();
//            for (Field field : fields)
//            {
//                System.out.println("Name:         " + field.getName());
//                System.out.println("Type:         " + field.getType());
//                System.out.println("Length:       " + field.getLength());
//                System.out.println("DecimalCount: " + field.getDecimalCount());
//                System.out.println();
//            }


    //        Iterator<Record> iterator = table.recordIterator();

   //         List<String> old = new ArrayList<String>();
           // old = new LinkedList<String>();
   //         Set<String> newset = new HashSet<String>();

//            while (iterator.hasNext())
//            {
//                Record record = iterator.next();
//               String ch = record.getStringValue("MN");
//                if (ch != null) {
//                    // System.out.println(ch);
//                    // System.out.println("\n");
//                    old.add(ch);
//                    newset.add(ch);
//                }
//            }
//            String st = old.get(2);
//            int len = st.length();

            //dupl
//            System.out.println("Duplicates:");
//            for (String item : old)
//            {
//                for (String item1 : newset) {
//                    if (!item.equals(item1)) {
//                        System.out.println(item + "\n");
//                    }
//                }
//            }

//            System.out.println("Array: "+old.size()+" Len: "+len);
//            System.out.println("HashSet: "+newset.size());
//            int deff = old.size() - newset.size();
//            System.out.println("Deff: "+deff);

            String regex = "(\\w+(ГОСТ)+\\w*)";//(\\w+)ДСТУ(\\w+)ОТС(\\w+)ТУ(\\w+)*" ;
            String s = "Лист 1.5х700х1400 ГОСТ 19904-90 / 17Х18Н9Н ТУ 14-1-2186-77";
            Pattern p2 = Pattern. compile (regex);
            Matcher m2 = p2.matcher(s);
//            while (m2.find()) {
//                System.out.println("e-mail: " + m2.group());
//            }
        while (m2.find()) {

            System.out.println("1: " + m2.group(1));
            System.out.println("2: " + m2.group(2));
        }

//    }catch (IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        catch(CorruptedTableException ex){
//            System.out.println(ex.getMessage());
//        }

        //finally {
//            try {
//                table.close();
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
       // }
    }
}
