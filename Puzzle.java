import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Puzzle {

	private int[][] matriz;
	private String md5;
	
	public Puzzle(int[][] matriz) {
		this.matriz = matriz;
	}
	
	
	public Puzzle() {
		this.matriz = new int[3][3];
		reiniciar();
	}
	
	public void reiniciar() {
		for(int i = 0; i < this.matriz.length; i++)
			for(int j = 0; j < this.matriz.length; j++)
				this.matriz[i][j] = 0;
	}
	
	public static void reiniciar(int[][] matriz) {
		for(int i = 0; i < matriz.length; i++)
			for(int j = 0; j < matriz.length; j++)
				matriz[i][j] = 0;
	}
	
	public void change(int row, int column) {
		//System.out.println(row + "-" + column +"\n");
		pushBelt(this.matriz);
		//System.out.println("Push matriz");
		//mostrarMatriz(this.matriz);
		this.matriz[row+1][column+1] = (this.matriz[row+1][column+1] == 1)? 0 : 1;
		
		this.matriz[row][column+1] = (this.matriz[row][column+1] == 1)? 0 : 1;
		this.matriz[row+2][column+1] = (this.matriz[row+2][column+1] == 1)? 0 : 1;
		
		this.matriz[row+1][column] = (this.matriz[row+1][column] == 1)? 0 : 1;
		this.matriz[row+1][column+2] = (this.matriz[row+1][column+2] == 1)? 0 : 1;
		pullBelt(this.matriz);
		//System.out.println("Pull matriz");
		//mostrarMatriz(this.matriz);


	}
	

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}
	
	public String getMd5() {
		updatemd5();
		return md5;
	}
	
	public void updatemd5() {
		try {
			this.md5 = generateMD5(face(matriz));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static String generateMD5(String id) throws NoSuchAlgorithmException {

		String IdMD5;

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(id.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		IdMD5 = sb.toString();

		return IdMD5;
	}
	
	public void pushBelt(int[][] matriz) {
		int[][] belt = new int[matriz.length+2][matriz.length+2];
		reiniciar(belt);
		for(int i = 1; i < belt.length-1; i++)
			for(int j = 1; j < belt.length-1; j++) 
				belt[i][j] = matriz[i-1][j-1];
			
		
		this.matriz = belt;
	}
	
	public void pullBelt(int[][] belt) {
		int[][] matriz = new int[belt.length-2][belt.length-2];
		for(int i = 0; i < matriz.length; i++)
			for(int j = 0; j < matriz.length; j++)
				matriz[i][j] = belt[i+1][j+1];
		this.matriz = matriz;
	}

	public String face(int[][] m) {
		String txt = "";
		for(int i = 0; i < m.length; i++)
			for(int j = 0; j < m.length; j++)
				txt+=m[i][j];
		return txt;
	}

	public static void mostrarMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			  for (int j=0; j < matriz.length; j++) 
				  System.out.print(matriz[i][j]+ " ");
			  System.out.println("");
		}
	}
	
}


