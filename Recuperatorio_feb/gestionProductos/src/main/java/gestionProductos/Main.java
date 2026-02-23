package gestionProductos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws IOException{
		File archivo = new File("inventario.csv");
		crearArchivo();
		menu();
	}
	public static void menu() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.printf("=====MENU=====\n");
			System.out.printf("1. Ingresar productos\n");
			System.out.printf("2. Mostrar productos\n");
			System.out.printf("3. Comprar o vender producto\n");
			System.out.printf("4. Buscar por marca\n");
			System.out.printf("5. Salir\n");
			System.out.printf("Seleccione una opcion: ");
			
			String opcion = br.readLine();
			
			switch (opcion) {
			case "1":
				ingresar(br);
				break;
			case "2":
				mostrar();
				break;
			case "3":
				comprarOVender();
				break;
			case "4":
				buscarPorMarca();
			case "5":
				System.out.printf("Saliendo...");
				return;
			default:
				System.out.print("Opcion invalida\n");
				break;
			}
		}
	}
	public static void crearArchivo() throws IOException {
		File archivo = new File("inventario.csv");
		
		if(!archivo.exists()) {
			System.out.print("Archivo no existe\n");
		} 
		
		
	}
	public static void ingresar(BufferedReader br) throws IOException{
		BufferedReader lector = new BufferedReader(new FileReader("inventario.csv"));
		FileWriter archivo = new FileWriter("inventario.csv");
		ArrayList<String> lineas = new ArrayList<>();
		String linea = br.readLine();
		
		while((linea = lector.readLine())!= null) {
			for(int i = 0; i < lineas.size() ; i++) {
				archivo.append(linea);	
			}
		}
		lector.close();
		
		ArrayList<String> producto = new ArrayList<>();
	
		
	}
	public static void mostrar() throws IOException{
		BufferedReader br = new BufferedReader (new FileReader("inventario.csv"));
		FileWriter fw = new FileWriter("Inventario.csv");
		String linea;
		while ((linea = br.readLine()) != null) {
			fw.write(linea);
		}
		
		br.close();
		fw.close();
	}
	public static void comprarOVender() throws IOException{
	
}
	public static void buscarPorMarca() throws IOException{
	
}
	
}
