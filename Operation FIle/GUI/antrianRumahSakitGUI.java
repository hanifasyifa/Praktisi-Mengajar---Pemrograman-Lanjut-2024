import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

class Pasien {
    private String nama;
    private int usia;
    private int nomorAntrian;
    private LocalDateTime waktuMasuk;
    private LocalDateTime waktuPemeriksaan;
    private LocalDateTime waktuMenungguObat;
    private LocalDateTime waktuMengambilObat;

    public Pasien(String nama, int usia, int nomorAntrian, LocalDateTime waktuMasuk) {
        this.nama = nama;
        this.usia = usia;
        this.nomorAntrian = nomorAntrian;
        this.waktuMasuk = waktuMasuk;
    }

    public String getNama() {
        return nama;
    }

    public int getUsia() {
        return usia;
    }

    public int getNomorAntrian() {
        return nomorAntrian;
    }

    public LocalDateTime getWaktuMasuk() {
        return waktuMasuk;
    }

    public LocalDateTime getWaktuPemeriksaan() {
        return waktuPemeriksaan;
    }

    public void setWaktuPemeriksaan(LocalDateTime waktuPemeriksaan) {
        this.waktuPemeriksaan = waktuPemeriksaan;
    }

    public LocalDateTime getWaktuMenungguObat() {
        return waktuMenungguObat;
    }

    public void setWaktuMenungguObat(LocalDateTime waktuMenungguObat) {
        this.waktuMenungguObat = waktuMenungguObat;
    }

    public LocalDateTime getWaktuMengambilObat() {
        return waktuMengambilObat;
    }

    public void setWaktuMengambilObat(LocalDateTime waktuMengambilObat) {
        this.waktuMengambilObat = waktuMengambilObat;
    }
}

public class antrianRumahSakitGUI extends JFrame {
    private Queue<Pasien> antrianPasien;
    private int nomorAntrian;
    private String fileName;

    private JTextField namaField;
    private JTextField usiaField;
    private JTextArea antrianArea;
    private JTextArea statusArea;

    public antrianRumahSakitGUI(String fileName) {
        this.fileName = fileName;
        this.antrianPasien = new LinkedList<>();
        this.nomorAntrian = 1;

        setTitle("Sistem Antrian Rumah Sakit");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top Panel for Input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Pasien"));
        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);
        inputPanel.add(new JLabel("Usia:"));
        usiaField = new JTextField();
        inputPanel.add(usiaField);
        JButton tambahButton = new JButton("Tambah ke Antrian");
        inputPanel.add(tambahButton);
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahAntrian();
            }
        });

        // Middle Panel for Queue Display
        antrianArea = new JTextArea();
        antrianArea.setEditable(false);
        antrianArea.setBorder(BorderFactory.createTitledBorder("Daftar Antrian"));

        // Bottom Panel for Status and Actions
        JPanel actionPanel = new JPanel(new GridLayout(1, 3));
        JButton panggilButton = new JButton("Panggil Pasien");
        panggilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panggilAntrian();
            }
        });
        actionPanel.add(panggilButton);
        
        JButton resetButton = new JButton("Reset Antrian");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAntrian();
            }
        });
        actionPanel.add(resetButton);

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setBorder(BorderFactory.createTitledBorder("Status Antrian"));
        actionPanel.add(new JScrollPane(statusArea));

        // Adding panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(antrianArea), BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void tambahAntrian() {
        String nama = namaField.getText();
        int usia;
        try {
            usia = Integer.parseInt(usiaField.getText());
            if (usia <= 0) {
                statusArea.setText("<Error: Usia harus berupa angka positif.>");
                return;
            }
        } catch (NumberFormatException e) {
            statusArea.setText("<Error: Usia harus berupa angka.>");
            return;
        }

        LocalDateTime waktuMasuk = LocalDateTime.now();
        Pasien pasien = new Pasien(nama, usia, nomorAntrian++, waktuMasuk);
        antrianPasien.offer(pasien);

        antrianArea.append("Nama: " + pasien.getNama() + ", Usia: " + pasien.getUsia() +
                ", Nomor Antrian: " + pasien.getNomorAntrian() + ", Waktu Masuk: " + pasien.getWaktuMasuk() + "\n");
        statusArea.setText("<Pasien atas nama " + pasien.getNama() + " telah ditambahkan ke dalam antrian>");

        // Clear input fields
        namaField.setText("");
        usiaField.setText("");
    }

    private void panggilAntrian() {
        Pasien pasien = antrianPasien.poll();
        if (pasien != null) {
            pasien.setWaktuPemeriksaan(LocalDateTime.now());
            statusArea.setText("<Pasien atas nama " + pasien.getNama() + " sedang dalam ruang pemeriksaan>");
        } else {
            statusArea.setText("<Antrian kosong, tidak ada pasien yang dapat dipanggil>");
        }
    }

    private void resetAntrian() {
        antrianPasien.clear();
        antrianArea.setText("");
        statusArea.setText("<Antrian telah direset>");
        nomorAntrian = 1;
    }

    @SuppressWarnings("unused")
    private void simpanDataAntrian(Pasien pasien) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Nama: " + pasien.getNama() + ", Usia: " + pasien.getUsia() +
                    ", Nomor Antrian: " + pasien.getNomorAntrian() +
                    ", Waktu Masuk: " + pasien.getWaktuMasuk() +
                    ", Waktu Pemeriksaan: " + pasien.getWaktuPemeriksaan() +
                    ", Waktu Menunggu Obat: " + pasien.getWaktuMenungguObat() +
                    ", Waktu Mengambil Obat: " + pasien.getWaktuMengambilObat());
            writer.newLine();
        } catch (IOException e) {
            statusArea.setText("<Error: Terjadi kesalahan saat menyimpan data antrian> " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new antrianRumahSakitGUI("dataAntrian.txt").setVisible(true);
            }
        });
    }
}
