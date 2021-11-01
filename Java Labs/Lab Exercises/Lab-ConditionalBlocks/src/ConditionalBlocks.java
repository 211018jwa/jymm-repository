
public class ConditionalBlocks {

	public static void main(String[] args) {
		// boolean b = false; //Lab #7
		// boolean b = true; //Lab #7

		//boolean firstCondition = true; // Lab #8
		boolean firstCondition = false; // Lab #8
		boolean secondCondition = true; // Lab #8

		if (firstCondition) { // Lab #7 ----> added firstCondition for Lab #8
			System.out.println("inside the if-statement"); // Lab #7
			
			if (secondCondition) { // Lab #8
				System.out.println("inside the nested if-statement"); // Lab #8
			}
		}else if (secondCondition) { // Lab #8
			System.out.println(5); // Lab #8
		} else { // Lab #7 ----> added else {} for Lab #8
			System.out.println("inside of the else-statement"); // Lab #8
		}
		
		System.out.println("Outside of the if-statement");  // Lab #7

	}

}
