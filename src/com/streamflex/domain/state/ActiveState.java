package com.streamflex.domain.state;

import com.streamflex.domain.model.Content;
import com.streamflex.domain.decorator.SubscriptionComponent;

/**
 * Aktif hesap durumu (State Pattern - Concrete State).
 * Kullanıcının hesabı aktifken içerik oynatma taleplerini değerlendirir.
 * Yaş kısıtlaması ve premium içerik kontrolü bu sınıf içinde yapılır.
 */
public class ActiveState implements AccountState {

    /**
     * Aktif durumdaki kullanıcının içerik oynatma talebini değerlendirir.
     * Yaş kısıtlamasını ve abonelik ayrıcalıklarını kontrol eder.
     *
     * @param content İzlenmek istenen içerik
     * @param plan    Kullanıcının mevcut abonelik planı
     * @return Oynatma izni (true: izin verildi)
     */
    @Override
    public boolean handlePlayback(Content content, SubscriptionComponent plan) {
        // Savunmacı kontrol: Plan yoksa erişimi reddet
        if (plan == null) {
            System.out.println("⚠  Aktif bir abonelik planınız bulunmamaktadır.");
            return false;
        }

        // Yaş kısıtlaması kontrolü — basit simülasyon (varsayılan kullanıcı yaşı: 20)
        // Gerçek uygulamada Subscriber nesnesinden yaş bilgisi alınır
        if (content.getAgeRestriction() > 18) {
            System.out.println("⚠  Yaş kısıtlaması uyarısı: Bu içerik " 
                    + content.getAgeRestriction() + "+ yaş sınırına sahiptir.");
        }

        // Premium içerik kontrolü — erişim engelleme ile
        if (content.isPremium()) {
            boolean hasPremiumFeature = plan.getFeatures().stream()
                    .anyMatch(f -> f.toLowerCase().contains("4k") 
                            || f.toLowerCase().contains("reklamsız")
                            || f.toLowerCase().contains("premium"));
            if (!hasPremiumFeature) {
                System.out.println("❌ Erişim reddedildi: Bu premium bir içeriktir. "
                        + "Lütfen paketinizi yükseltin. (Menü 2)");
                return false;
            }
        }

        System.out.println("✅ Erişim izni verildi. Hesap durumu: Aktif");
        return true;
    }

    @Override
    public String getStateName() {
        return "Aktif";
    }

    @Override
    public String toString() {
        return "ActiveState [Aktif]";
    }
}
