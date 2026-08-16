package com.streamflex.domain.state;

import com.streamflex.domain.model.Content;
import com.streamflex.domain.decorator.SubscriptionComponent;
import com.streamflex.exception.AccessDeniedException;

/**
 * Askıya alınmış hesap durumu (State Pattern - Concrete State).
 * Hesap askıya alındığında tüm içerik oynatma talepleri reddedilir.
 */
public class SuspendedState implements AccountState {

    /**
     * Askıya alınmış durumda içerik oynatma talebini reddeder.
     * AccessDeniedException fırlatarak erişimi engeller.
     *
     * @param content İzlenmek istenen içerik
     * @param plan    Kullanıcının mevcut abonelik planı
     * @return Asla true dönmez; her zaman exception fırlatır
     * @throws AccessDeniedException Hesap askıya alınmış olduğu için
     */
    @Override
    public boolean handlePlayback(Content content, SubscriptionComponent plan) {
        throw new AccessDeniedException(
                "❌ Erişim reddedildi: Hesabınız askıya alınmıştır. "
                + "Lütfen müşteri hizmetleri ile iletişime geçin.");
    }

    @Override
    public String getStateName() {
        return "Askıya Alınmış";
    }

    @Override
    public String toString() {
        return "SuspendedState [Askıya Alınmış]";
    }
}
