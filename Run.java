import java.io.*;
import java.util.Base64;

public class Run {

	public void runBBMcrypt(String action, BufferedReader keyfile, BufferedReader input, BufferedWriter output, String mode) throws IOException{
		
		Cipher cipher = new Cipher();
		// base64 to binary
		String base64_str = keyfile.readLine();
		String bitString = new String(Base64.getDecoder().decode(base64_str.getBytes()));
		Subkey subkey = new Subkey();
		String[] subkeylist = subkey.generate(bitString);
		
		/*		ENCRYPTION		*/
		
		if (action.equals("enc")){				
			
			String plaintext = input.readLine();
			String finalciphertext = "";
			
			/* ECB */
			
			if (mode.equals("ECB")){
				for(int i = 0; i < plaintext.length(); i+=96){
					String blocktext = plaintext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String left = blocktext.substring(0, blocktext.length()/2);
					String right = blocktext.substring(blocktext.length()/2);
					finalciphertext += cipher.encryption(left, right, subkeylist, 0);
					cipher.ciphertext = "";
				}
				output.write(finalciphertext);
			}
			
			/* CBC */
			
			else if (mode.equals("CBC")){
				String initVector = "";				// IV
			    initVector = String.format("%-96s", initVector).replace(' ', '1');
			    for(int i = 0; i < plaintext.length(); i+=96){
					String blocktext = plaintext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String xored = cipher.xorStrings(blocktext, initVector);		// plaintext ^ iv
					String left = xored.substring(0, xored.length()/2);
					String right = xored.substring(xored.length()/2);
					initVector = cipher.encryption(left, right, subkeylist, 0);
					finalciphertext += initVector;
					cipher.ciphertext = "";
				}
			    output.write(finalciphertext);
			}
			
			/* OFB */
			
			else if (mode.equals("OFB")){
				String initVector = "";				
			    initVector = String.format("%-96s", initVector).replace(' ', '1');		// IV
			    String out = "";
			    for(int i = 0; i < plaintext.length(); i+=96){
					String blocktext = plaintext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String left = initVector.substring(0, initVector.length()/2);
					String right = initVector.substring(initVector.length()/2);
					out = cipher.encryption(left, right, subkeylist, 0);
					String xored = cipher.xorStrings(blocktext, out);		// plaintext ^ out
					finalciphertext += xored;
					cipher.ciphertext = "";
					initVector = out;
			    }
			    output.write(finalciphertext);
			}
		}
		
		/*		DECRYPTION		*/
		
		else{
			
			String ciphertext = input.readLine();
			String finalplaintext = "";
			
			/* ECB */
			
			if (mode.equals("ECB")){
				for(int i = 0; i < ciphertext.length(); i+=96){
					String blocktext = ciphertext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String left = blocktext.substring(0, blocktext.length()/2);
					String right = blocktext.substring(blocktext.length()/2);
					finalplaintext += cipher.decryption(right, left, subkeylist, 9);
					cipher.plaintext = "";
				}
				output.write(finalplaintext);
			}
			
			/* CBC */
			
			else if (mode.equals("CBC")){
				String initVector = "";				
			    initVector = String.format("%-96s", initVector).replace(' ', '1');		// IV
				for(int i = 0; i < ciphertext.length(); i+=96){
					String blocktext = ciphertext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String left = blocktext.substring(0, blocktext.length()/2);
					String right = blocktext.substring(blocktext.length()/2);
					String out = cipher.decryption(right, left, subkeylist, 9);
					finalplaintext += cipher.xorStrings(out, initVector);
					initVector = blocktext;
					cipher.plaintext = "";
				}
				output.write(finalplaintext);
			}
			
			/* OFB */
			
			else if (mode.equals("OFB")){
				String initVector = "";				
			    initVector = String.format("%-96s", initVector).replace(' ', '1');		// IV
			    String out = "";
			    for(int i = 0; i < ciphertext.length(); i+=96){
					String blocktext = ciphertext.substring(i, i+96);
					if (blocktext.length() < 96)
						String.format("%-96s", blocktext).replace(' ', '0');
					String left = initVector.substring(0, initVector.length()/2);
					String right = initVector.substring(initVector.length()/2);
					out = cipher.encryption(left, right, subkeylist, 0);
					String xored = cipher.xorStrings(blocktext, out);		// ciphertext ^ out
					finalplaintext += xored;
					cipher.ciphertext = "";
					initVector = out;
			    }
			    output.write(finalplaintext);
			}
		}
	}
    
}
