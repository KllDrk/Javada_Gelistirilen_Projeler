import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.*;

public class Main {
    //Iki bagimsiz degisken icin(x1 ve x2) 2 boyutlu 200 sayi alan dizi olusturdum.
    static double[][] x;
    //bagimsiz degisken icin bir boyutlu dizi olusturdum.
    static double[] y;
    //egitim icin kullanilacak x1 x2 ve y degiskenleri icin 140 sayi alan dizi olusturdum.
    static double[][] egitim;
    // test icin kullanılacak x1 x2 ve y degiskenleri icin 60 sayi alan dizi olusturdum.
    static double[][] test;
    // eklenen veya silinen verileri sayisini tutmak icin olusturdum.
    static int silinenVeri;
    //yerlestirilen rastgele degerlerin hepsinin kontrolu icin olusturdum. Eger butun degerleri dogru cıkarsa
    //dogru cikiyor demektir.
    static boolean[] veriControl;
    //eger ki olusturulan veri henuz islem gormemisse menuye geri doner. Eger islem gormusse veriislemlerine doner.
    static boolean Control;
    //x1 ve x2 icin korelasyon katsayisi hesaplar. Buyukluklerine gore islem uygulanir.
    static double [] korelasyonKatsayisi;
    //formullerin hepsinde 5 temel fonksiyon kullanilir. Bu 5 temel fonksiyon icin çikan sonucu tutar.
    static double[] fonksiyonelIslemler;
    //kullanim degerinde eger false ise x1 in korelasyon katsayisi buyuk oldugu anlamina  gelir.
    //eger deger true olursa x2 katsayisi buyuk veya esit anlamina gelir.
    static boolean kullanim;
    //y=ax+b degerinde ki a degerini bulur.
    static double regresyonA;
    //y=ax+b degerinde ki b degerini bulur.
    static double regresyonB;
    //y=ax+b modelinde egitimin y degerleri tutulur.
    static double[] regresyonEgitimY;
    //y=ax+b modelinde testiny degerleri tutulur.
    static double[] regresyonVeriY;
    //Bu degerler korelasyon katsayisinde islemleri yapma kolayligi saglamasi icin olusturulmustur.
    static double a,b,c,d,e;
    //egitimin SSE degeri tutulur.
    static double egitimSSE;
    //testin SSSE degeri tutulur.
    static double veriSSE;

    //baslangic degerleri ve alanlari belirlenir.
    static {
        x = new double[2][200];
        y = new double[200];
        egitim = new double[3][140];
        test = new double[3][60];
        veriControl = new boolean[200];
        Control=false;
        korelasyonKatsayisi = new double[2];
        fonksiyonelIslemler = new double [5];
        regresyonA = 0;
        regresyonB = 0;
        regresyonEgitimY = new double[140];
        regresyonVeriY = new double [60];
        kullanim=false;
        a=0;
        b=0;
        c=0;
        d=0;
        e=0;
        silinenVeri=0;
        egitimSSE=0;
        veriSSE=0;
    }

    //program ilk basladiginda veriler olusturulur. egitim test olarak dagitilir.
    public static void main(String[] args){

        veriSetiOlusturma();
    }

    public static void veriSetiOlusturma()
    {
        // her veri olusumunda degerlerin sifirlanmasi gerekmektedir.
            korelasyonKatsayisi[0]=0;
            korelasyonKatsayisi[1]=0;
            regresyonA=0;
            regresyonB=0;
            egitimSSE=0;
            veriSSE=0;
            kullanim=false;
            Control=false;

            //fonksiyonel islem degerleri de sifirlanir.
            for(int i=0;i<5;i++)
            {
                fonksiyonelIslemler[i]=0;
            }

            //x1 x2 degerleri rastgele olusturulur. Y deger ibu iki degerin kareleri farkının mutlak degeri olarak
        //cikar. Bu denklem de 3 degerinde negatif cikma sansi sifirdir.
            for(int i=0;i<200;i++)
            {
                Random random1=new Random();
                x[0][i]= random1.nextInt(100);
                Random random2=new Random();
                x[1][i]= random2.nextInt(100);
                y[i]=pow(x[0][i],2)-pow(x[1][i],2);
                y[i]=abs(y[i]);
                //veri control degerleri false yapilir.
                veriControl[i]=false;
            }

        System.out.println("Veri Seti Oluşturuldu.");
        //rastgele dagitma islemi yapilir.
        rastgeleDagitma();
    }

