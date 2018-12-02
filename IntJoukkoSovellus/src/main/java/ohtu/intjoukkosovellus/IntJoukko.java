
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.


    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alusta(ljono);
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        ljono = new int[kapasiteetti];
        alusta(ljono);
        this.kasvatuskoko = OLETUSKASVATUS;
    }
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti on liian pieni");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko on liian pieni");//heitin vaan jotain :D
        }
        ljono = new int[kapasiteetti];
        alusta(ljono);
        this.kasvatuskoko = kasvatuskoko;
    }

    public void alusta(int[] jono) {
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) { //jos ei jo kuulu:
            ljono[alkioidenLkm] = luku; //lisätään viimeistä seuraavaksi alkioksi uusi luku
            alkioidenLkm++;
            if (alkioidenLkm % ljono.length == 0) { //jos ovat samat (?)
                ljono = luoUusiTaulukko(ljono);
            }
            return true;
        }
        return false; //jos kuuluikin, palautetaan false
    }

    public boolean kuuluu(int luku) {
        if(alkioidenLkm > 0) {
            for (int i = 0; i < alkioidenLkm; i++) {
                if (luku == ljono[i]) { //heti jos löytyy yksikin sama, palautetaa true
                    return true;
                }
            }
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int[] luoUusiTaulukko(int[] alkup) {
        int[] alkupTaulukko = new int[alkup.length]; //otetaan talteen vanha taulukko
        kopioiTaulukko(alkup, alkupTaulukko); //kopioidaan ljonon alkiot odottamaan alkupiin
        ljono = new int[alkioidenLkm + kasvatuskoko]; //ljonon kokoa kasvatetaan
        kopioiTaulukko(alkupTaulukko, ljono); //kopioidaan arvot takaisin ljonoon
        return ljono;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                siirraLahtien(i);
                return true;
            }
        }
        return false;
    }

    public void siirraLahtien(int i) {
        for (int j = i; j < alkioidenLkm - 1; j++) {
            int apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
        alkioidenLkm--;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        String tuloste = "{";
        if(alkioidenLkm > 0) {
            for (int i = 0; i < alkioidenLkm; i++) {
                tuloste += ljono[i];
                tuloste = pilkku(i, tuloste); //tarvitaanko pilkkua vai ei
            }
        }
        tuloste += "}";
        return tuloste;
    }

    public String pilkku(int i, String jono) {
        if (i < alkioidenLkm - 1) {
            jono += ", ";
        }
        return jono;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        return arrayYhdiste(new IntJoukko(), a.toIntArray(), b.toIntArray());
    }

    public static IntJoukko arrayYhdiste (IntJoukko loppujoukko, int[] joukko1, int[] joukko2){
        for (int i = 0; i < joukko1.length; i++) {
            loppujoukko.lisaa(joukko1[i]);
        }
        for (int i = 0; i < joukko2.length; i++) {
            loppujoukko.lisaa(joukko2[i]);
        }
        return loppujoukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        return arrayLeikkaus(new IntJoukko(), a.toIntArray(), b.toIntArray());
    }

    public static IntJoukko arrayLeikkaus (IntJoukko loppujoukko, int[] joukko1, int[] joukko2) {
        for (int i = 0; i < joukko1.length; i++) {
            for (int j = 0; j < joukko2.length; j++) {
                if (joukko1[i] == joukko2[j]) {
                    loppujoukko.lisaa(joukko2[j]);
                }
            }
        }
        return loppujoukko;
    }

    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        return arrayErotus(new IntJoukko(), a.toIntArray(), b.toIntArray());
    }

    public static IntJoukko arrayErotus(IntJoukko loppujoukko, int[] joukko1, int[] joukko2) {
        for (int i = 0; i < joukko1.length; i++) {
            loppujoukko.lisaa(joukko1[i]);
        }
        for (int i = 0; i < joukko2.length; i++) {
            loppujoukko.poista(i);
        }
        return loppujoukko;
    }
}