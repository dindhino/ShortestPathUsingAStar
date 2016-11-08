

/*
 * @author dindhino
 *
 * Dindin Dhino Alamsyah
 * 1301144360
 * IF-37-09
 * 
 * Tugas Artificial Intelligence
 * Implementasi A*
 */

public class Kota {

    private final String kota;
    private double g; //jarak jari node asal ke node yang lagi di eksekusi
    private final double h; //heuristik, data yang ada di tabel
    private double f = 0; //f = g + h
    private Cost[] edge;
    private Kota parent;
    private int jumlahEdge;

    public Kota(String kota, double heuristik) {
        this.kota = kota;
        h = heuristik; //gausah pake this.h soalnya nama parameternya beda, pake this.h juga sama aja
    }

    //getter
    public double getG() {
        return g;
    }

    public double getF() {
        return f;
    }

    public Cost[] getCost() {
        return edge;
    }

    public Kota getParent() {
        return parent;
    }

    public double getH() {
        return h;
    }

    public String getKota() {
        return kota;
    }

    public int getJumlahEdge() {
        return jumlahEdge;
    }

    //setter
    public void setG(double g) {
        this.g = g;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setJumlahEdge(int jumlahEdge) {
        this.jumlahEdge = jumlahEdge;
    }
    
    public void setCost(int maxEdge) {
        edge = new Cost[maxEdge];
    }

    public void addEdge(Kota newEdge, double newCost) {
        if (this.getJumlahEdge() < edge.length) {
            edge[this.getJumlahEdge()] = new Cost(newEdge, newCost);
            jumlahEdge++;
        }
        else
        {
            System.out.println("Edge penuh");
        }
    }

    public void setParent(Kota parent) {
        this.parent = parent;
    }
    
    
    
    //hitung f suatu node, f = g + h
    public void hitungF(double jarakdariawal, double jarakkeakhir) {
        this.f = jarakdariawal + jarakkeakhir;
    }

    //dibuat override toString()
    //karena di dalam main ada fungsi printPath yang mereturn path
    //dan ada procesure astar yang menggunakan hashmap, hashset, set, queue, priorityqueue, comparator
    //pake list dan arraylist juga
    //kalau ga dibuat override toString, nanti outputnya Node@address
    
    @Override
    public String toString() {
        return kota;
    }

}