    //eger veri sildiyseniz veya veri eklediyseniz veya veri degistirdiyseniz bu islemi tekrar yapmaniz
    // gerekmektedir!!!!
    public static void rastgeleDagitma()
    {
        //deger no indeks sayisi olarak kullanilir.
        int DegerNo;
        //toplam deger degeri sifirlanir. Bu degerin olma nedeni butun indeksteki degerlerin alinip alinmadigina
        //bakmaktir
        int toplam_deger = 0;
        //ilk once egitim degerleri yerlestirilir.
        for(int i=0;i<140;i++)
        {
            //indeks degeri random secilir.
            Random random=new Random();
            DegerNo= random.nextInt(200);
            //eger ki veriControl false degeri ise egitime degerler aktarilir.
            if(!veriControl[DegerNo])
            {
                //burada true yapilir ki bir daha ayni deger aktarilmasin.
                veriControl[DegerNo]=true;
                //System.out.println("Egitim Deger No:"+DegerNo);
                toplam_deger++;
                for(int j=0;j<3;j++)
                {
                    // sirayla x1 x2 ve y degerleri aktarilir.
                    switch (j)
                    {
                        case 0 -> egitim[j][i]=x[0][DegerNo];
                        case 1 -> egitim[j][i]=x[1][DegerNo];
                        case 2 -> egitim[j][i]=y[DegerNo];
                    }
                }
            }
            // eger degerNo true gelirse i eksiltilir. fordan dolayi 1 artacagi için aslında aynı degeri tekrar
            //eder ta ki degerNo false degeri geleseye kadar.
            else
            {
                i--;
            }
        }
        //ayni islemler test verileri icinde yailir.
        for (int i=140;i<200;i++)
        {
            Random random=new Random();
            DegerNo= random.nextInt(200);
            if(!veriControl[DegerNo])
            {
                veriControl[DegerNo]=true;
                //System.out.println("Test Deger No:"+DegerNo);
                toplam_deger++;
                for(int j=0;j<3;j++)
                {
                    switch (j)
                    {
                        case 0 -> test[j][i-140]=x[0][DegerNo];
                        case 1 -> test[j][i-140]=x[1][DegerNo];
                        case 2 -> test[j][i-140]=y[DegerNo];
                    }
                }

            }
            else
            {
                i--;
            }
        }

        //yapilan islemin dogrulugu kontrol edilmek icin toplam degere bailir. eger sonuc 200 cikarsa deger dogru
        //cikmis demektir.
        System.out.println("Toplam deger:"+toplam_deger);
        //control false ise menuye true ise veriIslemlere gider.
        if(!Control)
        {
            Menu();
        }
        else {
            veriIslemleri();
        }
    }

