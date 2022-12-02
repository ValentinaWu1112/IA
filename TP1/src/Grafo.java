import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

class Coordenadas{
    int x;
    int y;

    Coordenadas(int a, int b){
        x=a;
        y=b;
    }

    public void imprimir (){
        System.out.print("("+ x + "," + y + ")" );
    }

    public boolean comparar(Coordenadas a){
        if((a.x==x && a.y==y) || (a.x==y && a.y==x)) return true;
        return false;
    }
}

class Intersect{
    int p1_x;
    int p1_y;
    int p2_x;
    int p2_y;

    Intersect(int a, int b, int c, int d){
        p1_x=a;
        p1_y=b;
        p2_x=c;
        p2_y=d;
    }

}

class Graph{
    int n;
    Coordenadas[] nodes;
    boolean[][] adj;
    boolean[] visited;

    Graph(int n, Coordenadas v[]){
        this.n=n;
        nodes=v;
        adj=new boolean[n][n];
        visited=new boolean[n];
        for(int i=0; i<n; i++){
            visited[i]=false;
            for(int j=0; j<n; j++){
                adj[i][j]=false;
            }
        }
    }

    public void addPoint(int a, int b){
        adj[a][b]=true;
        adj[b][a]=true;
        visited[a]=true;
    }

    public void removePoint(int a, int b){
        adj[a][b]=false;
        adj[b][a]=false;
    }

    public int NNFirst(int a){
        int res = 0; //nó a returnar
        double min = Integer.MAX_VALUE;
        for(int i=0; i<n; i++){
            if(!visited[i] && a!=i){
                double d = distancia(nodes[a],nodes[i]);
                if (d < min) {
                    min=d;
                    res=i;
                }
            }
        }
        return res;
    }

    public double distancia(Coordenadas a, Coordenadas b){
        return sqrt(pow((a.x-b.x),2)+pow((a.y-b.y),2));
    }

    public void imprimir(int first){
        for(int i=0; i<n; i++){
            if(first==i) System.out.print("*");
            nodes[i].imprimir();
            System.out.print(" -> ");
            for(int j=0; j<n; j++){
                if(adj[i][j]) nodes[j].imprimir();
            }
            System.out.println();
        }
    }

    public boolean interseta(Coordenadas p1, Coordenadas p2, Coordenadas p3, Coordenadas p4){
        double d123 = calculo(p3,p1,p2); //(p3-p1)x(p2-p1)
        double d124 = calculo(p4,p1,p2); //(p4-p1)x(p2-p1)
        double d341 = calculo(p1,p3,p4); //(p1-p3)x(p4-p3)
        double d342 = calculo(p2,p3,p4); //(p2-p3)x(p4-p3)

        if((d123*d124)<0 && (d341*d342)<0) return true;
        else if (d123==0 && IN_BOX(p1,p2,p3)) return true;
        else if (d124==0 && IN_BOX(p1,p2,p4)) return true;
        else if (d341==0 && IN_BOX(p3,p4,p1)) return true;
        else if (d342==0 && IN_BOX(p3,p4,p2)) return true;
        return false;
    }

    public double calculo(Coordenadas a, Coordenadas b, Coordenadas c){ //(a-b)(c-b)
        double res=(a.x-b.x)*(c.y-b.y) - (c.x-b.x)*(a.y-b.y);
        return res;
    }

    public boolean IN_BOX(Coordenadas p1, Coordenadas p2, Coordenadas p3){
        return (Math.min(p1.x,p2.x)<=p3.x && p3.x<=Math.max(p1.x,p2.x)) && (Math.min(p1.y,p2.y)<=p3.y && p3.y<=Math.max(p1.y, p2.y));
    }

    public LinkedList<Intersect> lista_intersetados() {
        LinkedList<Intersect> result = new LinkedList<>();

        boolean[][] verficar_dentro = new boolean[n][n];
        boolean[][] visitado_permanente = new boolean[n][n];

        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                verficar_dentro[i][j]=false;
                visitado_permanente[i][j]=false;
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(adj[i][j]){
                    int first_node=i;
                    int first_link=j;
                    if(!visitado_permanente[first_node][first_link]){
                        for(int k=0; k<n; k++){
                            for(int l=0; l<n; l++){
                                if(adj[k][l]){
                                    int second_node=k;
                                    int second_link=l;
                                    if (first_node == second_node || first_node == second_link || first_link == second_node || first_link == second_link) {
                                        continue;
                                    }
                                    else{
                                        if(!verficar_dentro[second_node][second_link] && !visitado_permanente[second_node][second_link]){
                                            if(interseta(nodes[first_node],nodes[first_link],nodes[second_node],nodes[second_link])){
                                                result.add(new Intersect(first_node,first_link,second_node,second_link));
                                                verficar_dentro[second_node][second_link]=true;
                                                verficar_dentro[second_link][second_node]=true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    visitado_permanente[first_link][first_node]=true;
                    visitado_permanente[first_node][first_link]=true;
                    for(int p=0; p<n; p++)
                        for(int o=0; o<n; o++)
                            verficar_dentro[p][o] = false;
                }
            }
        }
        return result;
    }

    public Graph novo(){
        Graph novo = new Graph(n,nodes);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                novo.adj[i][j]=adj[i][j];
            }
        }
        return novo;
    }

    public void clean(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                adj[i][j]=false;
            }
            visited[i]=false;
        }
    }

    public void two_exchange(int no1, int no2, int no3, int no4){
        removePoint(no1, no2);
        removePoint(no3, no4);
        boolean flag;

        if(adj[no1][no3] || adj[no2][no4]) {
            addPoint(no1, no4);
            addPoint(no2, no3);
            flag=false;
        }
        else{
            addPoint(no1, no3);
            addPoint(no2, no4);
            flag=true;
        }

        if(!poligno()){
            if(flag){
                removePoint(no1, no3);
                removePoint(no2, no4);
                addPoint(no1, no4);
                addPoint(no2, no3);
            }
            else{
                removePoint(no1, no4);
                removePoint(no2, no3);
                addPoint(no1, no3);
                addPoint(no2, no4);
            }
        }
    }

    public boolean poligno(){
        boolean[] visitado = new boolean [n];
        int contador=0;
        for(int i=0; i<n; i++) visitado[i]=false;

        for(int no=0; no<n; no++){
            if(!visitado[no]){
                contador++;
                dfs(no,visitado);
            }
        }
        return (contador==1);
    }

    public void dfs (int no, boolean[] visitado){
        visitado[no]=true;
        for(int w=0; w<n; w++){
            if(adj[no][w] && !visitado[w]) dfs(w,visitado);
        }
    }

    public boolean poligno_simples(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(adj[i][j]){
                    int first_node=i;
                    int first_link=j;
                    for(int k=0; k<n; k++){
                        for(int l=0; l<n; l++){
                            if(adj[k][l]){
                                int second_node=k;
                                int second_link=l;
                                if (first_node == second_node || first_node == second_link || first_link == second_node || first_link == second_link) {
                                    continue;
                                }
                                else{
                                    if(interseta(nodes[first_node],nodes[first_link],nodes[second_node],nodes[second_link])){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public double perimetro(){
        double result=0;
        boolean[][] visitado = new boolean[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                visitado[i][j]=false;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                int p1=i;
                int p2=j;
                if(adj[p1][p2] && !visitado[p1][p2]){
                    result+=distancia(nodes[p1],nodes[p2]);
                    visitado[p1][p2]=true;
                    visitado[p2][p1]=true;
                }
            }
        }
        return result;
    }

}
