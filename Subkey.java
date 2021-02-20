
public class Subkey {
	
	private String[] subkeys = new String[10];
	
	public String[] generate(String bitString){

	    for(int n = 0; n < 10 ; n++){
	
	        String left = leftcircular(bitString, n+1);
	
	        char[] perm = new char[left.length()/2];
	        int j = 0;
	
	        if(n%2 == 0){
	            for(int i = 0; i < bitString.length(); i = i +2){
	                perm[j++] = left.charAt(i);
	            }
	        }
	        else {
	            for(int i = 1; i < bitString.length(); i = i +2){
	                perm[j++] = left.charAt(i);
	            }
	        }
	        
	        subkeys[n] = String.valueOf(perm);
	    }
	    return subkeys;
	}

	static String leftcircular(String str, int d)
	{
	    String leftc = str.substring(d) + str.substring(0, d);
	    return leftc;
	}

}