    //veri silinirken silinecek olan indeks degeri girilir. bu silinen indekse degerinden itibaren tek tek geriye
    //degerler atilir. Bu degerler atilma sonucunda son indeks degerinde -1 degerleri tutulur.
    //Ornek: 28. indeksdeki degerler silinsin. Bundan dolayi 29. indeks degerleri 28 e,30. indeks degerleri 29 a
    // diye ilerler ve en son 199. indeks degerleri 198. indekse atanır. 199 daki indeks degerleri ise -1 e cevrilir.
    public static void veriSilme()
    {
        //eger silinen veri sayisi 200 ise bu tum verilerin silindigini gosterir. Daha fazla silinemeyecegi icin
        //veri islemlerine uyari vererek geri doner.
        if(silinenVeri==200)
        {
            System.out.println("Veri Kapasitesi Boş!");
            veriIslemleri();
        }
        //verideki kacıncı degerin silinecegi tutulur.
        int degerNo;
        System.out.println("""
                Hangi Sıradaki değer silinecek?
                Deger:""");
        //deger alinir.
        Scanner girdi=new Scanner(System.in);
        degerNo=girdi.nextInt();

        //silinen degerler ekrana yazdirilir.
        System.out.println("Silinen Sıra:"+degerNo+"\nSilinen x1 degeri:"+x[0][degerNo-1]+"\nSilinen x2"+
                "degeri:"+x[1][degerNo-1]+"\nsilinen y degeri:"+y[degerNo-1]);

        //bu indekste bulunan degerleri sıfırlar. mesela 29 uncu sıradaki degeri silmek isteyen kisi 28. indeks
        //degerini silmek istiyordur. ilk once bu degerler sifirlanir.
        x[0][degerNo-1]=0;
        x[1][degerNo-1]=0;
        y[degerNo-1]=0;
        //silinen veri 1 arttirilir.
        silinenVeri++;
        //burada tek tek diger indekslere dogru atama yapilir. i degerine indeks numarasi atilir. Bu sayede
        //butun verileri kontrol edilmesi kacinilir ve sistem daha hizli calisir.
        for(int i=degerNo-1;i<200;i++)
        {
            //bir veri degeri silindigi icin i<=198 olacak. buda 198. indeksi kapsamak üzere bütün indeksleri
            //o indekse atar
            if(i<=199-silinenVeri)
            {
                //indeksleri bir sonraki indeksleri icine alir. mesela i=198 olsun. Bu da x[198]=x[199] yapacak
                //ve o indeksde bir sonraki indeks degerleri atanmis olacak.
                x[0][i]=x[0][i+1];
                x[1][i]=x[1][i+1];
                y[i]=pow(x[0][i],2)-pow(x[1][i],2);
                y[i]=abs(y[i]);

            }
            //i degeri 200-silinenVeridne buyukv eya esit oldugu zaman degerleri -1 yapacak. Mesela sadece bir veri
            //silinsin. i>=200-1 i>=199 yapar. yani bu durumda sadece 199. indeks degeri -1 yapilir. Sonra bir veri
            //daha silinsin. Bu da i>=200-2 i>=198 yapar bu da hem 198 hem de 199. indeks degerlerini -1 yapacaktir.
            else if(i>=200-silinenVeri)
            {
                x[0][i]=(-1);
                x[1][i]=(-1);
                y[i]=(-1);
            }
        }
    }
    //veri ekleme mantigi veri silme ile neredeyse zit calisir. silme isleminde biri alt indekse bir ustteki indeks
    //Degeri atanirken simdi eklenecek olan indeks degerdeki bulunan elemanlar bir ustteki indekse atanir. Mesela
    //28. indekse deger atadiniz. 199. indeks degeri eger silinmis deger varsa 198. indeks degerini alir. Ayni
    //sekilde 198. indeks degeri 197. indekse atanir. En sonunda 28. indeks 29. indekse atanir. 28. indeks sifirlanir
    //ve buraya eklenecek degerler atanir.
    public static void veriEkle()
    {
        //eger silinen veri yoksa atama islemi gerceklestirilmez ve verisilemlerine gonderilir.
        if(silinenVeri==0)
        {
            System.out.println("Veri Kapasitesi Dolu!");
            veriIslemleri();
        }
        //sira numarasi, x1 ve x2 tutulur.
        int degerNo,deger1,deger2;

        //sira numarasi alinir.
        System.out.println("""
                Hangi Sıradaki değere eklenecek?
                Deger:""");
        Scanner girdi1=new Scanner(System.in);
        degerNo=girdi1.nextInt();

        //x1 degeri alinir.
        System.out.println("x1 degerini giriniz:");
        Scanner girdi2=new Scanner(System.in);
        deger1=girdi2.nextInt();

        //x2 degeri alinir.
        System.out.println("x2 degerini giriniz:");
        Scanner girdi3=new Scanner(System.in);
        deger2=girdi3.nextInt();

        //silinenveri 1 eksiltilir. Bu sayede eksik degere gore islemler yapilabilir.
        silinenVeri--;
        //i en son indeksten baslar.
        for(int i=199;i>=degerNo-1;i--)
        {
            //degerno-1 indeks numarasidir. Az önceki örnekte 28. indkese deger eklemek istiyorsak 29. indeks
            //dahil tüm indeksleri bir üst indekse aktarmaliyiz.
            if(i>degerNo-1)
            {
                x[0][i]=x[0][i-1];
                x[1][i]=x[1][i-1];
                y[i]=y[i-1];
            }

            //eger deger indeks numarasindan kucuk veya esitse(for dongusunden dolayı sadece esit olabilir)
            //x1 ve x2 degerleri aktarilir vey degeri hesaplanarak degerler aktarilir.
            else if(i<=degerNo-1)
            {
                x[0][i]=deger1;
                x[0][i]=deger2;
                y[i]=pow(x[0][i],2)-pow(x[1][i],2);
                y[i]=abs(y[i]);
            }
        }

    }
    //eger ki bir indeksteki deger degistirilmek isteniliyorsa bu kullanilmasi gerekmektedir. Mesela 28. indekste
    //degerlerin degismesi isteniyor. Sıra numarasi girilerek indeks degerine ulasilir ve o degerler degistirilir.
    public static void veriDegistir()
    {
        //sıra numarasi,x1 ve x2 tutulmaktadir.
        int degerNo,deger1,deger2;

        //sira numarasi girilir.
        System.out.println("""
                Hangi Sıradaki Degeri degistirmek istiyorsun?
                Deger:""");
        Scanner girdi1=new Scanner(System.in);
        degerNo=girdi1.nextInt();

        //x1 degeri girilir.
        System.out.println("x1 degerini giriniz:");
        Scanner girdi2=new Scanner(System.in);
        deger1=girdi2.nextInt();
        x[0][degerNo-1]=deger1;

        //x2 degeri girilir.
        System.out.println("x2 degerini giriniz:");
        Scanner girdi3=new Scanner(System.in);
        deger2=girdi3.nextInt();
        x[1][degerNo-1]=deger2;

        //degerler hesaplanir ve y degeri bulunur.
        y[degerNo-1]=pow(x[0][degerNo-1],2)-pow(x[1][degerNo-1],2);
        y[degerNo-1]=abs(y[degerNo-1]);

        //girilen degerler ekrana yazdirilir.
        System.out.println("Değerler:\nx1:" + x[0][degerNo-1] + "\nx2:" + x[1][degerNo-1] + "\ny:" + y[degerNo-1]);
    }

