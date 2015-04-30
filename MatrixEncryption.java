import java.util.Arrays;

public class MatrixEncryption {
	private int[] numbers = new int[74];
	private char[] letters;
	private double[][] multiply = {{41, 31, 9},{36, 49, 25},{2, 21, 18}}; 
	private double[][] inverse = {{(.17664523),(-.18258288),(.1652647)},{(-.2958931),(.35625593),(-.346858)},{(.3255814),(-.39534884),(.4418605)}};
	private double[][] matrix;
	
	public MatrixEncryption()
	{
		for(int i = 0 ; i < 74 ; i++)
			numbers[i] = i;
		letters = "A BCDEFGHIJKLMNOPQRSTUVWXYZa!b@c#d$e%f^g&h*i(j)klmnopqrstuvwxyz.,?:;/-+=_~".toCharArray();
	}
	
	public String Encrypt(String message)
	{
		while(message.length()%3 != 0)
			message += " ";
		char[] changedMessage;
		double[] matrixNumbers = new double[message.length()];
		changedMessage = message.toCharArray();
		for(int i = 0 ; i < changedMessage.length ; i++)
		{
			loop:
			for(int j = 0 ; j < letters.length ; j++)
			{
				if(changedMessage[i] == letters[j])
				{
					matrixNumbers[i] = numbers[j];
					break loop;
				}
			}
		}
		int x = 0;
		matrix = new double[matrixNumbers.length/3][3];
		for(int i = 0 ; i < matrixNumbers.length/3 ; i++)
		{
			for(int j = 0 ; j < 3 ; j++)
			{
				matrix[i][j] = matrixNumbers[x];
				x++;
			}
		}
		return Arrays.deepToString(multiply(matrix,multiply));
	}
	
	public String Decrpyt(String message)
	{
		String[] items = message.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ","").split(",");
		double[][] matrix = new double[items.length/3][3];
		String decryptedMessage = "";
		int x = 0;
		for(int i = 0 ; i < items.length/3 ; i++)
		{
			for(int j = 0 ; j < 3 ; j++)
			{
				matrix[i][j] = Double.parseDouble(items[x]);
				x++;
			}
		}
		matrix = multiply(matrix,inverse);
		for(int i = 0 ; i < matrix.length ; i++)
		{
			for(int k = 0 ; k < 3 ; k++)
			{
				x = (int)matrix[i][k];
				for(int j = 0 ; j < numbers.length ; j++)
				{
					if(x == numbers[j])
					{
						decryptedMessage += letters[j];
					}
				}
			}
		}
		decryptedMessage.trim();
		return decryptedMessage;
	}
	
	private double[][] multiply(double[][] matrix1, double[][] matrix2)
	{
		double[][] newMatrix = new double[matrix1.length][3];
		double x = 0;
		long y = 0;
		for(int i = 0 ; i < matrix1.length; i++)
		{
			for(int k = 0 ; k < 3 ; k++)
			{
				x = 0;
				for(int j = 0 ; j < 3 ; j++)
				{
					x += (matrix1[i][j]*matrix2[j][k]);
				}
				y = Math.round(x);
				newMatrix[i][k] = y;
			}
		}
		return newMatrix;
	}
}
