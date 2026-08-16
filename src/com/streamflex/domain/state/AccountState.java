package com.streamflex.domain.state;

import com.streamflex.domain.model.Content;
import com.streamflex.domain.decorator.SubscriptionComponent;

/**
 * Kullanıcı hesabının durumunu temsil eden arayüz (State Pattern - State).
 * Her somut durum sınıfı (ActiveState, SuspendedState vb.) bu arayüzü uygular
 * ve yetkilendirme mantığını kendi bünyesinde barındırır.
 * Bu sayede CLIController'da if-else blokları ortadan kalkar.
 */
public interface AccountState {

    /**
     * İçerik oynatma talebini değerlendirir.
     * Aktif durumda izin verir; diğer durumlarda AccessDeniedException fırlatır.
     *
     * @param content İzlenmek istenen içerik
     * @param plan    Kullanıcının mevcut abonelik planı
     * @return Oynatma izni verilip verilmediği
     */
    boolean handlePlayback(Content content, SubscriptionComponent plan);

    /**
     * Durum adını döndürür (örn: "Aktif", "Askıya Alınmış").
     *
     * @return Durumun okunabilir adı
     */
    String getStateName();
}
