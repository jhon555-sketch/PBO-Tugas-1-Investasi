import java.util.*;

class Saham {
    String kode, namaPerusahaan;
    double harga;

    public Saham(String kode, String nama, double harga) {
        this.kode = kode;
        this.namaPerusahaan = nama;
        this.harga = harga;
    }
}

class SuratBerhargaNegara {
    String nama;
    double bunga;
    int jangkaWaktu;
    String tanggalJatuhTempo;
    double kuotaNasional;

    public SuratBerhargaNegara(String nama, double bunga, int jangkaWaktu, String tanggal, double kuota) {
        this.nama = nama;
        this.bunga = bunga;
        this.jangkaWaktu = jangkaWaktu;
        this.tanggalJatuhTempo = tanggal;
        this.kuotaNasional = kuota;
    }
}

class PortofolioCustomer {
    Map<Saham, Integer> sahamDimiliki = new HashMap<>();
    Map<SuratBerhargaNegara, Double> sbnDimiliki = new HashMap<>();

    void tampilkan(List<Saham> daftarSaham) {
        double totalPembelianSaham = 0, totalNilaiPasar = 0;

        System.out.println("\n==== Portofolio Saham ====");
        for (Saham saham : sahamDimiliki.keySet()) {
            int jumlah = sahamDimiliki.get(saham);
            double pembelian = jumlah * saham.harga; // asumsi harga saat beli = harga sekarang
            double nilaiPasar = jumlah * saham.harga;
            totalPembelianSaham += pembelian;
            totalNilaiPasar += nilaiPasar;
            System.out.printf("%s (%s) - %d lembar, Beli: Rp%.2f, Pasar: Rp%.2f\n",
                    saham.namaPerusahaan, saham.kode, jumlah, pembelian, nilaiPasar);
        }
        System.out.printf("Total Pembelian: Rp%.2f, Total Nilai Pasar: Rp%.2f\n",
                totalPembelianSaham, totalNilaiPasar);

        System.out.println("\n==== Portofolio SBN ====");
        for (SuratBerhargaNegara sbn : sbnDimiliki.keySet()) {
            double nominal = sbnDimiliki.get(sbn);
            double kupon = (sbn.bunga / 12) * 0.9 * nominal;
            System.out.printf("%s - Nominal: Rp%.2f, Kupon/Bulan: Rp%.2f\n",
                    sbn.nama, nominal, kupon);
        }
    }
}

public class InvestasiApp {
    static Scanner input = new Scanner(System.in);
    static List<Saham> daftarSaham = new ArrayList<>();
    static List<SuratBerhargaNegara> daftarSBN = new ArrayList<>();
    static PortofolioCustomer portofolio = new PortofolioCustomer();

    static Map<String, String> akunAdmin = Map.of("admin", "admin123");
    static Map<String, String> akunCustomer = Map.of("cust", "cust123");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Menu Utama ===");
            System.out.println("1. Login");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilih = input.nextLine();

            if (pilih.equals("1")) {
                login();
            } else if (pilih.equals("0")) {
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    static void login() {
        System.out.print("Username: ");
        String user = input.nextLine();
        System.out.print("Password: ");
        String pass = input.nextLine();

        if (akunAdmin.containsKey(user) && akunAdmin.get(user).equals(pass)) {
            menuAdmin();
        } else if (akunCustomer.containsKey(user) && akunCustomer.get(user).equals(pass)) {
            menuCustomer();
        } else {
            System.out.println("Login gagal!");
        }
    }

    static void menuAdmin() {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Logout");
            System.out.print("Pilih: ");
            String pilih = input.nextLine();

            switch (pilih) {
                case "1": menuAdminSaham(); break;
                case "2": menuAdminSBN(); break;
                case "3": return;
                default: System.out.println("Pilihan tidak valid");
            }
        }
    }

