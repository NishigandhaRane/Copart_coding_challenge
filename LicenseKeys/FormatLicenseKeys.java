//License Keys
//Author: Nishigandha Rane

public class FormatLicenseKeys {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			String s = args[0];
			int k = Integer.parseInt(args[1]);
			int i = 0, j = 0, count = 0;
			
			char[] str = s.toCharArray();
			char[] res = new char[1000];
			
	 		//converting the letters to uppercase
			for(i=0;i<str.length;i++)   
			{
				if(Character.isLetter(str[i]))
				{
					str[i] = Character.toUpperCase(str[i]);
					
				}
			}
			
			//separating out the alphanumeric characters from the string (removing dashes)
			char[] str1 = new char[100];
			for(i=0,j=0;i<str.length;i++)
			{
				if(str[i]!='-')
				{
					str1[j] = str[i];
					j++;
				}
			}
			
			int len_str1 = j-1;   //length of str1
			
			//formatting the keys according to the integer k (grouping the characters and appending to the res array)
			i=0;
			j=0;
			while(i<=len_str1)
			{
				count = k;
				while(count>0)
				{
					res[j] = str1[i];
					i++;
					j++;
					count--;
				}
				if(i>=len_str1)
					continue;
				else
				{
					res[j] = '-';
					j++;
				}
			}
			
			System.out.print("License: ");
			System.out.print(res);
		}
}
