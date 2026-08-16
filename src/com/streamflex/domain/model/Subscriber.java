package com.streamflex.domain.model;

import com.streamflex.domain.state.AccountState;
import com.streamflex.domain.state.ActiveState;
import com.streamflex.domain.decorator.SubscriptionComponent;

/**
 * Sistemdeki bir aboneyi (kullanıcı) temsil eden ana entity sınıfı.
 * Has-a ilişkisi ile AccountState ve SubscriptionComponent nesnelerine sahiptir.
 * İçerik oynatma talebini kendi state nesnesine devreder (State Pattern).
 * Tüm alanlar private olup kapsüllenmiştir (NFR-3).
 * Kimlik alanları (id, username) final olarak korunmaktadır.
 */
public class Subscriber {

    private final String id;
    private final String username;
    private AccountState state;
    private SubscriptionComponent plan;

    /**
     * Yeni bir abone oluşturur. Varsayılan hesap durumu Active'dir.
     *
     * @param id       Benzersiz kullanıcı kimliği
     * @param username Kullanıcı adı
     * @throws IllegalArgumentException id veya username null/boş ise
     */
    public Subscriber(String id, String username) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı ID boş olamaz.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı boş olamaz.");
        }
        this.id = id;
        this.username = username;
        this.state = new ActiveState(); // Varsayılan: Aktif
        this.plan = null;
    }

    /**
     * Hesap durumunu değiştirir (State geçişi).
     * Ödeme başarılıysa ActiveState, başarısızsa PendingPaymentState atanır.
     *
     * @param state Yeni hesap durumu
     */
    public void setState(AccountState state) {
        System.out.println("🔄 Hesap durumu değiştirildi: " 
                + this.state.getStateName() + " → " + state.getStateName());
        this.state = state;
    }

    /**
     * Abonelik planını ayarlar veya günceller.
     *
     * @param plan Yeni abonelik planı (BasePlan veya Decorator ile sarmalanmış plan)
     */
    public void setPlan(SubscriptionComponent plan) {
        this.plan = plan;
    }

    /**
     * İçerik oynatma talebini mevcut hesap durumuna devreder.
     * CLIController bu metodu çağırır; iş mantığı State nesnesinde kalır.
     *
     * @param content İzlenmek istenen içerik
     * @return Oynatma izni verilip verilmediği
     */
    public boolean requestPlayback(Content content) {
        return state.handlePlayback(content, plan);
    }

    // ── Getter Metotları ──────────────────────────────

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public AccountState getState() {
        return state;
    }

    public SubscriptionComponent getPlan() {
        return plan;
    }

    // ── equals & hashCode (id tabanlı) ────────────────

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Subscriber{id='%s', username='%s', state=%s, plan=%s}",
                id, username, state.getStateName(),
                plan != null ? plan.getDescription() : "Plan yok");
    }
}
