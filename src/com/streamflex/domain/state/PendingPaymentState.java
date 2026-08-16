package com.streamflex.domain.state;

import com.streamflex.domain.model.Content;
import com.streamflex.domain.decorator.SubscriptionComponent;
import com.streamflex.exception.AccessDeniedException;

/**
 * Ödeme bekleyen hesap durumu (State Pattern - Concrete State).
 * Ödeme reddedildiğinde hesap bu duruma geçer ve içerik erişimi engellenir.
 */
public class PendingPaymentState implements AccountState {

    /**
     * Ödeme bekleyen durumda içerik oynatma talebini reddeder.
     * AccessDeniedException fırlatarak erişimi engeller.
     *
     * @param content İzlenmek istenen içerik
     * @param plan    Kullanıcının mevcut abonelik planı
     * @return Asla true dönmez; her zaman exception fırlatır
     * @throws AccessDeniedException Ödeme beklediği için
     */
    @Override
    public boolean handlePlayback(Content content, SubscriptionComponent plan) {
        throw new AccessDeniedException(
                "❌ Erişim reddedildi: Hesabınızda bekleyen bir ödeme bulunmaktadır. "
                + "Lütfen önce ödeme işlemini tamamlayın.");
    }

    @Override
    public String getStateName() {
        return "Ödeme Bekliyor";
    }

    @Override
    public String toString() {
        return "PendingPaymentState [Ödeme Bekliyor]";
    }
}
