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

public class Cost {

    private final double cost; //panjang egdenya
    private final Kota tujuan; //node tujuan yang mau dihubungin sama node yang lagi di eksekusi

    public Cost(Kota tujuan, double jarak) {
        this.tujuan = tujuan;
        cost = jarak; //gausah pake this.cost soalnya nama parameternya beda, pake this.cost juga sama aja
    }

    //getter
    public double getCost() {
        return cost;
    }

    public Kota getTujuan() {
        return tujuan;
    }
}