package models.consola;

import java.util.Scanner;

public class Consola {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("/*** Consola de prueba ***/\n");
		System.out.println("a) Opcion a\nb) opcion b\nc)opcion c\nSalir\n");
		
		while (true) {
			System.out.print("Elija una de las opciones:\t");
			String selection = scanner.nextLine();
		
			if (selection.equalsIgnoreCase("salir")) {
				System.out.println("\nAdios!\n");
				break;
			} else if (selection.equalsIgnoreCase("a")) {
				System.out.println("\nElegiste la opcion a, felicitaciones!\n");
			} else if (selection.equalsIgnoreCase("b")) {
				System.out.println("\nElegiste la opcion b, felicitaciones!\n");
			} else if (selection.equalsIgnoreCase("c")) {
				System.out.println("\nElegiste la opcion c, felicitaciones!\n");
			}
		}
		
		scanner.close();
		System.exit(0);
	}
}
