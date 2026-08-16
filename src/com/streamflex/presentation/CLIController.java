package com.streamflex.presentation;

import com.streamflex.data.ContentInventory;
import com.streamflex.domain.decorator.*;
import com.streamflex.domain.model.Content;
import com.streamflex.domain.model.Subscriber;
import com.streamflex.domain.state.*;
import com.streamflex.domain.strategy.*;
import com.streamflex.exception.AccessDeniedException;

import java.util.Collection;
import java.util.Scanner;

/**
 * Terminal tabanlı kullanıcı arayüzü kontrolcüsü (Presentation & Routing Katmanı).
 * Kullanıcı girdilerini işler ve iş mantığını domain nesnelerine devreder.
 * "God Object" anti-paternini engellemek için iş mantığından kesinlikle izole edilmiştir.
 * Runtime exception'ları yakalar ve uygulamanın çökmesini önler.
 */
public class CLIController {

    private final Scanner scanner;
    private final ContentInventory inventory;
    private Subscriber currentUser;

    /**
     * CLIController oluşturur.
     *
     * @param scanner Kullanıcı girdi okuyucu
     */
    public CLIController(Scanner scanner) {
        this.scanner = scanner;
        this.inventory = ContentInventory.getInstance();
        this.currentUser = null;
    }

    /**
     * Ana uygulama döngüsünü başlatır.
     * Sonsuz döngüde (while loop) menüyü gösterir ve kullanıcı girdilerini işler.
     */
    public void run() {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        handleRegister();
                        break;
                    case "2":
                        handleCustomizeSubscription();
                        break;
                    case "3":
                        handlePayment();
                        break;
                    case "4":
                        handlePlayback();
                        break;
                    case "5":
                        handleStateChange();
                        break;
                    case "6":
                        handleListContents();
                        break;
                    case "7":
                        handleViewProfile();
                        break;
                    case "0":
                        running = false;
                        System.out.println("\n👋 StreamFlex'ten çıkılıyor. İyi günler!");
                        break;
                    default:
                        System.out.println("⚠  Geçersiz seçim. Lütfen menüden bir numara girin.");
                }
            } catch (AccessDeniedException e) {
                System.out.println("\n" + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠  Bir hata oluştu: " + e.getMessage());
            }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  1. HESAP OLUŞTURMA
    // ══════════════════════════════════════════════════════════════

    /**
     * Yeni kullanıcı kaydı oluşturur.
     * Mevcut bir hesap varsa onay ister — sessiz veri kaybını engeller.
     */
    private void handleRegister() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       📝 HESAP OLUŞTURMA            ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Mevcut oturum kontrolü — sessiz overwrite engelleme
        if (currentUser != null) {
            System.out.println("⚠  Zaten aktif bir hesabınız var: " + currentUser.getUsername());
            System.out.print("   Yeni hesap oluşturmak mevcut hesabı silecektir. Devam? (e/h): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("e")) {
                System.out.println("   İşlem iptal edildi.");
                return;
            }
        }

        System.out.print("Kullanıcı ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Kullanıcı Adı: ");
        String username = scanner.nextLine().trim();

        if (id.isEmpty() || username.isEmpty()) {
            System.out.println("⚠  ID ve kullanıcı adı boş olamaz.");
            return;
        }

        currentUser = new Subscriber(id, username);
        System.out.println("✅ Hesap başarıyla oluşturuldu: " + username);
        System.out.println("   Hesap Durumu: " + currentUser.getState().getStateName());
    }

    // ══════════════════════════════════════════════════════════════
    //  2. ABONELİK ÖZELLEŞTİRME (Decorator Pattern)
    // ══════════════════════════════════════════════════════════════

    /**
     * Kullanıcının abonelik planını özelleştirmesini sağlar.
     * Decorator Pattern ile dinamik olarak eklentiler eklenir.
     */
    private void handleCustomizeSubscription() {
        if (!ensureUserLoggedIn()) return;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    🎯 ABONELİK ÖZELLEŞTİRME        ║");
        System.out.println("║       (Decorator Pattern)            ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Temel plan seçimi
        System.out.println("\n📦 Temel Planlar:");
        System.out.println("   [1] Standart Plan  — ₺49.99");
        System.out.println("   [2] Premium Plan   — ₺79.99");
        System.out.print("\nPlan seçin: ");
        String planChoice = scanner.nextLine().trim();

        SubscriptionComponent plan;
        switch (planChoice) {
            case "1":
                plan = new BasePlan("Standart Plan", 49.99);
                break;
            case "2":
                plan = new BasePlan("Premium Plan", 79.99);
                break;
            default:
                System.out.println("⚠  Geçersiz seçim.");
                return;
        }

        // Eklenti döngüsü — Decorator ile sarmalama
        boolean addingAddons = true;
        while (addingAddons) {
            System.out.println("\n🔧 Eklenti Ekle (mevcut plan: " + plan.getDescription() + ")");
            System.out.printf("   Güncel Fiyat: ₺%.2f%n", plan.getCost());
            System.out.println("\n   [1] 4K Ultra HD         (+₺20.00)");
            System.out.println("   [2] Reklamsız İzleme    (+₺15.00)");
            System.out.println("   [3] Çevrimdışı İzleme   (+₺10.00)");
            System.out.println("   [0] Eklenti eklemeyi bitir");
            System.out.print("\nSeçim: ");
            String addonChoice = scanner.nextLine().trim();

            switch (addonChoice) {
                case "1":
                    if (plan.getFeatures().stream().anyMatch(f -> f.contains("4K"))) {
                        System.out.println("   ⚠  4K Ultra HD zaten eklenmiş.");
                    } else {
                        plan = new FourKResolution(plan);
                        System.out.println("   ✅ 4K Ultra HD eklendi.");
                    }
                    break;
                case "2":
                    if (plan.getFeatures().stream().anyMatch(f -> f.contains("Reklamsız"))) {
                        System.out.println("   ⚠  Reklamsız İzleme zaten eklenmiş.");
                    } else {
                        plan = new AdFree(plan);
                        System.out.println("   ✅ Reklamsız İzleme eklendi.");
                    }
                    break;
                case "3":
                    if (plan.getFeatures().stream().anyMatch(f -> f.contains("Çevrimdışı"))) {
                        System.out.println("   ⚠  Çevrimdışı İzleme zaten eklenmiş.");
                    } else {
                        plan = new OfflineMode(plan);
                        System.out.println("   ✅ Çevrimdışı İzleme eklendi.");
                    }
                    break;
                case "0":
                    addingAddons = false;
                    break;
                default:
                    System.out.println("   ⚠  Geçersiz seçim.");
            }
        }

        currentUser.setPlan(plan);

        // Özet
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│         📋 ABONELİK ÖZETİ           │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│ Plan : " + plan.getDescription());
        System.out.println("│ Özellikler:");
        for (String feature : plan.getFeatures()) {
            System.out.println("│   • " + feature);
        }
        System.out.printf("│ Toplam Maliyet: ₺%.2f%n", plan.getCost());
        System.out.println("└──────────────────────────────────────┘");
    }

    // ══════════════════════════════════════════════════════════════
    //  3. ÖDEME İŞLEMİ (Strategy Pattern)
    // ══════════════════════════════════════════════════════════════

    /**
     * Kullanıcının seçtiği ödeme yöntemi ile ödeme işlemini gerçekleştirir.
     * Strategy Pattern ile ödeme algoritması runtime'da belirlenir.
     * Başarılı ödeme → ActiveState, başarısız ödeme → PendingPaymentState.
     */
    private void handlePayment() {
        if (!ensureUserLoggedIn()) return;

        if (currentUser.getPlan() == null) {
            System.out.println("⚠  Önce bir abonelik planı seçmelisiniz. (Menü 2)");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        💰 ÖDEME İŞLEMİ              ║");
        System.out.println("║       (Strategy Pattern)             ║");
        System.out.println("╚══════════════════════════════════════╝");

        double totalCost = currentUser.getPlan().getCost();
        System.out.printf("\n   Ödenecek Tutar: ₺%.2f%n", totalCost);

        // Ödeme yöntemi seçimi
        System.out.println("\n   Ödeme Yöntemi:");
        System.out.println("   [1] 💳 Kredi Kartı");
        System.out.println("   [2] 🪙 Kripto Para");
        System.out.print("\nSeçim: ");
        String methodChoice = scanner.nextLine().trim();

        PaymentStrategy strategy;
        switch (methodChoice) {
            case "1":
                System.out.print("Kart Numarası: ");
                String cardNum = scanner.nextLine().trim();
                System.out.print("Kart Sahibi: ");
                String cardHolder = scanner.nextLine().trim();
                strategy = new CreditCardPayment(cardNum, cardHolder);
                break;
            case "2":
                System.out.print("Cüzdan Adresi: ");
                String wallet = scanner.nextLine().trim();
                strategy = new CryptoPayment(wallet);
                break;
            default:
                System.out.println("⚠  Geçersiz ödeme yöntemi.");
                return;
        }

        // Ödeme işlemi — Strategy Pattern
        System.out.println();
        boolean success = strategy.pay(totalCost);

        // State geçişi — ödeme sonucuna göre
        if (success) {
            currentUser.setState(new ActiveState());
            System.out.println("\n✅ Ödeme başarılı! Hesabınız aktif.");
        } else {
            currentUser.setState(new PendingPaymentState());
            System.out.println("\n❌ Ödeme reddedildi. Hesabınız 'Ödeme Bekliyor' durumuna alındı.");
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  4. İÇERİK İZLEME (State Pattern)
    // ══════════════════════════════════════════════════════════════

    /**
     * Kullanıcının içerik oynatma talebini işler.
     * Yetkilendirme mantığı CLIController'da değil, AccountState nesnesinde kalır.
     * Erişim reddedilirse AccessDeniedException fırlatılır ve yakalanır.
     */
    private void handlePlayback() {
        if (!ensureUserLoggedIn()) return;

        if (currentUser.getPlan() == null) {
            System.out.println("⚠  Önce bir abonelik planı seçmelisiniz. (Menü 2)");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        ▶  İÇERİK İZLEME             ║");
        System.out.println("║        (State Pattern)               ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Mevcut içerikleri listele
        Collection<Content> contents = inventory.getAllContents();
        if (contents.isEmpty()) {
            System.out.println("⚠  Envanterde hiç içerik bulunmuyor.");
            return;
        }

        System.out.println("\n📺 Mevcut İçerikler:");
        for (Content c : contents) {
            System.out.println("   " + c.getMetadata());
        }

        System.out.print("\nİzlemek istediğiniz içeriğin ID'si: ");
        String contentId = scanner.nextLine().trim();

        Content content = inventory.getContent(contentId);
        if (content == null) {
            System.out.println("⚠  İçerik bulunamadı: " + contentId);
            return;
        }

        // State Pattern — Subscriber kendi state'ine devreder
        System.out.println("\n🔍 Yetkilendirme kontrolü yapılıyor...");
        System.out.println("   Hesap Durumu: " + currentUser.getState().getStateName());

        boolean allowed = currentUser.requestPlayback(content);

        if (allowed) {
            System.out.println("\n▶  Oynatılıyor: " + content.getTitle());
            System.out.println("   ████████████████████ 100%");
            System.out.println("   Keyifli seyirler! 🎬");
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  5. HESAP DURUMU DEĞİŞTİRME (State Geçişleri)
    // ══════════════════════════════════════════════════════════════

    /**
     * Kullanıcı hesabının durumunu manuel olarak değiştirmeyi sağlar.
     * Test ve demo senaryoları için kullanılır.
     */
    private void handleStateChange() {
        if (!ensureUserLoggedIn()) return;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    🔄 HESAP DURUMU DEĞİŞTİR         ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("\n   Mevcut Durum: " + currentUser.getState().getStateName());
        System.out.println("\n   Yeni Durum Seçin:");
        System.out.println("   [1] ✅ Aktif (ActiveState)");
        System.out.println("   [2] ⏸  Askıya Alınmış (SuspendedState)");
        System.out.println("   [3] ⏳ Ödeme Bekliyor (PendingPaymentState)");
        System.out.println("   [4] ❌ İptal Edilmiş (CancelledState)");
        System.out.print("\nSeçim: ");
        String stateChoice = scanner.nextLine().trim();

        switch (stateChoice) {
            case "1":
                currentUser.setState(new ActiveState());
                break;
            case "2":
                currentUser.setState(new SuspendedState());
                break;
            case "3":
                currentUser.setState(new PendingPaymentState());
                break;
            case "4":
                currentUser.setState(new CancelledState());
                break;
            default:
                System.out.println("⚠  Geçersiz seçim.");
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  6. İÇERİK ENVANTERİ LİSTELEME
    // ══════════════════════════════════════════════════════════════

    /**
     * Envanterdeki tüm içerikleri listeler.
     * Türe göre filtreleme seçeneği sunar.
     */
    private void handleListContents() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      📚 İÇERİK ENVANTERİ            ║");
        System.out.println("╚══════════════════════════════════════╝");

        Collection<Content> contents = inventory.getAllContents();
        if (contents.isEmpty()) {
            System.out.println("⚠  Envanterde hiç içerik bulunmuyor.");
            return;
        }

        System.out.println("\n   Toplam İçerik: " + inventory.getContentCount());
        System.out.println("   ─────────────────────────────────");
        for (Content c : contents) {
            System.out.println("   " + c.getMetadata());
        }
        System.out.println("   ─────────────────────────────────");

        System.out.print("\nTüre göre filtrelemek ister misiniz? (tür adı / hayır): ");
        String genreFilter = scanner.nextLine().trim();

        if (!genreFilter.equalsIgnoreCase("hayır") && !genreFilter.isEmpty()) {
            var filtered = inventory.searchByGenre(genreFilter);
            if (filtered.isEmpty()) {
                System.out.println("   Bu türde içerik bulunamadı: " + genreFilter);
            } else {
                System.out.println("\n   🔍 '" + genreFilter + "' türündeki içerikler:");
                for (Content c : filtered) {
                    System.out.println("   " + c.getMetadata());
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  7. PROFİL GÖRÜNTÜLEME
    // ══════════════════════════════════════════════════════════════

    /**
     * Mevcut kullanıcının profil bilgilerini görüntüler.
     */
    private void handleViewProfile() {
        if (!ensureUserLoggedIn()) return;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         👤 KULLANICI PROFİLİ         ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("   ID         : " + currentUser.getId());
        System.out.println("   Kullanıcı  : " + currentUser.getUsername());
        System.out.println("   Durum      : " + currentUser.getState().getStateName());

        if (currentUser.getPlan() != null) {
            System.out.println("   Plan       : " + currentUser.getPlan().getDescription());
            System.out.printf("   Maliyet    : ₺%.2f%n", currentUser.getPlan().getCost());
            System.out.println("   Özellikler :");
            for (String feature : currentUser.getPlan().getFeatures()) {
                System.out.println("     • " + feature);
            }
        } else {
            System.out.println("   Plan       : Henüz seçilmedi");
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  YARDIMCI METOTLAR
    // ══════════════════════════════════════════════════════════════

    /**
     * Kullanıcının giriş yapıp yapmadığını kontrol eder.
     *
     * @return Kullanıcı mevcutsa true
     */
    private boolean ensureUserLoggedIn() {
        if (currentUser == null) {
            System.out.println("⚠  Önce bir hesap oluşturmalısınız. (Menü 1)");
            return false;
        }
        return true;
    }

    /**
     * Hoş geldiniz banner'ını yazdırır.
     */
    private void printWelcomeBanner() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║     ███████╗████████╗██████╗ ███████╗ █████╗ ███╗   ███╗║");
        System.out.println("║     ██╔════╝╚══██╔══╝██╔══██╗██╔════╝██╔══██╗████╗ ████║║");
        System.out.println("║     ███████╗   ██║   ██████╔╝█████╗  ███████║██╔████╔██║║");
        System.out.println("║     ╚════██║   ██║   ██╔══██╗██╔══╝  ██╔══██║██║╚██╔╝██║║");
        System.out.println("║     ███████║   ██║   ██║  ██║███████╗██║  ██║██║ ╚═╝ ██║║");
        System.out.println("║     ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝║");
        System.out.println("║                    F  L  E  X                        ║");
        System.out.println("║                                                      ║");
        System.out.println("║     İçerik ve Abonelik Yönetim Sistemi               ║");
        System.out.println("║     DEÜ — OOAD Dönem Projesi (Faz 3)                ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    /**
     * Ana menüyü yazdırır.
     */
    private void printMainMenu() {
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│            📋 ANA MENÜ               │");
        System.out.println("├──────────────────────────────────────┤");

        if (currentUser != null) {
            System.out.println("│  👤 " + currentUser.getUsername()
                    + " | Durum: " + currentUser.getState().getStateName());
            System.out.println("├──────────────────────────────────────┤");
        }

        System.out.println("│  [1] 📝 Hesap Oluştur               │");
        System.out.println("│  [2] 🎯 Abonelik Özelleştir          │");
        System.out.println("│  [3] 💰 Ödeme Yap                    │");
        System.out.println("│  [4] ▶  İçerik İzle                  │");
        System.out.println("│  [5] 🔄 Hesap Durumu Değiştir        │");
        System.out.println("│  [6] 📚 İçerik Envanteri             │");
        System.out.println("│  [7] 👤 Profil Görüntüle              │");
        System.out.println("│  [0] 🚪 Çıkış                        │");
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Seçiminiz: ");
    }
}
