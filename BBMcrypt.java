import java.io.*;

public class BBMcrypt {

	public static void main(String[] args) {
		
		String action = args[0];		// enc | dec
		BufferedReader inputfile = null;
		BufferedReader keyfile = null;
		BufferedWriter outputfile = null;
		String mode = "";
		Run run = new Run();
		try {
			for(int c = 0; c < args.length; c++){
				if (args[c].equals("-K"))
					keyfile = new BufferedReader(new FileReader(args[c+1]));
				else if (args[c].equals("-I"))
					inputfile = new BufferedReader(new FileReader(args[c+1]));
				else if (args[c].equals("-O"))
					outputfile = new BufferedWriter(new FileWriter(args[c+1]));
				else if (args[c].equals("-M"))
					mode = args[c+1];
			}
			run.runBBMcrypt(action, keyfile, inputfile, outputfile, mode);
			inputfile.close();
			outputfile.close();
			keyfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}
