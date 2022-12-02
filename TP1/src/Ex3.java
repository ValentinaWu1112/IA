import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Ex3 {
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
            if(!contem(v,new Coordenadas(a,b),n)) {
                v[index] = new Coordenadas(a, b);
                index++;
            }
        }

        Graph g = new Graph(n, v);

        int first = ran.nextInt(n);
        int pi = first;
        int verificador=1;
        while(verificador<n){
            int ps = ran.nextInt(n);
            if(!g.visited[ps] && first!=ps){
                g.addPoint(first, ps);
                first=ps;
                verificador++;
            }
        }
        g.addPoint(first, pi);
        g.imprimir(pi);

        System.out.println("Intersecoes: ");
        LinkedList<Intersect> r = g.lista_intersetados();

        for(Intersect a: r){
            v[a.p1_x].imprimir();
            System.out.print(" ");
            v[a.p1_y].imprimir();
            System.out.print(" interseta ");
            v[a.p2_x].imprimir();
            v[a.p2_y].imprimir();
            System.out.println();
        }

        Graph candidato[] = new Graph[r.size()];

        for(int i=0; i<r.size(); i++){
            int a = r.get(i).p1_x;
            int b = r.get(i).p1_y;
            int c = r.get(i).p2_x;
            int d = r.get(i).p2_y;
            candidato[i] = g.novo();
            candidato[i].two_exchange(a,b,c,d);
        }

        for(int i=0; i<r.size(); i++){
            System.out.println("Candidato: ");
            candidato[i].imprimir(0);
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
