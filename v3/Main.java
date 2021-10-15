package horner.v3;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//System.out.println("convertToDecimal number numbersystem lengthBeforePeriod lengthOfPeriod");
		System.out.println("[number] [numbersystem of the input number] [numbersystem of the output number] [lengthOfPeriod]");
		Scanner scan = new Scanner(System.in);
		String[] data = scan.nextLine().split(" ");
		Numbersystem n1 = new Numbersystem(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
	}
}