    //verilerin kontrol etme amacli gormek isterseniz diye bunu yazdim. Fonksiyon Butun veri lsitesini gosterebilir
    //tek bir indekse degerini gosterebilir ya da  gitim ve Test verilerini gosterebilir.
    public static void veriGoster()
    {
        //secim degeri tutulur.
        int secim;
        //secim yapilir.
        System.out.println("""
                Yapılacak İslem:
                1-Bütün Veri Setini Göster
                2-Tek Bir Degeri Goster.
                3-Egitim ve Test Veri Setini Göster.
                Secim:""");
        Scanner girdi=new Scanner(System.in);
        secim=girdi.nextInt();

        switch (secim)
        {
            case 1 ->{
                for(int i=0;i<200;i++)
                {
                    //eger burasi gelirse butun veri listesi yazdirilir.
                    System.out.println((i+1)+". Değerler:\nx1:" + x[0][i] + "\nx2:" + x[1][i] + "\ny:" + y[i]);
                }
            }
            case 2 ->{
                //buradan sira numarasi girmesi istenir.
                int secimDegeri;

                //deger girilir.
                System.out.println("""
                Hangi Sıradaki Degeri eklemek istiyorsun?
                Deger:""");
                Scanner girdi1=new Scanner(System.in);
                secimDegeri=girdi1.nextInt();

                //o indeksdeki deger yazdirilir.
                System.out.println((secimDegeri)+"Değerler:\nx1:" + x[0][secimDegeri-1] + "\nx2:" + x[1][secimDegeri-1] +
                        "\ny:" + y[secimDegeri-1]);
            }
            case 3 ->{
                for(int i=0;i<200;i++)
                {
                    //indeks degeri 140 dan kucukse egitim verileri yazdirilir.
                    if(i<140)
                    {
                        System.out.println("Egitim Degerleri:");
                        for(int j=0;j<3;j++)
                        {
                            //sirayla x1 x2 ve y degerleri yazdirilir.
                            switch (j)
                            {
                                case 0->System.out.println("x1:"+egitim[j][i]);
                                case 1->System.out.println("x2:"+egitim[j][i]);
                                case 2->System.out.println("y:"+egitim[j][i]);
                            }
                        }
                    }
                    //eger 140 a esit veya buyukse test verileri yazdirilir.
                    else
                    {
                        System.out.println("Test Degerleri:");
                        for(int j=0;j<3;j++)
                        {
                            switch (j)
                            {
                                //sirayla x1 x2 ve y degerleri yazdirilir. i-140 yazma nedeni for dongusu 140
                                //degerinden baslyacagindan test degerlerini 0. indeksten baslatmak icin yapildi
                                case 0->System.out.println("x1:"+test[j][i-140]);
                                case 1->System.out.println("x2:"+test[j][i-140]);
                                case 2->System.out.println("y:"+test[j][i-140]);
                            }
                        }
                    }
                }
            }
            //girilen deger bu uc secenekten biri degilse veriGostere tekrar donulur
            default -> {
                System.out.println("Yanlış Değer girdiniz. Lütfen Tekrar Deneyin.");
                veriGoster();
            }
        }
    }

    //veriIslemlerin menusu burasidir. Burada yapilacak olan eylem secilir ve islemlerin yapilacagi fonksiyonlara
    //goturulur.
    public static void veriIslemleri()
    {
        //control true yapilarak geri donme oalrak buraya donerler.
        Control=true;
        //secim degeri tutulur.
        int secim;
        //secim degeri alinir.
        System.out.println("""
                Yapılacak işlemi seçiniz.
                1-Veri Seti Oluşturma
                2-Veri Seti Değer Silme
                3-Veri Seti Değer Ekleme
                4-Veri Degistirme
                5-Veri Seti Degerlerini Göster
                6-Menüye Geri Dönme
                Secim:""");
        Scanner girdi=new Scanner(System.in);
        secim=girdi.nextInt();

        switch(secim)
        {
            //basilan degere gore fonksiyona gider
            case 1 -> veriSetiOlusturma();
            case 2 -> veriSilme();
            case 3 -> veriEkle();
            case 4 -> veriDegistir();
            case 5-> veriGoster();
            case 6-> Menu();
            //eger bunlar disinda bir degere basilirsa uyari verir ve veriIslemlere geri doner.
            default -> System.out.println("Yanlış Değer Girdiniz. Lütfen Tekrar Deneyin.");
        }

        veriIslemleri();
    }

