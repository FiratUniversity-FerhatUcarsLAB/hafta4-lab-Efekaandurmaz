/*
 * Ad Soyad: Efe Durmaz
 * Ogrenci No: 250541106
 * Tarih: 06.11.2025
 * Aciklama: Gorev 1 - Ogrenci Bilgi Sistemi
 * * Bu program kullanicidan ogrenci bilgilerini alir ve
 * duzenli bir formatta ekrana yazdirir.
 * Diğer java dosyalarının başında da bu örnek formattaki gibi kısa bilgi giriniz
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class OgrenciBilgi {
    public static void main(String[] args) {
        // Scanner objesi olusturun. try-with-resources ile otomatik kapanmasini saglayin.
        try (Scanner girdiAl = new Scanner(System.in)) {
            
            // Degisken tanimlamalari
            String ad, soyad;
            int ogrenciNo, yas;
            double gpa;
            
            // Kullanicidan bilgileri alin
            System.out.println("=== OGRENCI BILGI SISTEMI ===");
            System.out.println("Lütfen istenen bilgileri giriniz:");
            
            // Ad
            System.out.print("Adınız: ");
            ad = girdiAl.nextLine();
            
            // Soyad
            System.out.print("Soyadınız: ");
            soyad = girdiAl.nextLine();
            
            // Ogrenci No
            System.out.print("Öğrenci Numaranız (Sayısal): ");
            // Hata kontrolü eklenmiştir.
            try {
                ogrenciNo = girdiAl.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("HATA: Öğrenci numarası sayısal olmalıdır. Varsayılan değer atanmıştır.");
                girdiAl.next(); // Hatalı girdiyi temizle
                ogrenciNo = 0; 
            }
            
            // Yas
            System.out.print("Yaşınız (Sayısal): ");
            try {
                yas = girdiAl.nextInt();
            } catch (InputMismatchException e) {
                 System.out.println("HATA: Yaş sayısal olmalıdır. Varsayılan değer atanmıştır.");
                 girdiAl.next();
                 yas = 0; 
            }
            
            // GPA (Ortalama)
            System.out.print("Ortalamanız (GPA, ör: 3.18): ");
            try {
                 gpa = girdiAl.nextDouble();
            } catch (InputMismatchException e) {
                 System.out.println("HATA: GPA ondalıklı sayı olmalıdır. Varsayılan değer atanmıştır.");
                 girdiAl.next();
                 gpa = 0.0;
            }
            
            // Bilgileri ekrana yazdirin
            System.out.println("\n=== OGRENCI BILGI SISTEMI RAPORU ===");
            
            // COZUMUNUZU BURAYA YAZIN
            System.out.println("Adı Soyadı        : " + ad + " " + soyad);
            System.out.println("Öğrenci Numarası  : " + ogrenciNo);
            System.out.println("Yaşı              : " + yas);
            System.out.printf("GPA (Ortalama)    : %.2f%n", gpa); // GPA'yı iki ondalık basamakla göster
            System.out.println("=====================================");

            // Scanner'i kapatin (try-with-resources yapısı otomatik olarak kapatır)
            
        } catch (Exception e) {
             System.out.println("Beklenmedik bir hata oluştu: " + e.getMessage());
        }
    }
}
