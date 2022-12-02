import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Ex4a {
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


        System.out.println("Poligno inicial: ");
        g.imprimir(0);

        while (!g.poligno_simples()) {
            System.out.println("Poligno original: ");
            g.imprimir(0);
            LinkedList<Intersect> r = g.lista_intersetados();

            Graph candidato[] = new Graph[r.size()];

            for (int i = 0; i < r.size(); i++) {
                int a = r.get(i).p1_x;
                int b = r.get(i).p1_y;
                int c = r.get(i).p2_x;
                int d = r.get(i).p2_y;
                System.out.println(a + " " + b + " " + c + " " + d);
                candidato[i] = g.novo();
                candidato[i].two_exchange(a, b, c, d);
            }

            int cand = escolha_candidato(candidato, r.size());
            System.out.println("Candidato: " + cand);
            g=candidato[cand].novo();
        }
        System.out.println("Poligno final: ");
        g.imprimir(0);
    }

    public static int escolha_candidato(Graph[] candidato, int n){
        int result=0;
        double min = Integer.MAX_VALUE;
        for(int i=0; i<n; i++){
            double perimetro = candidato[i].perimetro();
            if(perimetro<min){
                min=perimetro;
                result=i;
            }
        }
        return result;
    }

    public static boolean contem (Coordenadas v[], Coordenadas a, int n){
        for(int i=0; i<n;i++){
            if(v[i]!=null)
                if(v[i].comparar(a)) return true;
        }
        return false;
    }
}