    //burada bizim suana kadar kulandimiz tum formullerin icinde olan 5 ana toplam degeri ele alinir.
    //Bu degerlerin boyle yazilma sebebi daha kolay formullerin elde edilmesini saglamak ve baska
    //degiskenlerde formul olarak kullanilabilmesini saglamak

    //fonksiyon1 x*y nin toplam degeri elde edilir.
    public static void fonksiyon1(double x,double y)
    {
        fonksiyonelIslemler[0]+=x*y;
    }

    //fonksiyon ikide x in toplam degeri elde edilir
    public static void fonksiyon2(double x)
    {
        fonksiyonelIslemler[1]+=x;
    }

    //fonksiyon ucte y nin toplam degeri elde edilir.
    public static void fonksiyon3(double y)
    {
        fonksiyonelIslemler[2]+=y;
    }

    //fonksiyon dortte x^2 nin toplam degeri elde edilir.
    public static void fonksiyon4(double x)
    {
        fonksiyonelIslemler[3]+=pow(x,2);
    }

    //fonksiyon beste y^2 nin toplam degeri elde edilir.
    public static void fonksiyon5(double y)
    {
        fonksiyonelIslemler[4]+=pow(y,2);
    }


    //korelasyon degeri burada olculur. Ben bu degeri -1 veya 1 e en yakın degeri bularak hesaplattirdim.
    //bunu da iki degerin mutlak degerini alarak hangisi daha buyuk olduguna baktirmam oldu. Sonucta iki
    //degerde en fazla 1 veya -1 degeri alabilir. Mutlak degerini aldirtip baktirmam fonksiyon acisindan
    //daha kisa surede bitmesini sagladi.
    public static void korelasyonKatsayisiHesaplama()
    {
        //korelasyon degerleri x1 ve x2  icin ayni sekilde farkli yerlerde tutularak hesaplanir.
        for(int i=0;i<2;i++)
        {
            //her iki hesaplamada da degerler sifirlanir. Bu sayede etkilesim kolaylasir ve islemlerin icinde
            //herhangi bir karmassa olmaz.
            regresyonA=0;
            regresyonB=0;
            korelasyonKatsayisi[i]=0;
            fonksiyonelIslemler[0]=0;
            fonksiyonelIslemler[1]=0;
            fonksiyonelIslemler[2]=0;
            fonksiyonelIslemler[3]=0;
            fonksiyonelIslemler[4]=0;
            a=0;
            b=0;
            c=0;
            d=0;
            e=0;
            //egitim verileri icin bu degerler tek tek fonksiyonlara gonderilir hesaplanir.
            for(int j=0;j<140;j++)
            {
                //eger bu degerlerin ucunden biri  -1 ise zaten o deger silinmis demektir. Hatta biri bile -1 ise
                //bu deger silinmis anlamina gelir cunku x1 ve x2 olusturulurken 0-100 arasında bir deger olusur
                //yani hicbir sekilde negatif olmaz. Ayni sekilde y degeri mutlak degeri alindigi icin o da negatif
                //olamaz. Sadece siilinme kosulunda negatif olma durumu vardir. Bu da silinen indeks degerlerini
                //tutmak icin epey mantikli bir yoldur.
                if(egitim[0][j]!=-1 && egitim[1][j]!=-1 && egitim[2][j]!=-1)
                {
                    //fonksiyonlar tek tek gonderilir en sonda bu 5 deger teker teker tutulur.
                    fonksiyon1(egitim[i][j],egitim[2][j]);
                    fonksiyon2(egitim[i][j]);
                    fonksiyon3(egitim[2][j]);
                    fonksiyon4(egitim[i][j]);
                    fonksiyon5(egitim[2][j]);
                }
            }
            //a degeri n*(x*y)toplam degerinin sonucunu ifade eder. 140-silinenVeri yapılma sebebi bu isleme dahil
            //edilen deger sayisini bulmaktir. yani n degerini sembolize eder. fonksiyonelIslemler[0] (x*y)toplam
            //degeri sonucunda elde edilmis sayiyi tutar.
            a=(140-silinenVeri)*fonksiyonelIslemler[0];

            //b degeri (y)toplam*(x)toplam degerinin sonucunu tutar. fonksiyonelIslemler1 (x)toplamı,
            //fonksiyonelIslemler2 ise (y) toplam degerini tutar.
            b=fonksiyonelIslemler[1]*fonksiyonelIslemler[2];

            //c degeri n*(x^2)toplam-((x)toplam)^2 degerini tutar. fonksiyonelislemler3 (x^2)toplam degerini tutar.
            //((x)toplam)^2 elde etemk icin iki adet (x)toplam degerini carpmak yeterlidir.
            c=(140-silinenVeri)*fonksiyonelIslemler[3]-(fonksiyonelIslemler[1]*fonksiyonelIslemler[1]);

            //d degeri n*(y^2)toplam-((y)toplam)^2 degerini tutar. fonksiyonelislemler4 (y^2)toplam degerini tutar.
            //((y)toplam)^2 elde etmek icin iki adet (y)toplam degerini carpmak yeterlidir.
            d=(140-silinenVeri)*fonksiyonelIslemler[4]-(fonksiyonelIslemler[2]*fonksiyonelIslemler[2]);

            //e degeri c ile d nin carpiminin karekoku degerini alir. Bu degeri alma sebebi aslında e degeri formuldeki
            // payda degeri elde edilmesidir. Boylece formuldeki karmasadan biraz daha kurtulmus olduk.
            e=sqrt(c*d);

            //korelasyonkatsayisina ilk once a degeri eklenir.
            korelasyonKatsayisi[i]+=a;

            //daha sonra b degeri cıkarilir ve bu a-b degeri yapar.
            korelasyonKatsayisi[i]-=b;

            //sonrasinda e degerine bolunur ve bu da (a-b)/e yapar. Bu sayede formul olabildigince sadelesmis olur.
            korelasyonKatsayisi[i]/=e;

            //degerler ekrana yazdirilir.
            System.out.println((i+1)+". Korelasyon Katsayısı:"+korelasyonKatsayisi[i]);
        }

        //eger ki x1 degerinin korelasyon katsayisi x2 nin korelasyon katsayısından kucuk veya esitse kullanim
        //true degeri alarak x2 degerinin korelasyon katsayisinin kullanilmasini saglar. Eger bu kosul saglanmazsa
        //x1 degerinin korelasyon katsayisi kullanilir.
        kullanim= abs(korelasyonKatsayisi[0]) <= abs(korelasyonKatsayisi[1]);

        //dogruysa burada fonksiyonel islemler daha sonra kullanilmak uzere sifirlanir.
        fonksiyonelIslemler[0]=0;
        fonksiyonelIslemler[1]=0;
        fonksiyonelIslemler[2]=0;
        fonksiyonelIslemler[3]=0;
        fonksiyonelIslemler[4]=0;

        //kullanim true veya false olup olmadigi kontrol ediliyor
        if(kullanim)
        {
            //eger dogruysa x2 degeri islemlerde kullanilir.
            for(int j=0;j<140;j++)
            {
                //egitim verilerinden ucude -1 olup olmadigi kontrol ediliyor.
                if(egitim[0][j]!=-1 && egitim[1][j]!=-1 && egitim[2][j]!=-1)
                {
                    //burada fonksiyonelislem sonuclari hesaplanir. Bunlar ileri de kullanilacaktir.
                    fonksiyon1(egitim[1][j],egitim[2][j]);
                    fonksiyon2(egitim[1][j]);
                    fonksiyon3(egitim[2][j]);
                    fonksiyon4(egitim[1][j]);
                    fonksiyon5(egitim[2][j]);
                }
            }
        }
        else
        {
            //eger dogru degilse x1 degeri islemlerde kullanilir.
            for(int j=0;j<140;j++)
            {
                //egitim verilerinden ucude -1 olup olmadigi kontrol ediliyor.
                if(egitim[0][j]!=-1 && egitim[1][j]!=-1 && egitim[2][j]!=-1)
                {
                    //burada fonksiyonelislem sonuclari hesaplanir. Bunlar ileri de kullanilacaktir.
                    fonksiyon1(egitim[0][j],egitim[2][j]);
                    fonksiyon2(egitim[0][j]);
                    fonksiyon3(egitim[2][j]);
                    fonksiyon4(egitim[0][j]);
                    fonksiyon5(egitim[2][j]);
                }
            }
        }


    }


