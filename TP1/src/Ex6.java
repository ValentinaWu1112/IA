import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math.*;

public class Ex6 {
    static double [][] pheromone;
    static double [] cost;
    static double [][] distancia;
    static double [][] probability;
    static boolean[] visited;
    static Graph[] formigas;
    static int n;
    static int ant;
    static int tmax;
    static double alfa = 3.5;
    static double beta = 1.3;
    static double Q = 1.4;

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        Random random = new Random();
        n = in.nextInt();
        int m = in.nextInt();

        Coordenadas v[] = new Coordenadas[n];

        int index = 0;
        while(index<n){
            int a = random.nextInt()%(m+1);
            int b = random.nextInt()%(m+1);
            if(!contem(v,new Coordenadas(a,b),n)) {
                v[index] = new Coordenadas(a, b);
                index++;
            }
        }

        ant=30;
        tmax=30;

        pheromone = new double[n][n];
        cost = new double[ant];
        distancia = new double[n][n];
        probability = new double[n][n];
        visited = new boolean[n];
        formigas = new Graph[ant];

        //Inicializar
        for(int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                if(i==j){
                    pheromone[i][j]=0;
                    distancia[i][j]=0;
                    probability[i][j]=0;
                }
                else {
                    pheromone[i][j]=1;
                    distancia[i][j]=distance(v[i],v[j]);
                    probability[i][j]=1;
                }
            }
            visited[i]=false;
        }

        for(int i=0; i<ant; i++){
            formigas[i] = new Graph(n,v);
            cost[i]=1;
        }

        //ACO
        for (int t=1; t<=tmax; t++){
            for(int k=0; k<ant; k++){
                int firstnode = random.nextInt(n);
                tour_construct(formigas[k], firstnode);
            }
            compute_cost();
            update_pheromena();
            clean_formigas();
        }

        //Solução
        Graph solution = new Graph(n,v);
        int first=random.nextInt(n);
        int tmp=first;
        int second=0;
        double maximo=0;
        int ind = 0;
        while(ind<n-1){
            for(int i=0; i<n; i++){
                if(!solution.visited[i] && first!=i){
                    if(pheromone[first][i]>maximo){
                        second=i;
                        maximo=pheromone[first][i];
                    }
                }
            }
            solution.addPoint(first,second);
            first=second;
            ind++;
            maximo=0;
        }
        solution.addPoint(first,tmp);

        System.out.println("Solução: ");
        solution.imprimir(0);

    }

    public static double distance(Coordenadas a, Coordenadas b){
        return sqrt(pow((a.x-b.x),2)+pow((a.y-b.y),2));
    }

    public static void tour_construct(Graph k, int node){
        int index=0;
        double[][] num_prob = new double[n][n];
        LinkedList<Integer> nos = new LinkedList<>();
        int choosenode=0;
        double maxvalue=0;
        while(index<n){
            for(int p=0; p<n; p++){
                if(!k.visited[p] && node!=p){
                    num_prob[node][p]=calculate_num(node,p);
                    nos.add(p);
                }
            }
            for(int j=0; j<nos.size(); j++){
                int link = nos.get(j);
                probability[node][link]=num_prob[node][link]/den(num_prob, nos,node, link);
            }
            for(int i=0; i<nos.size(); i++){
                int j = nos.get(i);
                if(probability[node][j]>maxvalue){
                    maxvalue=probability[node][j];
                    choosenode=j;
                }
            }
            k.addPoint(node,choosenode);
            node=choosenode;
            index++;
            nos.clear();
            choosenode=0;
            maxvalue=0;
        }
    }

    public static double calculate_num(int i, int j){
        return pow(pheromone[i][j],alfa)*pow((1/distancia[i][j]),beta);
    }

    public static double den(double[][] num, LinkedList<Integer> nos, int i, int j){
        double result = 0;
        for(int k=0; k<nos.size(); k++){
            int l = nos.get(k);
            if(l!=j){
                result+=num[i][l];
            }
        }
        return result;
    }

    public static void compute_cost(){
        for(int i=0; i<ant; i++){
            cost[i]=formigas[i].perimetro();
        }
    }

    public static void update_pheromena(){
        double evaporation = 0.3;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i!=j){
                    pheromone[i][j]=(1.0-evaporation)*pheromone[i][j]+evaporation*somatorio_delta(i,j);
                }
            }
        }
    }

    public static double somatorio_delta(int i, int j){
        double result=0;
        for(int a=0; a<ant; a++){
            if(formigas[a].adj[i][j]){
                result+=Q/cost[a];
            }
        }
        return result;
    }

    public static void clean_formigas(){
        for(int k=0; k<ant; k++){
            formigas[k].clean();
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
