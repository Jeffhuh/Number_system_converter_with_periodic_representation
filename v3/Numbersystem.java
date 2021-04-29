package horner.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Numbersystem {
	String number;
	String numberAfterConverting;
	int inputNumbersystem;
	int outputNumbersystem;
	int lengthOfPeriod;
	int lengthOfPeriodAfterConverting;
	String result;
	int[] values = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
	
	public Numbersystem(String number, int inputNumbersystem, int outputNumbersystem,int lengthOfPeriod) {
		this.number = number; 
		this.inputNumbersystem = inputNumbersystem;
		this.outputNumbersystem = outputNumbersystem;
		this.lengthOfPeriod = lengthOfPeriod;
		numberAfterConverting = toDecimal();
		//System.out.println(numberAfterConverting);
		result = toNumbersystem();
		System.out.println(result);
		System.out.println(lengthOfPeriodAfterConverting);
	}
	
	public String toDecimal() {
		String[] d = number.split("\\.");
		String decimalNumber = String.valueOf(horner(d[0], inputNumbersystem));
		if (d.length == 2) {
			int lengthBeforePeriod = d[1].length() - lengthOfPeriod, numr, denr; 
			if (lengthOfPeriod == 0) {
				numr = horner(d[1], inputNumbersystem);
				denr = (int) Math.pow(inputNumbersystem, lengthBeforePeriod);
			} else {
				numr = (horner(d[1], inputNumbersystem) - horner(lengthBeforePeriod == 0 ? "0" : d[1].substring(0, lengthBeforePeriod), inputNumbersystem));
				denr = (int) (Math.pow(inputNumbersystem, d[1].length())-Math.pow(inputNumbersystem, lengthBeforePeriod));
			}
			if (numr == denr) {
				decimalNumber = String.valueOf(Integer.parseInt(decimalNumber)+1);
			} else {
				decimalNumber += ("."+division(numr, denr, 10));
			}
		}
		return decimalNumber;
	}
	
	public String toNumbersystem() {
		String[] d = numberAfterConverting.split("\\.");
		String n = "";
		int div = 0, rest = 0, numberBase10 = Integer.parseInt(d[0]);
		String r = "";
		do {
			rest = numberBase10 % outputNumbersystem;
			div = numberBase10 / outputNumbersystem;
			numberBase10 = div;
			 if (rest > 9) {
				 r = Character.toString((char)(rest+55));
			 } else {
				 r = String.valueOf(rest);
			 }
			n = r + n;
		} while (div != 0);
		if (d.length == 2) {
			int dLength = d[1].length(), lengthBeforePeriod = dLength - lengthOfPeriodAfterConverting;
			long numr, denr;
			if (lengthOfPeriodAfterConverting == 0) {
				numr = Long.parseLong(d[1]);
				denr = pow(10, dLength);
			} else {
				numr = Long.parseLong(d[1]) - Long.parseLong(lengthBeforePeriod == 0 ? "0": String.valueOf(d[1]).substring(0, lengthBeforePeriod));
				denr = pow(10, dLength)-pow(10, lengthBeforePeriod);
				//System.out.println(Math.pow(10, dLength)-Math.pow(10, lengthBeforePeriod));
			}
			n += ("."+division(numr, denr, outputNumbersystem));
		} else {
			n += ".0";
		}
		return n;
	}
	
	public long pow(int a, int b) {
		long r = 1;
		for (int i = 0; i < b; i++) {
			r *= a;
		}
		return r;
	}
	
	public int horner(String naturalNumber, int numbersystem) {
		int decimalNumber = 0;
		for (int i = 0; i < naturalNumber.length(); i++) {
			char c = naturalNumber.charAt(naturalNumber.length() - 1 - i);
			int digit = 0;
			if (Character.isDigit(c)) {
				digit = c - 48;
			} else {
				digit = values[c - 65];
			}
			decimalNumber += (int)Math.pow(numbersystem, i) * digit;
		}
		return decimalNumber;
	}
	
	public String division(long numr, long denr, int numbersystem) {
		String comma = "";
		ArrayList<Long> mp = new ArrayList<>();
		mp.clear();
		//System.out.println(numr +" "+denr);
		long rem = numr;
		while(rem != 0 && !mp.contains(rem)) {
			mp.add(rem);
			rem *= numbersystem;
			
			long zahl = rem / denr;
			if (zahl > 9) {
				System.out.println(zahl);
				comma += Character.toString((char)((int)zahl+55));
			} else {
				comma += String.valueOf(zahl);
			}
			//System.out.print(zahl+" ");
			rem %= denr;
			//System.out.println(rem);
		}
		if (mp.contains(rem)) {
			lengthOfPeriodAfterConverting = (mp.size()-mp.indexOf(rem));
			/*for (int i = 0; i < mp.size(); i++) {
				System.out.println(i +"\t"+mp.get(i));
				if (i == mp.size()-1) {
					System.out.println((i+1)+"\t"+rem+"\nLengthOfPeriodAfterConverting: "+lengthOfPeriodAfterConverting);
				}
			}*/
		}
		//if (comma.length() == 0) comma = "0";
		/*System.out.println("comma: "+comma+"\tcomma.length(): "+comma.length());
		System.out.println();*/
		return comma;
	}
	
	/////////////////////////////////////////////////////////////////////////
	/*
	public String decimalToNumbersystem() {
		int[] d = Stream.of(number.split("\\.")).mapToInt(Integer::parseInt).toArray();
		String numberBase = convertToNumbersystemNaturalNumber(d[0]);
		if (d.length == 2) {
			numberBase += ".";
			if (lengthOfPeriod == 0) {
				numberBase += commaNumbersystemWithoutPeriod(d[1]);
			} else {
				numberBase += commaNumbersystemWithPeriod(d[1]);
			}
		} else {
			numberBase += ".0";
		}
		return numberBase;
	}
	
	public String commaNumbersystemWithPeriod(int base10) {
		int dividend, divisor; 
		if (lengthBeforePeriod == 0) {
			dividend = Integer.parseInt(String.valueOf(base10).substring(0, lengthOfPeriod));
			divisor = (int)Math.pow(10, lengthOfPeriod)-1;
		} else {
			dividend = Integer.parseInt(String.valueOf(base10).substring(0, lengthOfPeriod+lengthBeforePeriod)) - Integer.parseInt(String.valueOf(base10).substring(0, lengthBeforePeriod));
			divisor = (int)Math.pow(10, lengthOfPeriod+lengthBeforePeriod)-(int)Math.pow(10, lengthBeforePeriod);
		}
		return division(dividend, divisor);
	}
	
	public String commaNumbersystemWithoutPeriod(int base10) {
		int dividend = base10;
		int divisor = (int)Math.pow(10, String.valueOf(base10).length());
		return division(dividend, divisor);
	}
	
	public double convertToDecimal() {
		String[] d = number.split("\\.");
		double decimalNumber = convertToDecimalNaturalNumber(d[0]);
		if (d.length == 2) {
			if (lengthOfPeriod == 0) {
				decimalNumber += commaNumberWithoutPeriod(d[1]);
			} else {
				decimalNumber += commaNumberWithPeriod(d[1]);
			}
		}
		return decimalNumber;
	}
	
	public double commaNumberWithPeriod(String commaPart) {
		int div = 0;
		if (lengthBeforePeriod == 0) {
			commaPart = commaPart.substring(0, lengthOfPeriod);
			div = (int)Math.pow(numbersystem, lengthOfPeriod) - 1;
		} else {
			commaPart = subtraction(commaPart.substring(0, lengthOfPeriod+lengthBeforePeriod),commaPart.substring(0, lengthBeforePeriod));
			div = (int)Math.pow(numbersystem, lengthOfPeriod+lengthBeforePeriod) - (int) Math.pow(numbersystem, lengthBeforePeriod);
		}
		int zahl = convertToDecimalNaturalNumber(commaPart);
		int temp = this.numbersystem; 
		this.numbersystem = 10;
		double d = Double.parseDouble("0."+(division(zahl, div)));
		this.numbersystem = temp;
		return d;
	}
	
	public String convertToNumbersystemNaturalNumber(int numberBase10) {
		String number = "";
		int div = 0, rest = 0;
		String r = "";
		do {
			rest = numberBase10 % numbersystem;
			div = numberBase10 / numbersystem;
			numberBase10 = div;
			 if (rest > 9) {
				 r = Character.toString((char)(rest+55));
			 } else {
				 r = String.valueOf(rest);
			 }
			number = r + number;
		} while (div != 0);
		return number;
	}
	
	public int convertToDecimalNaturalNumber(String naturalNumber) {
		
		int decimalNumber = 0;
		for (int i = 0; i < naturalNumber.length(); i++) {
			char c = naturalNumber.charAt(naturalNumber.length() - 1 - i);
			int digit = 0;
			if (Character.isDigit(c)) {
				digit = c - 48;
			} else {
				digit = values[c - 65];
			}
			decimalNumber += Math.pow(numbersystem, i) * digit;
		}
		return decimalNumber;
	}
	
	public double commaNumberWithoutPeriod(String naturalNumber) {
		double decimalNumber = 0;
		for (int i = 0; i < naturalNumber.length(); i++) {
			char c = naturalNumber.charAt(i);
			int digit = 0;
			if (Character.isDigit(c)) {
				digit = c - 48;
			} else {
				digit = values[c - 65];
			}
			decimalNumber += Math.pow(numbersystem, -i-1) * digit;
		}
		return decimalNumber;
	}
	
	public boolean repeatingNumbers(ArrayList<Integer> a) {
		for (int i = 0; i < a.size()-1; i++) {
			for (int j = i+1; j < a.size();j++) {
				int a1 = a.get(i);
				int a2 = a.get(j);
				if (a1 == a2) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String subtraction(String minuend, String subtrahend) {
		int differenz = convertToDecimalNaturalNumber(minuend) - convertToDecimalNaturalNumber(subtrahend);
		return convertToNumbersystemNaturalNumber(differenz);
	}*/
}
