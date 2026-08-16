package com.streamflex.domain.state;

import com.streamflex.domain.model.Content;
import com.streamflex.domain.decorator.SubscriptionComponent;
import com.streamflex.exception.AccessDeniedException;

/**
 * İptal edilmiş hesap durumu (State Pattern - Concrete State).
 * Hesap iptal edildiğinde tüm içerik erişimleri kalıcı olarak engellenir.
 * Sequence Diagram'daki transitionToCancelled() akışının sonucu.
 */
public class CancelledState implements AccountState {

    /**
     * İptal edilmiş durumda içerik oynatma talebini reddeder.
     * AccessDeniedException fırlatarak erişimi engeller.
     *
     * @param content İzlenmek istenen içerik
     * @param plan    Kullanıcının mevcut abonelik planı
     * @return Asla true dönmez; her zaman exception fırlatır
     * @throws AccessDeniedException Hesap iptal edildiği için
     */
    @Override
    public boolean handlePlayback(Content content, SubscriptionComponent plan) {
        throw new AccessDeniedException(
                "❌ Erişim reddedildi: Hesabınız iptal edilmiştir. "
                + "Yeniden abone olmak için lütfen yeni bir hesap oluşturun.");
    }

    @Override
    public String getStateName() {
        return "İptal Edilmiş";
    }

    @Override
    public String toString() {
        return "CancelledState [İptal Edilmiş]";
    }
}
