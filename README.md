### Number_system_converter_with_periodic_representation

Converts a rational number from an arbitrary number system to the inputted number system. Parameters are:
[number] [numbersystem of the input number] [numbersystem of the output number] [lengthOfPeriod]
Example input string: 23.15 8 16 1
Output: 13.36DB 3

Explanation: 
23.15 (base 8) is equivalent to 23.15555..., because the parameter "lengthOfPeriod" is the number of repeating digits counting from the last index of the input number. 13.36DB (base 16) is the output number and the 3 represents the length of the repeating digits starting from the last index, therefore the output with this notation is equivalent to 13.36DB6DB6DB6DB...