    //y=ax+b olusturmak icin sirayla a b ve y degerleri bulunur. Bu degerler egitim modeline gore hesaplanir.
    public static void regresyonModeliOlusturma()
    {
        //eger kisi korelasyon hesabi yapmayip buraya tiklarsa korelasyon hesabi yapilmadigi icin ortaya sonuc
        //cikamaz. Bu yuzden buraya geldiginde ilk once korelasyon hesaplayip sonra buraya gelecek.
        korelasyonKatsayisiHesaplama();

        //a degeri normalde a=(y)aritmetik-b*(x)aritmetik ile hesaplanir. Fakat islemleri basitlestirmek icin
        //(y) aritmetik yerine (y)toplam/n ve (x) aritmetik yerine (x)toplam/n degeri yazilarak bir denklem elde
        //edilmistir. Bu denklem asagida ki sekilde ilerlemektedir.

        //ilk once (y)toplam*(x^2)toplam carpilir.
        regresyonA=fonksiyonelIslemler[2]*fonksiyonelIslemler[3];

        //daha sonra (x)toplam*(x*y)toplam cıkarilir
        regresyonA-=(fonksiyonelIslemler[1]*fonksiyonelIslemler[0]);

        //en sonunda n*(x^2)toplam-((x)toplam)^2 si ile bolunur ve a degeri bulunmus olur.
        regresyonA/=((140-silinenVeri)*(fonksiyonelIslemler[3])-(fonksiyonelIslemler[1]*fonksiyonelIslemler[1]));

        //b degeri icin ilk once n*(x*y)toplam degeri elde edilir.
        regresyonB=(140-silinenVeri)*fonksiyonelIslemler[0];

        //daha sonra (x)toplam*(y)toplamdan cikartilir.
        regresyonB-=(fonksiyonelIslemler[1]*fonksiyonelIslemler[2]);

        //en sonunda n*(x^2)toplam-((x)toplam)^2 degerinden bolunur.
        regresyonB/=((140-silinenVeri)*fonksiyonelIslemler[3]-(fonksiyonelIslemler[1]*fonksiyonelIslemler[1]));

        //burada regresyonY degeri hesaplanir.
        for(int i=0;i<140;i++)
        {
            //kullanim dogrusya x2 degerleri kullanilarak islem yapilir.
            if(kullanim)
            {
                //eger ucu de -1 e esit degilse isleme devam edilir.
                if(egitim[0][i]!=-1 && egitim[1][i]!=-1 && egitim[2][i]!=-1)
                {
                    //teker teker regresyonY deger bulunur.
                    regresyonEgitimY[i]=regresyonA+regresyonB*egitim[1][i];
                }
            }
            //eger dogru degilse x1 degerleri ile hessaplanir.
            else
            {
                //eger ucu de -1 e esit degilse isleme devam edilir.
                if(egitim[0][i]!=-1 && egitim[1][i]!=-1 && egitim[2][i]!=-1)
                {
                    //teker teker regresyonY deger bulunur.
                    regresyonEgitimY[i]=regresyonA+regresyonB*egitim[0][i];
                }
            }
            //Sistemde hem regresyonY degerleri hem de normal Y degerleri yazilir.
            System.out.println("Regresyon Eğitim Y:"+regresyonEgitimY[i]+" Y:"+egitim[2][i]);
        }
    }

