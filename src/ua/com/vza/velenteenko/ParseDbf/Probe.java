package ua.com.vza.velenteenko.ParseDbf;

import java.io.IOException;
import java.sql.SQLException;

import ua.com.vza.velenteenko.DBUtils.DBParseProcess;

public class Probe {

	public static void main(String[] args) throws IOException, SQLException {

		System.out.println("Start:");
		 long starttime = System.currentTimeMillis();
		 
		 DBParseProcess process = new DBParseProcess();
		 process.updateData();
		 System.out.println("--------------\nDbProcess done!");
		System.out.println("Job done!\nAll data are parse!!!");
		
		 long endtime = System.currentTimeMillis();
		 long totaltime = (endtime - starttime) ;
		 System.out.println("Total time "+totaltime+" seconds.");

		System.out.println("End:");
	}
}
