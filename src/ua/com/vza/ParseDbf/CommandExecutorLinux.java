package ua.com.vza.ParseDbf;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExecutorLinux {

	public static String execute(String linuxTerminalCommand) {
		StringBuilder sb = new StringBuilder();
		String[] commands = new String[] { "/bin/sh", "-c",
				linuxTerminalCommand };
		try {
			Process process = new ProcessBuilder(commands).start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
			
			while((s = stdError.readLine()) != null){
				sb.append(s);
				sb.append("\n");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sb.toString();
	}

}
