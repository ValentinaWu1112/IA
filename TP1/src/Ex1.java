import java.util.Random;
import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        Random ran = new Random();
        int n = in.nextInt();
        int m = in.nextInt();

        Coordenadas v[] = new Coordenadas[n];

        int index = 0;
        while(index<n){
            int a = ran.nextInt()%(m+1);
            int b = ran.nextInt()%(m+1);
            if(!contem(v,new Coordenadas(a,b),n)){
                v[index] = new Coordenadas(a, b);
                index++;
            }
        }

        for(int k=0; k<n; k++){
            v[k].imprimir();
        }
    }

    public static boolean contem (Coordenadas v[], Coordenadas a, int n){
        for(int i=0; i<n;i++){
            if(v[i]!=null)
                if(v[i].comparar(a)) return true;
        }
        return false;
    }
}
