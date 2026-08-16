package com.streamflex;

import com.streamflex.data.ContentInventory;
import com.streamflex.domain.model.Content;
import com.streamflex.presentation.CLIController;

import java.util.Scanner;

/**
 * StreamFlex uygulamasının giriş noktası.
 * Demo içerikleri yükler ve CLIController'ı başlatır.
 *
 * @author 2023510147 - Muhammed Yusuf ESEN
 * @author 2023510072 - Eren YILMAZ
 * @version Faz 3 - Final Implementation
 */
public class App {

    public static void main(String[] args) {
        // Demo içeriklerin yüklenmesi (In-Memory Inventory)
        loadDemoContent();

        // CLI kontrolcüsünün başlatılması
        Scanner scanner = new Scanner(System.in);
        CLIController controller = new CLIController(scanner);
        controller.run();

        scanner.close();
    }

    /**
     * Sistemin test edilmesi için örnek içerikleri envantere yükler.
     * ContentInventory Singleton'ı kullanılarak eklenir.
     */
    private static void loadDemoContent() {
        ContentInventory inventory = ContentInventory.getInstance();

        // Filmler
        inventory.addContent(new Content("MOV001", "Yıldızlararası", "Bilim Kurgu", 13, false));
        inventory.addContent(new Content("MOV002", "Esaretin Bedeli", "Drama", 16, false));
        inventory.addContent(new Content("MOV003", "Inception", "Bilim Kurgu", 13, true));
        inventory.addContent(new Content("MOV004", "Godfather", "Suç", 18, true));

        // Belgeseller
        inventory.addContent(new Content("DOC001", "Gezegenimiz", "Belgesel", 0, false));
        inventory.addContent(new Content("DOC002", "Cosmos: Olası Dünyalar", "Belgesel", 0, false));

        // Diziler
        inventory.addContent(new Content("SER001", "Breaking Bad", "Drama", 18, true));
        inventory.addContent(new Content("SER002", "Çocuklar İçin Bilim", "Eğitim", 0, false));

        System.out.println("📂 Demo içerikler yüklendi. (" + inventory.getContentCount() + " adet)");
    }
}
