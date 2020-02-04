

import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Estado {
	
	private Puzzle puzzle;
	private String accion;
	
	public Estado(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public Estado(Puzzle puzzle, String accion) {
		this.puzzle = puzzle;
		this.accion = accion;
	}

	
	public static String escribirJSON(Puzzle puzzle) throws java.io.IOException {
		Gson gson = new Gson();
		//System.out.print(System.getProperties().getProperty("user.dir")+"\\puzzleWritten.json");
		FileWriter file = new FileWriter(System.getProperties().getProperty("user.dir")+"\\puzzleWritten.json");
		file.write(gson.toJson(puzzle));
		file.close();
	 
	 	return gson.toJson(puzzle).toString();
	 	//System.out.print("\n"+gson.toJson(puzzle));
	}
	
	//Sucesores
	public static ArrayList<Estado> sucesores(Estado estado) {
				
		ArrayList<Estado> list = new ArrayList<Estado>();
		Puzzle puzzle = new Puzzle(copiarMatriz(estado.getPuzzle().getMatriz()));
		for(int i = 0; i < estado.getPuzzle().getMatriz().length; i++) 
			for(int j = 0; j < estado.getPuzzle().getMatriz().length; j++) 
				if(i != 1 || j  != 1) {
					puzzle = new Puzzle(copiarMatriz(estado.getPuzzle().getMatriz()));
					puzzle.change(i, j);
					list.add(new Estado(puzzle, i + "-" + j));
				}
		return list;
	}
	

	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

		
		
	//Auxiliar Methods
		
	public static int[][] traspuesta(int matriz[][], Boolean b) {
		if(b)
			return giro(matriz);
		else 
			return giro(giro(giro(matriz)));
	}
			
	public static int[][] giro(int matriz[][]) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		
		for(int i = 0, j = matriz.length - 1; i < matriz.length && j >= 0; i++, j--)
	        for(int k = 0; k < matriz.length; k++)
	            rotacion[k][j] = matriz[i][k]; 
		
		return rotacion;
	}
	
	public static void copiarPuzzle(Puzzle src, Puzzle dst) {
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	public static void mostrarMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			  for (int j=0; j < matriz.length; j++) 
				  System.out.print(matriz[i][j]+ " ");
			  System.out.println("");
		}
	}
		
	public static int[][] copiarMatriz(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++)
			  for (int j=0; j < matriz.length; j++) 
				  rotacion[i][j]=matriz[i][j];
		return rotacion;
	}
		
	////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
