//Convert String to Integer
//Author: Nishigandha Rane

package convertString;

public class ConvertString {

	public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		
		String str = args[0];
		char[] temp_s = str.toCharArray();     //convert the string to character array 
		int temp_i = 0;   //converted integer
		int temp;
		
		int len = str.length();  //length of the input string
		
		int i = 0;
		
		while(i<len)  //for each character in the character array
		{
			temp_i = temp_i * 10;    //advancing the denomination
			temp = temp_s[i] - '0';  //converting a single character to integer
			temp_i = temp_i + temp;  //adding the converted number to the result
			i++;
		}
		
		System.out.println("Converted integer:"+temp_i);

	}

}
