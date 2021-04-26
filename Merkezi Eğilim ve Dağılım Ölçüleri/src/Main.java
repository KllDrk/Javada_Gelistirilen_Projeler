import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Main {
    static int[][] Deger1=new int[4][365];
    static int[][] Deger2=new int[4][365];
    static float[]aritmetik_ortalama=new float[4];
    static float[]toplam=new float[4];
    static int[]tekrar_sayisi=new int[4];
    static int[] temp=new int[4];
    static  float[] varyans=new float[4];
    static float[] ortalama_sapma=new float[4];
    static float[] standart_sapma=new float[4];
    static float[] degisim_kat_sayisi=new float[4];
    static float[] ceyrekler_acikligi=new float[4];
    static File file = new File("Sonuclar.txt");

    public static void main(String[] args)  {
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    RastgeleDeger();
    SelectionSort();
    Menu();

    }
    public static void RastgeleDeger()
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                Random random=new Random();
                Deger1[i][j]=random.nextInt(100);
                Deger2[i][j]=Deger1[i][j];
            }
        }
    }
    public static void SelectionSort()
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                int min=j;
                for(int k=j+1;k<365;k++)
                {
                    if(Deger2[i][k]<Deger2[i][min])
                    {
                        min=k;
                    }
                }
                int temp=Deger2[i][j];
                Deger2[i][j]=Deger2[i][min];
                Deger2[i][min]=temp;
            }
        }
    }
    public  static  void Aritmetik_Ortalama_Hesaplama() {
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                toplam[i]+=Deger2[i][j];
            }
            aritmetik_ortalama[i]=toplam[i]/(float)365;
            System.out.println((i+1)+". Ülkenin aritmetik ortalamasi="+aritmetik_ortalama[i]);
            try {
                bufferedWriter.write((i+1)+". Ülkenin aritmetik ortalamasi="+aritmetik_ortalama[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void Ortanca_Deger_Bulma()
    {
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            System.out.println((i+1)+".Ulkede Ortanca Degeri="+Deger2[i][182]);
            try {
                bufferedWriter.write((i+1)+".Ulkede Ortanca Degeri="+Deger2[i][182]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Tepe_Deger_Bulma()
    {
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        int k;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                tekrar_sayisi[i]=0;
                for(k=0;k<365;k++)
                {
                    if (Deger2[i][j]==Deger2[i][k])
                    {
                        tekrar_sayisi[i]++;
                    }
                    else
                    {
                        continue;
                    }
                }
                if(tekrar_sayisi[i]>temp[i])
                {
                    temp[i]=tekrar_sayisi[i];
                }
            }
        }
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                tekrar_sayisi[i]=0;
                for(k=0;k<365;k++)
                {
                    if(Deger2[i][j]==Deger2[i][k])
                    {
                       tekrar_sayisi[i]++;
                    }
                }

                    if(tekrar_sayisi[i]==temp[i] && Deger2[i][j]!=Deger2[i][j+1])
                    {
                        System.out.println((i+1)+". Ulkede En cok tekrar eden sayi="+Deger2[i][j]+"\ttekrar sayisi="+tekrar_sayisi[i]);
                        try {
                            bufferedWriter.write((i+1)+". Ulkede En cok tekrar eden sayi="+Deger2[i][j]+"\ttekrar sayisi="+tekrar_sayisi[i]+"\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void Degisim_Araligi_Bulma()
    {
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            System.out.println((i+1)+". Ulkede Degisim Araligi="+(Deger2[i][364]-Deger2[i][0]));
            try {
                bufferedWriter.write((i+1)+". Ulkede Degisim Araligi="+(Deger2[i][364]-Deger2[i][0])+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Ortalama_Sapma_Bulma()
    {
        Aritmetik_Ortalama_Hesaplama();
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                if(Deger2[i][j]-aritmetik_ortalama[i]>=0)
                {
                    ortalama_sapma[i]+=Deger2[i][j]-aritmetik_ortalama[i];
                }
                else
                {
                    ortalama_sapma[i]+=aritmetik_ortalama[i]-Deger2[i][j];
                }
            }
            ortalama_sapma[i]=ortalama_sapma[i]/(float)365;
            System.out.println((i+1)+". Ulkede Ortalama Sapma="+ortalama_sapma[i]);
            try {
                bufferedWriter.write((i+1)+". Ulkede Ortalama Sapma="+ortalama_sapma[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Varyans_Bulma()
    {
        Aritmetik_Ortalama_Hesaplama();
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<365;j++)
            {
                varyans[i]+=(Deger2[i][j]-aritmetik_ortalama[i])*(Deger2[i][j]-aritmetik_ortalama[i]);
            }
            varyans[i]=varyans[i]/(float)364;
            System.out.println((i+1)+"Ulkenin Varyans Degeri="+varyans[i]);
            try {
                bufferedWriter.write((i+1)+"Ulkenin Varyans Degeri="+varyans[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  void Standart_Sapma_Bulma()
    {
        Varyans_Bulma();
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        float temp;
        for(int i=0;i<4;i++)
        {
            standart_sapma[i]=varyans[i]/2;
            do
            {
                temp=standart_sapma[i];
                standart_sapma[i]=(temp+(varyans[i]/temp))/2;
            }while((temp-standart_sapma[i])!=0);
            System.out.println((i+1)+". Ulkenin Standart Sapma Degeri="+standart_sapma[i]);
            try {
                bufferedWriter.write((i+1)+". Ulkenin Standart Sapma Degeri="+standart_sapma[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Degisim_Kat_Sayisi_Bulma()
    {
        Standart_Sapma_Bulma();
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            degisim_kat_sayisi[i]=(standart_sapma[i]*100)/aritmetik_ortalama[i];
            System.out.println((i+1)+". Ulkenin Degisim Kat Sayisi="+degisim_kat_sayisi[i]);
            try {
                bufferedWriter.write((i+1)+". Ulkenin Degisim Kat Sayisi="+degisim_kat_sayisi[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Ceyrekler_Acikligi_Bulma()
    {
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i=0;i<4;i++)
        {
            ceyrekler_acikligi[i]=Deger2[i][274]-((Deger2[i][91]-Deger2[i][90])/(float)2);
            System.out.println((i+1)+" Ulkenin Ceyrekler Acikligi="+ceyrekler_acikligi[i]);
            try {
                bufferedWriter.write((i+1)+" Ulkenin Ceyrekler Acikligi="+ceyrekler_acikligi[i]+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Menu()
    {

        int secim;
        System.out.println("Hangi Secenegi Sececeksiniz?");
        System.out.println("1-Degerleri yeniden olusturma");
        System.out.println("2-Aritmetik ortalama bulma");
        System.out.println("3-Ortanca degeri bulma");
        System.out.println("4-Tepe deger bulma");
        System.out.println("5-Degisim araligi bulma");
        System.out.println("6-Ortalama mutlak sapma Bulma");
        System.out.println("7-varyans Bulma");
        System.out.println("8-Standart Sapma Bulma");
        System.out.println("9-Degisim katsayisi Bulma");
        System.out.println("10-Ceyrekler acikligini bulma");
        System.out.println("11-Cikis");
        System.out.print("Secim=");
        Scanner girdi=new Scanner(System.in);
        secim=girdi.nextInt();
        switch (secim) {
            case 1 : {
                RastgeleDeger();
                SelectionSort();
                break;
            }
            case 2 : {
                Aritmetik_Ortalama_Hesaplama();
                break;
            }
            case 3 : {
                Ortanca_Deger_Bulma();
                break;
            }
            case 4 : {
                Tepe_Deger_Bulma();
                break;
            }
            case 5 : {
                Degisim_Araligi_Bulma();
                break;
            }
            case 6 : {
                Ortalama_Sapma_Bulma();
                break;
            }
            case 7 : {
                Varyans_Bulma();
                break;
            }
            case 8 : {
                Standart_Sapma_Bulma();
                break;
            }
            case 9 : {
                Degisim_Kat_Sayisi_Bulma();
                break;
            }
            case 10 : {
                Ceyrekler_Acikligi_Bulma();
                break;
            }
            case 11 : {
                System.exit(0);
                break;
            }
            default : {
                System.out.println("Yanlis Deger  Girdiniz.Lütfen Tekrar Deneyin.");
                break;
            }
        }
        for(int i=0;i<4;i++)
        {
            toplam[i]=0;
            tekrar_sayisi[i]=0;
            aritmetik_ortalama[i]=0;
            temp[i]=0;
            varyans[i]=0;
            ortalama_sapma[i]=0;
            standart_sapma[i]=0;
            degisim_kat_sayisi[i]=0;
            ceyrekler_acikligi[i]=0;
        }
        Menu();
    }
}