    //regresyon modeli olusturulduguna gore sirada SSE degeri var. Yukarida ki tum islemler egitim islemlerine gore
    //yapildigi icin SSE degeride ilk once egitim bulunmaya calisilir.
    public static void egitimSSEDegeriHesaplama()
    {
        //eger kullanici regresyon yapmamissa yapmasi icin o fonksiyona gonderilir.
        regresyonModeliOlusturma();

        //ayni sekilde islem karmasikligini onlemek icin hem egitim hem de test SSE leri sifirlanir.
        egitimSSE=0;
        veriSSE=0;

        for(int i=0;i<140;i++)
        {
            //egitim SSE sonucu bulunur.
            egitimSSE+= pow(egitim[2][i]-regresyonEgitimY[i],2);
        }

        //egitimSSE degeri ekrana yazdirilir.
        System.out.println("EgitimSSE degeri:"+egitimSSE);
    }

    //bunca yapilmis tum hesaplamalar sonucunda degerle ekrana yazdirilir.
    public static void hesaplananDegerleriGoruntuleme()
    {
        //eger egitimSSE hesaplanmamissa hesaplanmasi icin gider.
        egitimSSEDegeriHesaplama();

        //her iki korelasyon kat sayisi ekrana yazdirilir.
        for(int i=0;i<2;i++)
        {
            System.out.println((i+1)+". Korelasyon Katsayısı:"+korelasyonKatsayisi[i]);
        }

        //regresyon denklemi gosterilir.
        System.out.println("Regresyon modeli: y="+regresyonA+"*x+"+regresyonB);

        //regresyonY degerleri ve normal Y degerleri yazdirilir.
        for(int i=0;i<140;i++)
        {
            System.out.println("Regresyon Eğitim Y:"+regresyonEgitimY[i]+"\t normal Y:"+egitim[2][i]);
        }

        //egitimSSE degeri ekrana yazdirilir.
        System.out.println("Eğitim SSE Değeri:"+egitimSSE);
    }

