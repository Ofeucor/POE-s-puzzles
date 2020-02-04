
import java.util.ArrayList;

public class Main {

	static int[][] m1 = {{0,0,1},
			{0,1,1},
			{1,1,1}};
	
	static int[][] m2 = {{0,0,0},
			{1,1,1},
			{1,1,1}};
	
	static int[][] msolved = {{1,1,1},
			{1,0,1},
			{1,1,1}};

	
	  public static void main(String[] args) {
	  
		  Puzzle solucion = new Puzzle(m2);
		  //System.out.println("Matriz Inicial:\n"); 
		  //mostrarMatriz(solucion.getMatriz());
		  		  
		  System.out.println("\n");
		  System.out.println("INICIANDO PROCESO \n");

		  //System.out.println(Problema.EsObjetivo(solucion));
		  //Estado.sucesores(new Estado(solucion));
		  //solucion.change(0, 1);
		  //solucion.change(0, 1);

		  //Problema.generador(9, todas);
		  Problema.busquedaAcotada(10, new Estado(solucion), Problema.Estrategia.A); 
		  //System.out.println("\n\nXOLUXION\n");
		  //mostrarMatriz(solucion.getMatriz());
	  }
	 

	public static void copiarArray(ArrayList<Integer> src, ArrayList<Integer> dst) {
		for (int i : src)
			dst.add(i);
	}

	public static int[][] copiarMatriz(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz.length; j++)
				rotacion[i][j] = matriz[i][j];
		return rotacion;
	}

	public static void mostrarMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++)
				System.out.print(matriz[i][j] + " ");
			System.out.println("");
		}
	}
	
	public static void mostrarMatriz(int[][] matriz, int base) {
		for (int i = 0; i < matriz.length; i++) {
			if(i % base == 0)
				System.out.println();
			for (int j = 0; j < matriz.length; j++) {
				if(j % base == 0)
					System.out.print(" ");
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("");
		}
	}

}
