
public class SBox {

	/* substitution box
	input:		yxxxy
	inner bits:	xxxx
	outer bits:	yy
	*/
	
	private String[][] boxes;
	
	public SBox(){
		boxes = new String[][]{
			new String[]{"0010", "1100", "0100", "0001", "0111", "1010", "1011", "0110", "1000", "0101", "0011", "1111", "1101", "0000", "1110", "1001"}, 
			new String[]{"1110", "1011", "0010", "1100", "0100", "0111", "1101", "0001", "0101", "0000", "1111", "1010", "0011", "1001", "1000", "0110"},
			new String[]{"0100", "0010", "0001", "1011", "1010", "1101", "0111", "1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
			new String[]{"1011", "1000", "1100", "0111", "0001", "1110", "0010", "1101", "0110", "1111", "0000", "1001", "1010", "0100", "0101", "0011"}};
	}
	
	public String[][] getBoxes(){
		return boxes;
	}
	
}
