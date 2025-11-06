import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Sınıf Adı: KapsamliMaaşVeGiderAnalizi
 * Amaç: Bir çalışanın brüt maaş bilgisinden hareketle, çalışanın eline geçen net ücretini, 
 * işveren tarafından devlete ödenen yükümlülükleri ve personelin işverene toplam maliyetini
 * (giderini) analiz eden profesyonel bir Java programıdır.
 *
 * Yasal Durum Varsayımı: 2025 Mali Yılı Oranları.
 */
public class KapsamliMaaşVeGiderAnalizi {

    // --- 1. YASAL ORANLAR VE SABİTLER (İŞVEREN VE İŞÇİ PAYLARI) ---

    // İşveren Payları
    private static final double SGK_PRIMI_ORANI_ISVEREN = 0.205; // %20.5
    private static final double ISSIZLIK_SIGORTASI_ORANI_ISVEREN = 0.02; // %2
    
    // İşçi Payları
    private static final double SGK_PRIMI_ORANI_ISCI = 0.14; // %14
    private static final double ISSIZLIK_SIGORTASI_ORANI_ISCI = 0.01; // %1

    // Vergi Oranları
    private static final double DAMGA_VERGISI_ORANI = 0.00759; // Binde 7.59
    private static final double GELIR_VERGISI_ORANI = 0.15; // Basit modelleme için %15
    
    // SGK matrah tavanı
    private static final double SGK_MATRAH_TAVANI = 60000.0; 

    /**
     * İşverenin brüte ek olarak ödediği primleri hesaplar. (İşveren Gideri)
     *
     * @param brutMaas Personelin aylık brüt ücreti.
     * @return İşverenin ödemesi gereken toplam prim tutarı.
     */
    public static double hesaplaIsverenPrimi(double brutMaas) {
        double matrah = Math.min(brutMaas, SGK_MATRAH_TAVANI);
        
        double sgkPrimi = matrah * SGK_PRIMI_ORANI_ISVEREN;
        double issizlikPrimi = matrah * ISSIZLIK_SIGORTASI_ORANI_ISVEREN;
        
        return sgkPrimi + issizlikPrimi;
    }
    
    /**
     * Çalışanın brüt maaşından kesilerek devlete aktarılacak olan tüm kesintileri (vergiler ve işçi primleri) hesaplar.
     * Bu aynı zamanda çalışanın net maaşını bulmak için de kullanılır.
     *
     * @param brutMaas Personelin aylık brüt ücreti.
     * @return Çalışandan kesilen toplam vergi ve prim tutarı.
     */
    public static double hesaplaIsciToplamKesinti(double brutMaas) {
        // İşçi Sigorta Payı Hesaplama
        double sigortaMatrahi = Math.min(brutMaas, SGK_MATRAH_TAVANI);
        double isciSigorta = sigortaMatrahi * (SGK_PRIMI_ORANI_ISCI + ISSIZLIK_SIGORTASI_ORANI_ISCI);

        // Gelir Vergisi Hesaplama
        double gvMatrahi = brutMaas - isciSigorta;
        double gelirVergisi = gvMatrahi > 0 ? gvMatrahi * GELIR_VERGISI_ORANI : 0;
        
        // Damga Vergisi Hesaplama
        double damgaVergisi = brutMaas * DAMGA_VERGISI_ORANI;

        return isciSigorta + gelirVergisi + damgaVergisi;
    }

    /**
     * Ana Metot: Kullanıcıdan brüt maaşı alır ve Çalışan ile İşveren Gideri detaylarını raporlar.
     */
    public static void main(String[] args) {
        try (Scanner girdiAlıcı = new Scanner(System.in)) {
            System.out.println("--- 📊 KAPSAMLI MAAŞ VE GİDER ANALİZ RAPORU 📊 ---");
            System.out.println("-------------------------------------------------");

            double brutMaas = 0;
            boolean girdiBasarili = false;

            // Girdi Doğrulama
            while (!girdiBasarili) {
                System.out.print("Lütfen aylık BRÜT MAAŞ miktarını (TL) giriniz: ");
                try {
                    brutMaas = girdiAlıcı.nextDouble();
                    if (brutMaas >= 0) {
                        girdiBasarili = true;
                    } else {
                        System.out.println("❌ HATA: Ücret sıfırdan küçük olamaz.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ HATA: Lütfen sadece sayısal bir değer giriniz.");
                    girdiAlıcı.next(); 
                }
            }

            // --- Hesaplamalar ---
            double isciToplamKesinti = hesaplaIsciToplamKesinti(brutMaas);
            double netMaas = brutMaas - isciToplamKesinti;
            double isverenPrimi = hesaplaIsverenPrimi(brutMaas);
            
            // Personel Gideri (Toplam İşveren Maliyeti)
            double personelGideri = brutMaas + isverenPrimi;


            // --- ÖZEL RAPORLAMA FORMATI (ÇALIŞAN VE İŞVEREN ODAKLI) ---

            System.out.println("\n--- 📝 GİDER VE MAAŞ BİLGİLERİ ---");
            System.out.printf("  GİRİLEN BRÜT ÜCRET:         %,.2f TL%n", brutMaas);
            System.out.println("----------------------------------------------");
            
            // A) ÇALIŞANIN ALACAĞI (MAAŞ) BİLGİSİ
            System.out.println("A) ÇALIŞANIN ELİNE GEÇEN NET ÜCRET (MAAŞ)");
            System.out.printf("   1. Çalışana Ödenen NET MAAŞ:   %,.2f TL%n", netMaas);
            System.out.printf("   2. Çalışandan Kesilen Toplam:  %,.2f TL%n", isciToplamKesinti);
            System.out.println("----------------------------------------------");
            
            // B) İŞVERENİN TOPLAM ÖDEMESİ (GİDER) BİLGİSİ
            System.out.println("B) İŞVERENİN TOPLAM PERSONEL GİDERİ");
            System.out.printf("   1. Brüt Ücret Masrafı:         %,.2f TL%n", brutMaas);
            System.out.printf("   2. İşverenin Ek Prim Gideri:   %,.2f TL%n", isverenPrimi);
            System.out.println("----------------------------------------------");
            System.out.printf("   TOPLAM İŞVEREN GİDERİ (MALİYET): %,.2f TL%n", personelGideri);
            System.out.println("----------------------------------------------");

            // Kontrol ve Ek Bilgi
            System.out.println("\nC) EK ANALİZ BİLGİSİ");
            System.out.printf("   Devlete Aktarılan Vergi/Prim:  %,.2f TL%n", (personelGideri - netMaas));
            System.out.printf("   (Net Maaş + Devlete Ödenen) = Toplam Gider Kontrolü%n");


        } catch (Exception e) {
            System.out.println("Beklenmedik bir sistem hatası oluştu: " + e.getMessage());
        }
    }
}
