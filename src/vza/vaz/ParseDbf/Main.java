package vza.vaz.ParseDbf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.healthmarketscience.jackcess.impl.CodecHandler;
import com.healthmarketscience.jackcess.impl.CodecProvider;
import com.healthmarketscience.jackcess.impl.PageChannel;

import vza.vaz.XmlSettings.ParseXmlSettings;

public class Main {

	public static void main(String[] args) {

		// TODO code application logic here
		String r1 = "(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*";
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

		while (m2.find()) {
			System.out.println("Line: " + m2.group(0));
		}

//		 ReadDbf dbf = new ReadDbf();
//		 ArrayList<String> lines = new ArrayList<String>();
//		 lines.addAll(dbf.getRows());
//		
//		 for (String string : lines) {
//		 System.out.println(string);
//		 }

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
            String url = "jdbc:odbc:Driver={Microsoft dBase Driver (*.dbf)};DBQ=c:/dbf";
			Connection con = DriverManager.getConnection(url);
        Statement st;
			st = con.createStatement();
        ResultSet rs;
			rs = st.executeQuery("SELECT * FROM YOU_DBF_FILE_NAME");
			while (rs.next()) {
			    System.out.println(rs.getString(1));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



//		Connection conn;
//		try {
//			conn = DriverManager.getConnection("jdbc:ucanaccess://m1.DBF");
//			Statement statemen = conn.createStatement();
//			ResultSet rs = statemen.executeQuery("SELECT MN FROM m1.mdb");
//			while (rs.next()) {
//			    System.out.println(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
//		try {
//			Reader in = new InputStreamReader(new FileInputStream("m1.DBF"));
//			BufferedReader br = new BufferedReader(in);
//			String sStream = null;
//			while ((sStream = br.readLine()) != null) {
//				System.out.println(sStream);
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