    public static void testVerileriSinama()
    {
        //model olusmamissa zaten hesaplama olmaz. bu yuzden gitmesi gereklidir.
        regresyonModeliOlusturma();

        //karmasiklik olmamasi icin SSE degerleri sifirlanir.
        veriSSE=0;
        egitimSSE=0;

        //regresyon modeli yazdirilir.
        System.out.println("Regresyon modeli: y="+regresyonA+"*x+"+regresyonB);

        //test verileri icin regresyonY degerii hesaplanir.
        for(int i=0;i<60;i++)
        {
            //kullanim dogruysa x2 degeri kullanilir.
            if(kullanim)
            {
                //regresyonY degeri hesaplanir.
                if(test[0][i]!=-1 && test[1][i]!=-1 && test[2][i]!=-1)
                {
                    regresyonVeriY[i]=regresyonA+regresyonB*test[1][i];
                }
            }
            //kullanim yanlissa x1 degeri kullanilir.
            else
            {
                //regresyonY degeri hesaplanir.
                if(test[0][i]!=-1 && test[1][i]!=-1 && test[2][i]!=-1)
                {
                    regresyonVeriY[i]=regresyonA+regresyonB*test[0][i];
                }
            }
            //regresyonY ile normal Y degeri yazdirilir.
            System.out.println("Regresyon Veri Y:"+regresyonVeriY[i]+" Y:"+test[2][i]);
        }

        //veriSSE hesaplanir.
        for(int i=0;i<60;i++)
        {
            veriSSE+= pow(test[2][i]-regresyonVeriY[i],2);
        }

        //veriSSE degeri yazdirilir.
        System.out.println("Veri SSE Değeri:"+veriSSE);
    }

    //modelleme islemlerin menusu burasidir. burada herhangi bir modelleme islemi yapilacaksa kullanilir.
    public static void modellemeIslemleri()
    {
        //secim degeri tutulur.
        int secim;

        //secim degeri alinir.
        System.out.println("""
                Yapılacak işlemi seçiniz.
                1-Korelasyon Katsayı Hesaplama
                2-Regresyon Modelini Oluşturma
                3-Egitim Verileri için SSE Değerini Bulma
                4-Egitim Verileri için Hesaplanan Değerleri Görüntüleme
                5-Test Verileri ile Değerleri Elde Etme ve Hesaplanan Değerleri Görüntüleme
                6-Menüye geri dönme
                Secim:""");
        Scanner girdi=new Scanner(System.in);
        secim=girdi.nextInt();

        switch (secim)
        {
            //yapilan secime gore fonksiyona gidilir.
            case 1 -> korelasyonKatsayisiHesaplama();
            case 2 -> regresyonModeliOlusturma();
            case 3 -> egitimSSEDegeriHesaplama();
            case 4 -> hesaplananDegerleriGoruntuleme();
            case 5 -> testVerileriSinama();
            case 6 -> Menu();

            //yapilan secim yanlissa uyari verir ve modellemeIslemlerie geri doner.
            default -> System.out.println("Yanlış Değer girdiniz. Lütfen Tekrar Deneyin.");
        }

        modellemeIslemleri();
    }

    //ana islemlerin hepsi burada yapilir. Buradan verii ile iletisime girilmek isteniliyorsa girilir. Ya da olusan
    //verilere modelleme islemeri yapilir. veya en sonunda islemler bitince uygulamadan cikilir.
    public static void Menu()
    {
        //secim degeri alinir.
        int secim;

        //secim degeri alinir.
        System.out.println("""
                Lütfen seçim yapınız.
                1-Veri İşlemleri
                2-Modelleme İşlemleri
                3-Çıkış İşlemi
                Secim:""");
        Scanner girdi=new Scanner(System.in);
        secim=girdi.nextInt();

        switch (secim) {
            //secime gore fonksiyona gidilir.
            case 1 -> veriIslemleri();
            case 2 -> modellemeIslemleri();
            case 3 -> System.exit(0);

            //eger deger yanlissa uyari verilir ve Menuya geri donulur.
            default -> System.out.println("Yanlış Değer girdiniz. Lütfen Tekrar Deneyin.");
        }
        Menu();
    }
}
