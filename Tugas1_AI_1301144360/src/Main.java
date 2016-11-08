
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

//import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {

    /**
     * Prosedur AStar
     *
     * @param awal adalah node awal yang ingin diseksekusi
     * @param akhir adalah node akhir atau node tujuan dari node awal
     *
     * PseudoCode dari buku AI di perpus:
     * function A*(masalah) returns solusi
     *      Open [assign] S
     *      Closed [assign] array kosong
     * loop sampai goal ditemukan atau sampai tidak ada simpul didalam open
     *      if OPEN = kosong then
     *          GAGAL
     *      else
     *          BestNode = simpul yang ada di open dengan f minimal
     *          Pindahkan simpul terbaik tersebut dari open ke closed
     *          if BestNode = goal then
     *              SUKSES
     *          else
     *              Bangkitkan semua suksesor BestNode tapi jangan buat pointer
     *              Untuk setiap suksesor, hitung nilai g(suksesor) nya:
     *                  g(suksesor) = g(BestNode) + actualCost(BestNode ke suksesor)
     *              {Periksa Suksesor}
     *              if suksesor ada di open then
     *                  {sudah pernah dibangkitkan tapi belum di proses}
     *                  old = simpul di open yang sama dengan suksesor tersebut
     *                  tambahkan old sebagai suksesor BestNode
     *                  buat pointer dari old ke BestNode
     *                  bandingkan nilai g(old) dengan g(suksesor)
     *                  if g(old) lebih baik then
     *                      ubah parent old ke BestNode
     *                      ubah nilai g dan f yang ada pada old
     *                      propagansi untuk semua suksesor old dengan penelusuran dfs dengan aturan:
     *                      loop sampai simpul suksesor tidak ada di open atau simpul tidak punya suksesor
     *                          if suksesor ada di open then
     *                              propagasi diteruskan
     *                          else 
     *                              if nilai g via suksesor lebih baik then
     *                                  propagasi diteruskan
     *                              else
     *                                  propagasi dihentikan
     *                          end 
     *                      end
     *                  end
     *              else
     *                  {suksesor tidak ada di open maupun closed}
     *                  masukkan suksesor ke open
     *                  tambahkan suksesor tersebut sebagai suksesornya BestNode
     *                  hitung f = g(suksesor) + h(suksesor)
     *              end
     *          end
     *      end 
     *  end
     */
    public static void AStar(Kota awal, Kota akhir) {
        //closed adalah HashSet yang akan diisikan dengan Kota yang sudah di instansiasi
        Set<Kota> closed = new HashSet<Kota>();
        //awalnya, closednya kosong

        //override method compare yang ada di comparator
        //method compare berfungi untuk membandingnya nilai f dari 2 node
        //return int 1 0 -1
        //1 jika f node pertama lebih besar
        //0 jika f node pertama lebih kecil
        //dan -1 jika f kedua node sama
        PriorityQueue<Kota> open = new PriorityQueue<Kota>(20,
                new Comparator<Kota>() {
            @Override
            public int compare(Kota databaru, Kota datasebelumnya) {
                if (databaru.getF() > datasebelumnya.getF()) {
                    return 1; //ada di paling belakang
                } else if (databaru.getF() < datasebelumnya.getF()) {
                    return -1; //ada di paling depan, kemungkinan di closed duluan
                } else {
                    return 0; //ada di tengah
                }
            }

        }
        );

        System.out.println("Perhitungan Cost: ");
        double jarakterpendek = 0;

        //cost awal di set 0 soalnya dari node awal
        awal.setG(0);

        //masukkan node awal kedalam open
        //open adalah queue
        //di queue ada add untuk menambahkan
        //ada poll untuk menghapus tetapi nilainya di return
        //ada remove untuk menghapus dan nilainya tidak di return
        open.add(awal);
        System.out.println("[OPEN]   " + open);
        System.out.println("[CLOSED] " + closed);
        System.out.println("");

        //node awal bukan node akhir, buat 1 variable/atribut untuk cek node akhir
        boolean target = false;

        //loop ketika opennya ga kosong dan node belum sampai di node tujuan
        while ((open.isEmpty()==false) && (target==false)) {

            Kota BestNode = open.poll();
            //nodenya punya f yang paling kecil dihapus dari open dan dimasukkan ke close
            closed.add(BestNode);
            System.out.println("[OPEN]   " + open);
            System.out.println("[CLOSED] " + closed);

            //cek node tujuannya ketemu apa engga
            //kalau ketemu loopingnya selesai
            if (BestNode.getKota() == akhir.getKota()) {
                target = true;
                System.out.println("--- SELESAI ---");
                System.out.println("");
            }

            //cek semua anak dari parent, foreach
            for (Cost e : BestNode.getCost()) {
                Kota suksesor = e.getTujuan();
                double cost = e.getCost();
                double temp_g = BestNode.getG() + cost;
                double temp_f = temp_g + suksesor.getH();
                System.out.println("\t[OPEN]\t" + suksesor + " " + temp_f);

                /*kalau node anaknya punya f yg paling gede langsung continue cari node lain*/
                if ((closed.contains(suksesor)==true) && (temp_f >= suksesor.getF())) {
                    continue; //gausah ditulis juga gapapa
                }
                /*else if, kalau node anaknya gaada di dalam queue atau f nya lebih kecil*/
                else if ((open.contains(suksesor)==false) || (temp_f < suksesor.getF())) {
                    suksesor.setParent(BestNode);
                    suksesor.setG(temp_g);
                    suksesor.setF(temp_f);

                    if (open.contains(suksesor)==true) {
                        open.remove(suksesor);
                    }

                    open.add(suksesor);

                }
            }
            System.out.println("");

        }
        System.out.println("Total Cost: " + akhir.getF());

    }

    /**
     * @param tujuan adalah node tujuan
     * 
     * 
     * Fungsi JalurKe berfungsi untuk mereturn node-node dalam bentuk list Kota
     * yang di return adalah node awal dan node lainnya yang terhubung sampai
     * node akhir dengan nilai cost yang paling minimum
     */
    public static List<Kota> JalurKe(Kota tujuan) {
        List<Kota> jalur;
        //path bertipe List<Kota> sesuai dengan yang bisa di return oleh fungsi printPath ini
        jalur = new ArrayList<Kota>();
        //pake arraylist biar gausah looping indexnya

        //looping dari node tujuan sampai node awal dan node tidak null
        //node yang di looping di add kedalam path
        //path bertipe arraylist, semua node sudah saling terhubung didalam path
        for (Kota node = tujuan; node != null; node = node.getParent()) {
            jalur.add(node);
        }

        Collections.reverse(jalur);
        //dilakukan reverse agar returnnya mulai dari node awal sampai node akhir

        return jalur;
    }

    public static void main(String[] args) {
        //main program
        //instansiasi object-object node dengan constructor yang ada di class Kota
        //parameter dari constructornya adalah nama kota dan heuristiknya
        //banyaknya node ada 12
        Kota Ravenna = new Kota("Ravenna", 0);
        Kota Rimini = new Kota("Rimini", 0.5);
        Kota Ferrara = new Kota("Ferrara", 5);
        Kota Forli = new Kota("Forli", 2);
        Kota Cesena = new Kota("Cesena", 4.5);
        Kota Faenza = new Kota("Faenza", 4);
        Kota Imola = new Kota("Imola", 5);
        Kota Emilia = new Kota("Emilia", 6);
        Kota Terme = new Kota("Terme", 7);
        Kota Carpi = new Kota("Carpi", 8);
        Kota Piacenza = new Kota("Piacenza", 10);
        Kota Bobbia = new Kota("Bobbia", 10.5);

        //instansiasi cost (yang menghubungkan antara dua node)
        //menginstansiasinya dengan constructor yang ada di class Cost
        //parameter dari constructornya adalah node yang mau dihubungkan dan jaraknya
        //banyaknya edge ada 20
        
        //Bobbia, punya 3 edge
        Bobbia.setCost(3);
        Bobbia.addEdge(Piacenza, 5);
        Bobbia.addEdge(Terme, 3);
        Bobbia.addEdge(Cesena, 15);

        //Piacenza, punya 2 edge
        Piacenza.setCost(2);
        Piacenza.addEdge(Carpi, 3);
        Piacenza.addEdge(Terme, 73);

        //Carpi, punya 2 edge
        Carpi.setCost(2);
        Carpi.addEdge(Ferrara, 8);
        Carpi.addEdge(Emilia, 2);

        //Terme, punya 2 edge
        Terme.setCost(2);
        Terme.addEdge(Emilia, 2);
        Terme.addEdge(Faenza, 3);

        //Emilia, punya 1 edge
        Emilia.setCost(1);
        Emilia.addEdge(Imola, 2);

        //Imola, punya 2 edge
        Imola.setCost(2);
        Imola.addEdge(Faenza, 1);
        Imola.addEdge(Forli, 3);

        //Faenza, punya 2 edge
        Faenza.setCost(2);
        Faenza.addEdge(Forli, 2);
        Faenza.addEdge(Cesena, 6);

        //Cesena, punya 1 edge
        Cesena.setCost(1);
        Cesena.addEdge(Rimini, 5);

        //Forli, punya 2 edge
        Forli.setCost(2);
        Forli.addEdge(Ravenna, 3);
        Forli.addEdge(Cesena, 2);

        //Ferrara, punya 2 edge
        Ferrara.setCost(2);
        Ferrara.addEdge(Imola, 3);
        Ferrara.addEdge(Ravenna, 6);

        //Rimini, punya 1 edge
        Rimini.setCost(1);
        Rimini.addEdge(Ravenna, 1);

        //3+2+2+2+1+2+2+1+2+2+1 = 20
        //semua edge sudah terinstansiasi dalam 11 node
        //node ke 12 adalah Ravenna, tidak memiliki edge
        Ravenna.setCost(0);
        
        System.out.println("----- FIND THE SHORTEST PATH FROM BOBBIA TO RAVENNA -----");
        System.out.println("");

        //perhitungan cost terkecil dengan astar dari bobbia ke ravenna
        AStar(Bobbia, Ravenna);

        //memasukkan jalur-jalur yang ditempuh dari bobbia ke ravenna kedalam path
        List<Kota> jalur = JalurKe(Ravenna);

        //mengoutputkan jalurnya
        System.out.println("Solusi: " + jalur);

    }

}