    static void menuAdminSaham() {
        while (true) {
            System.out.println("\n1. Tambah Saham\n2. Ubah Harga Saham\n3. Kembali");
            System.out.print("Pilih: ");
            String pilih = input.nextLine();
            if (pilih.equals("1")) {
                System.out.print("Kode: ");
                String kode = input.nextLine();
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Harga: ");
                double harga = Double.parseDouble(input.nextLine());
                daftarSaham.add(new Saham(kode, nama, harga));
            } else if (pilih.equals("2")) {
                for (int i = 0; i < daftarSaham.size(); i++) {
                    System.out.printf("%d. %s (%s) - Rp%.2f\n", i + 1, daftarSaham.get(i).namaPerusahaan,
                            daftarSaham.get(i).kode, daftarSaham.get(i).harga);
                }
                System.out.print("Pilih saham: ");
                int idx = Integer.parseInt(input.nextLine()) - 1;
                System.out.print("Harga baru: ");
                double hargaBaru = Double.parseDouble(input.nextLine());
                daftarSaham.get(idx).harga = hargaBaru;
            } else if (pilih.equals("3")) {
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    static void menuAdminSBN() {
        while (true) {
            System.out.println("\n1. Tambah SBN\n2. Kembali");
            System.out.print("Pilih: ");
            String pilih = input.nextLine();
            if (pilih.equals("1")) {
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Bunga (%): ");
                double bunga = Double.parseDouble(input.nextLine());
                System.out.print("Jangka waktu (bulan): ");
                int waktu = Integer.parseInt(input.nextLine());
                System.out.print("Tanggal Jatuh Tempo: ");
                String tanggal = input.nextLine();
                System.out.print("Kuota Nasional: ");
                double kuota = Double.parseDouble(input.nextLine());
                daftarSBN.add(new SuratBerhargaNegara(nama, bunga, waktu, tanggal, kuota));
            } else if (pilih.equals("2")) {
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    static void menuCustomer() {
        while (true) {
            System.out.println("\n=== Menu Customer ===");
            System.out.println("1. Beli Saham\n2. Jual Saham\n3. Beli SBN\n4. Simulasi SBN\n5. Portofolio\n6. Logout");
            System.out.print("Pilih: ");
            String pilih = input.nextLine();

            switch (pilih) {
                case "1": beliSaham(); break;
                case "2": jualSaham(); break;
                case "3": beliSBN(); break;
                case "4": simulasiSBN(); break;
                case "5": portofolio.tampilkan(daftarSaham); break;
                case "6": return;
                default: System.out.println("Pilihan tidak valid");
            }
        }
    }

    static void beliSaham() {
        for (int i = 0; i < daftarSaham.size(); i++) {
            Saham s = daftarSaham.get(i);
            System.out.printf("%d. %s (%s) - Rp%.2f\n", i + 1, s.namaPerusahaan, s.kode, s.harga);
        }
        System.out.print("Pilih: ");
        int idx = Integer.parseInt(input.nextLine()) - 1;
        System.out.print("Jumlah lembar: ");
        int jumlah = Integer.parseInt(input.nextLine());
        Saham dipilih = daftarSaham.get(idx);
        portofolio.sahamDimiliki.put(dipilih,
                portofolio.sahamDimiliki.getOrDefault(dipilih, 0) + jumlah);
    }

    static void jualSaham() {
        int i = 1;
        List<Saham> dimiliki = new ArrayList<>(portofolio.sahamDimiliki.keySet());
        for (Saham s : dimiliki) {
            System.out.printf("%d. %s (%s) - %d lembar\n", i++, s.namaPerusahaan, s.kode, portofolio.sahamDimiliki.get(s));
        }
        System.out.print("Pilih: ");
        int idx = Integer.parseInt(input.nextLine()) - 1;
        Saham dipilih = dimiliki.get(idx);
        System.out.print("Jumlah dijual: ");
        int jual = Integer.parseInt(input.nextLine());
        int dimilikiLembar = portofolio.sahamDimiliki.get(dipilih);
        if (jual > dimilikiLembar) {
            System.out.println("Gagal: jumlah lebih dari yang dimiliki!");
        } else {
            portofolio.sahamDimiliki.put(dipilih, dimilikiLembar - jual);
            if (portofolio.sahamDimiliki.get(dipilih) == 0)
                portofolio.sahamDimiliki.remove(dipilih);
        }
    }

    static void beliSBN() {
        for (int i = 0; i < daftarSBN.size(); i++) {
            SuratBerhargaNegara s = daftarSBN.get(i);
            System.out.printf("%d. %s - Kuota: Rp%.2f\n", i + 1, s.nama, s.kuotaNasional);
        }
        System.out.print("Pilih: ");
        int idx = Integer.parseInt(input.nextLine()) - 1;
        System.out.print("Nominal beli: ");
        double nominal = Double.parseDouble(input.nextLine());
        SuratBerhargaNegara sbn = daftarSBN.get(idx);
        if (nominal > sbn.kuotaNasional) {
            System.out.println("Kuota tidak cukup!");
        } else {
            sbn.kuotaNasional -= nominal;
            portofolio.sbnDimiliki.put(sbn,
                    portofolio.sbnDimiliki.getOrDefault(sbn, 0.0) + nominal);
        }
    }

    static void simulasiSBN() {
        System.out.print("Nominal: ");
        double nominal = Double.parseDouble(input.nextLine());
        System.out.print("Bunga tahunan (%): ");
        double bunga = Double.parseDouble(input.nextLine());
        double kupon = (bunga / 12.0) * 0.9 * nominal;
        System.out.printf("Kupon per bulan: Rp%.2f\n", kupon);
    }
}
