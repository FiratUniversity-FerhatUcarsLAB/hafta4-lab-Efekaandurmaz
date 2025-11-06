import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Proje Adı: DüzgünBeşgenÇözümleyici
 * Açıklama: Kullanıcıdan alınan kenar uzunluğuna göre düzgün bir beşgenin
 * çevresini ve alanını hesaplar. Geçersiz girdi kontrolü içerir.
 * Yazar: [Adınız Soyadınız]
 * Tarih: [Güncel Tarih]
 */
public class DuzgunBesgenCozumleyici {

    // 36 derecenin radyan karşılığı. Trigonometrik hesaplama için kullanılır.
    // 36° = Pi / 5
    private static final double RADYAN_ACI_36 = Math.PI / 5.0; 

    /**
     * Düzgün beşgenin çevre uzunluğunu hesaplar.
     * Çevre Formülü: Çevre = 5 * Kenar
     *
     * @param kenar Besgenin bir kenar uzunluğu (pozitif olmalıdır).
     * @return Hesaplanan çevre değeri.
     */
    public static double hesaplaCevre(double kenar) {
        return 5 * kenar;
    }

    /**
     * Düzgün beşgenin alanını hassas trigonometrik formülle hesaplar.
     * Alan Formülü: Alan = (5 * Kenar²) / (4 * tan(36°))
     *
     * @param kenar Besgenin bir kenar uzunluğu (pozitif olmalıdır).
     * @return Hesaplanan alan değeri.
     */
    public static double hesaplaAlan(double kenar) {
        // Alan = (5 * kenar^2) / (4 * tan(π/5)) formülü uygulanmıştır.
        return (5 * Math.pow(kenar, 2)) / (4 * Math.tan(RADYAN_ACI_36));
    }

    /**
     * Programın başlangıç noktası. Kullanıcı etkileşimini, girdi kontrolünü ve sonuç raporlamasını yönetir.
     */
    public static void main(String[] args) {
        // Kaynak sızıntısını önlemek için try-with-resources yapısı kullanılmıştır.
        try (Scanner girdiAlıcı = new Scanner(System.in)) {
            System.out.println("--- 💎 Düzgün Beşgen Analiz Programı (V1.0) 💎 ---");

            double kenarUzunlugu = 0;
            boolean girdiBasarili = false;

            // Geçerli (pozitif) bir kenar uzunluğu alınana kadar döngü devam eder.
            while (!girdiBasarili) {
                System.out.print("Lütfen beşgenin kenar uzunluğunu (sıfırdan büyük) giriniz: ");
                try {
                    kenarUzunlugu = girdiAlıcı.nextDouble();

                    // Geçerlilik Kontrolü: Kenar uzunluğu pozitif olmalıdır.
                    if (kenarUzunlugu > 0) {
                        girdiBasarili = true;
                    } else {
                        System.out.println("❌ HATA: Kenar uzunluğu mutlaka pozitif bir sayı olmalıdır. Tekrar deneyin.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ HATA: Lütfen sadece sayısal bir değer (tam sayı veya ondalıklı) giriniz.");
                    girdiAlıcı.next(); // Hatalı, sayısal olmayan girdiyi tüket.
                }
            }

            // Hesaplamaları Gerçekleştirme
            double cevre = hesaplaCevre(kenarUzunlugu);
            double alan = hesaplaAlan(kenarUzunlugu);

            // Sonuçları Detaylı Olarak Raporlama
            System.out.println("\n--- 📊 Hesaplama Sonuçları Raporu ---");
            System.out.printf("Girilen Kenar Uzunluğu (a): %.3f birim%n", kenarUzunlugu);
            System.out.println("------------------------------------");
            System.out.printf("1. Beşgenin Çevresi: %.4f birim%n", cevre);
            System.out.printf("2. Beşgenin Alanı: %.4f birim kare%n", alan);
            System.out.println("------------------------------------");
            System.out.println("Analiz başarıyla tamamlanmıştır. İyi çalışmalar dileriz.");

        } catch (Exception e) {
            System.out.println("Beklenmedik bir sistem hatası oluştu: " + e.getMessage());
        }
    }
}
