
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Problema {
	
	public static Scanner TC = new Scanner(System.in);
	
	public enum Estrategia{
		Profundidad, Anchura, CosteUniforme, Greddy, A
	}
	private static int id;
	
	public Problema() {
		id=0;
	}
	
	public static void generadorBase(int tamano, ArrayList<Puzzle> todas) {
		
		id=0;
		Estado estadoInicial = new Estado(new Puzzle(MatrizInicial(tamano)));
		
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>();

		Nodo inc = new Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial));

		System.out.println(inc.toString3()+"\n");
		ArrayList<String> visitados = new ArrayList<String>();
		ArrayList<Estado> ls = new ArrayList<Estado>();
		ArrayList<Nodo> ln = new ArrayList<Nodo>();

		frontera.add(inc);

		Nodo actual = null;
		while(!frontera.isEmpty() && todas.size() < 100) {
			if(!visitados.contains(((actual=frontera.remove()).getEstado().getPuzzle().getMd5()))) {
				if(EsObjetivo(actual.getEstado().getPuzzle())) {
					AddGirados(actual, todas, visitados);
					mostrarMatriz(actual.getEstado().getPuzzle().getMatriz());
				}
					//todas.add(actual.getEstado().getPuzzle());
				ls = Estado.sucesores(actual.getEstado());
				ln = creaListaNodo(ls, actual, actual.getD(), Estrategia.Profundidad);
				for(Nodo n : ln) 
					frontera.add(n);
				visitados.add(actual.getEstado().getPuzzle().getMd5());
			}
		}

	}
	
	public static void busquedaAcotada(int profMax, Estado estadoInicial, Estrategia estrategia) {
		
		id=0;
		
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>();

		Nodo inc;
		if(estrategia == Estrategia.A)
			inc = new Nodo(estadoInicial,0,"none",0,null,Heuristica(estadoInicial),id,Heuristica(estadoInicial));
		else if( estrategia == Estrategia.Profundidad)
			inc = new Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial));
		else
			inc = new Nodo(estadoInicial,0,"none",0,null,0,id,Heuristica(estadoInicial));
		mostrarMatriz(inc.getEstado().getPuzzle().getMatriz());
		ArrayList<String> visitados = new ArrayList<String>();
		ArrayList<Estado> ls = Estado.sucesores(inc.getEstado());
		ArrayList<Nodo> ln = creaListaNodo(ls, inc, inc.getD(), estrategia);

		for(Nodo n : ln) {
			frontera.add(n);
			//mostrarMatriz(n.getEstado().getPuzzle().getMatriz());
		}

		Nodo actual = null;
		while(!frontera.isEmpty() && !EsObjetivo((actual=frontera.poll()).getEstado().getPuzzle())) {
			if(actual.getD()<profMax && !visitados.contains((actual.getEstado().getPuzzle().getMd5()))) {
				ls = Estado.sucesores(actual.getEstado());
				ln = creaListaNodo(ls, actual, actual.getD(), estrategia);
				for(Nodo n : ln) 
					frontera.add(n);
				visitados.add(actual.getEstado().getPuzzle().getMd5());
			}
		}
		if(actual!=null && EsObjetivo(actual.getEstado().getPuzzle())) {
			System.out.println("LAST:");
			System.out.println(actual.toString());
			mostrarMatriz(actual.getEstado().getPuzzle().getMatriz());
				//generarSolucion(actual);
		}else
			System.out.print("SIN SOLUCION");

	}
	
	/*
	 * public static void busquedaAcotada(int profMax, Estado estadoInicial,
	 * Estrategia estrategia) {
	 * 
	 * id=0;
	 * 
	 * PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>();
	 * 
	 * Nodo inc; if(estrategia == Estrategia.A) inc = new
	 * Nodo(estadoInicial,0,"none",0,null,Heuristica(estadoInicial),id,Heuristica(
	 * estadoInicial)); else if( estrategia == Estrategia.Profundidad) inc = new
	 * Nodo(estadoInicial,0,"none",0,null,1,id,Heuristica(estadoInicial)); else inc
	 * = new Nodo(estadoInicial,0,"none",0,null,0,id,Heuristica(estadoInicial));
	 * System.out.println(inc.toString3()+"\n"); ArrayList<String> visitados = new
	 * ArrayList<String>(); ArrayList<Estado> ls =
	 * Estado.sucesores(inc.getEstado()); ArrayList<Nodo> ln = creaListaNodo(ls,
	 * inc, inc.getD(), estrategia);
	 * 
	 * for(Nodo n : ln) { frontera.add(n);
	 * //mostrarMatriz(n.getEstado().getPuzzle().getMatriz()); }
	 * 
	 * Nodo actual = null; while(!frontera.isEmpty() &&
	 * !EsObjetivo((actual=frontera.poll()).getEstado().getPuzzle())) {
	 * System.out.println(actual.toString());
	 * mostrarMatriz(actual.getEstado().getPuzzle().getMatriz());
	 * System.out.println(); if(actual.getD()<profMax &&
	 * !visitados.contains((actual.getEstado().getPuzzle().getMd5()))) { ls =
	 * Estado.sucesores(actual.getEstado()); ln = creaListaNodo(ls, actual,
	 * actual.getD(), estrategia); //System.out.println("ENTREEE"+ ln.toString() +
	 * actual.getEstado().getPuzzle().getEmptyNode()); for(Nodo n : ln)
	 * frontera.add(n); visitados.add(actual.getEstado().getPuzzle().getMd5()); } }
	 * if(actual!=null) if(EsObjetivo(actual.getEstado().getPuzzle())) {
	 * mostrarMatriz(actual.getEstado().getPuzzle().getMatriz());
	 * generarSolucion(actual); } else System.out.print("SIN SOLUCION");
	 * 
	 * }
	 */
	
	public static ArrayList<Nodo> creaListaNodo(ArrayList<Estado> estados, Nodo actual, double prof, Estrategia estrategia) {
			
			ArrayList<Nodo> list = new ArrayList<Nodo>();	
			
			for(Estado e : estados) {
				id+=1;

				switch(estrategia) {
					case Profundidad:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, 1/(prof+2), id, Heuristica(e)));
						break;
					case Anchura:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual,prof+1, id, Heuristica(e)));
						break;
					case CosteUniforme:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual,actual.getCosto()+1, id, Heuristica(e)));
						break;
					case Greddy:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, Heuristica(e), id, Heuristica(e)));
						break;
					case A:
						list.add(new Nodo(e, actual.getCosto()+1,e.getAccion(), prof+1, actual, Heuristica(e)+actual.getCosto()+1, id, Heuristica(e)));
						break;
					default:
						System.out.println("DEFAULT");
						break;
				}
			}	
			return list;
		}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	public static double Heuristica(Estado e) {
//		return ((double)Math.round(Math.abs(Entropia(e.getCube().getBack())+Entropia(e.getCube().getFront())+Entropia(e.getCube().getUp())+
//				Entropia(e.getCube().getDown())+Entropia(e.getCube().getLeft())+Entropia(e.getCube().getRight()))*100))/100;
		return Contador(e);
	}
	
	
	public static int Contador(Estado e) {
		int num = 0;
		for(int i = 0; i < e.getPuzzle().getMatriz().length; i++) 
			for(int k = 0; k < e.getPuzzle().getMatriz().length; k++) {
				if(i != 1 && k != 1 && e.getPuzzle().getMatriz()[i][k] == 0)
					num += 1;
					
			}	
		return num;
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	public static boolean EsObjetivo(Puzzle puzzle) {		
		for(int i = 0; i < puzzle.getMatriz().length; i++) 
			for(int k = 0; k < puzzle.getMatriz().length; k++) 
				if(i != 1 && k != 1 && puzzle.getMatriz()[i][k] == 0)
					return false;
					
		
		return true;
		
	}

	public static void AddGirados(Nodo actual, ArrayList<Puzzle> puzzles, ArrayList<String> visitados) {
		
		Puzzle puzzle = new Puzzle(copiarMatriz(actual.getEstado().getPuzzle().getMatriz()));
		for(int i = 0; i<3; i++) {
			puzzle = new Puzzle(traspuesta(puzzle.getMatriz()));
			mostrarMatriz(puzzle.getMatriz());
			visitados.add(puzzle.getMd5());
			puzzles.add(new Puzzle(copiarMatriz(puzzle.getMatriz())));
		}

	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	public static int[][] MatrizInicial(int tam){
		int[][] m = new int[tam][tam];
		for (int i = 0; i < m.length; i++) 
			  for (int j=0; j < m.length; j++) 
				  m[i][j]=0;
		return m;
	}
	
	public static void generarSolucion(Nodo actual) {
		Stack<String> st = new Stack<String>();	
		while(actual.getPadre() != null) {
			st.add(actual.toString3());
			actual = actual.getPadre();
		}
		st.add(actual.toString());
		while(!st.isEmpty()) {
			System.out.println(st.pop());
		}
		System.out.println("\n");
	}
	
	public static void mostrar(Nodo[] nodos) {
		for (int i = 0; i < nodos.length; i++)
			System.out.println("\t" + nodos[i].toString3());
	}
	
	public static void mostrarMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			  for (int j=0; j < matriz.length; j++) 
				  System.out.print(matriz[i][j]+ " ");
			  System.out.println("");
		}
		System.out.println("\n");
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
	
	public static int[][] traspuesta(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];

		for(int i = 0, j = matriz.length - 1; i < matriz.length && j >= 0; i++, j--)
	        for(int k = 0; k < matriz.length; k++)
	            rotacion[k][j] = matriz[i][k]; 
		
		return rotacion;
	}
	
	public static int[][] copiarMatriz(int[][] matriz) {
		int[][] rotacion = new int[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++)
			  for (int j=0; j < matriz.length; j++) 
				  rotacion[i][j]=matriz[i][j];
		return rotacion;
	}
	
}
