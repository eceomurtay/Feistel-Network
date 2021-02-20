
public class Cipher {
	
	/* 10 rounds
	 * 96 bit block size
	 * 96 bit key size
	 */
	
	public String ciphertext = "";
	public String plaintext = "";
	
	public String encryption(String left, String right, String[] keys, int roundNum){
		
		if (roundNum == 10){
			ciphertext += left.concat(right);
		}
		else{
			String ki = keys[roundNum];
			String ri = xorStrings(left, scramble(right, ki));
			encryption(right, ri, keys, roundNum + 1);
		}
		return ciphertext;
	}
	
	public String decryption(String left, String right, String[] keys, int roundNum){
		
		if (roundNum == -1){
			plaintext += right.concat(left);
		}
		else{
			String ki = keys[roundNum];
			String ri = xorStrings(left, scramble(right, ki));
			decryption(right, ri, keys, roundNum - 1);
		}
		return plaintext;
	}
	
	public String scramble(String right, String key){
		
		String xored = xorStrings(right, key);
		String[] boxes = new String[12];			// 12 box p1, p2...
		String[] outputBoxes = new String[12];
		SBox sbox = new SBox();
		String[][] table = sbox.getBoxes();
		
		int b = 0;
		for(int c = 0; c < 48; c+=6){
			boxes[b] = xored.substring(c, c+6);
			b++;
		}
		int xx = 0;
		for(int c = b; c < 12; c++){
			String xored2 = xorStrings(boxes[xx], boxes[xx+1]);
			boxes[c] = xored2;
			xx += 2;
		}
		for(int j = 0; j < boxes.length; j++){
			String bx = boxes[j];
			String inner = bx.substring(1, 5); 						// column
			String outer = bx.substring(0,1) + bx.charAt(bx.length()-1); 			// row
			int colidx = Integer.parseInt(inner, 2);
			int rowidx = Integer.parseInt(outer, 2);
			outputBoxes[j] = table[rowidx][colidx];
		}
		String result = "";
		for(String o:outputBoxes){	
			result += swap(o);
		}
		return result;
	}
	
	public String xorStrings(String str1, String str2){
		
		String result = "";
		for(int i = 0; i < str1.length(); i++)
			result += str1.charAt(i) ^ str2.charAt(i % str2.length());
		return result;
	}
	
	public String swap(String str){	
		
		char[] arr = str.toCharArray();
		for(int c = 0; c < str.length() - 1; c += 2){
		  char temp = arr[c];
		  arr[c] = arr[c+1];
		  arr[c+1] = temp;
		}
		return String.valueOf(arr);
	}
	
}
