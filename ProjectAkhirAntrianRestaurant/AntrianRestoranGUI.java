import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Pelanggan<T> {
    private String nama;
    private int nomorAntrian;
    private LocalDateTime waktuDatang;
    private LocalDateTime waktuDuduk;
    private List<T> pesanan;

    public Pelanggan(String nama, int nomorAntrian, LocalDateTime waktuDatang) {
        this.nama = nama;
        this.nomorAntrian = nomorAntrian;
        this.waktuDatang = waktuDatang;
        this.pesanan = new LinkedList<>();
    }

    public String getNama() {
        return nama;
    }

    public int getNomorAntrian() {
        return nomorAntrian;
    }

    public LocalDateTime getWaktuDatang() {
        return waktuDatang;
    }

    public LocalDateTime getWaktuDuduk() {
        return waktuDuduk;
    }

    public void setWaktuDuduk(LocalDateTime waktuDuduk) {
        this.waktuDuduk = waktuDuduk;
    }

    public void tambahPesanan(T pesanan) {
        this.pesanan.add(pesanan);
    }

    public List<T> getPesanan() {
        return pesanan;
    }
}

public class AntrianRestoranGUI extends JFrame {
    private Queue<Pelanggan<String>> antrianPelanggan;
    private int nomorAntrian;
    private String fileName;

    private JTextField namaField;
    private JTextField pesananField;
    private JTextArea antrianArea;
    private JTextArea statusArea;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public AntrianRestoranGUI(String fileName) {
        this.fileName = fileName;
        this.antrianPelanggan = new LinkedList<>();
        this.nomorAntrian = 1;

        loadQueue();

        setTitle("Sistem Antrian Restoran");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Pelanggan"));
        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);
        inputPanel.add(new JLabel("Pesanan:"));
        pesananField = new JTextField();
        inputPanel.add(pesananField);
        JButton tambahButton = new JButton("Tambah ke Antrian");
        inputPanel.add(tambahButton);
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahAntrian();
            }
        });

        antrianArea = new JTextArea();
        antrianArea.setEditable(false);
        antrianArea.setBorder(BorderFactory.createTitledBorder("Daftar Antrian"));

        JPanel actionPanel = new JPanel(new GridLayout(1, 2));
        JButton panggilButton = new JButton("Panggil Pelanggan");
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

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(antrianArea), BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);

        JButton homeButton = new JButton("Tampilkan Menu");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuListPage();
            }
        });
        inputPanel.add(homeButton);
    }

    private void showMenuListPage() {
        MenuListPage MenuListPage = new MenuListPage(null);
        MenuListPage.setVisible(true);
    }

    private void resetAntrian() {
        antrianPelanggan.clear();
        nomorAntrian = 1;
        antrianArea.setText("");
        statusArea.setText("Antrian telah di-reset");
        saveQueue();
    }


    private void tambahAntrian() {
        String nama = namaField.getText();
        String pesanan = pesananField.getText();

        LocalDateTime waktuDatang = LocalDateTime.now();
        Pelanggan<String> pelanggan = new Pelanggan<>(nama, nomorAntrian++, waktuDatang);
        pelanggan.tambahPesanan(pesanan);
        antrianPelanggan.offer(pelanggan);

        antrianArea.append("Nama: " + pelanggan.getNama() +
                ", Nomor Antrian: " + pelanggan.getNomorAntrian() +
                ", Waktu Datang: " + pelanggan.getWaktuDatang().format(formatter) +
                ", Pesanan: " + pelanggan.getPesanan() + "\n");
        statusArea.setText("Pelanggan atas nama " + pelanggan.getNama() + " telah ditambahkan ke dalam antrian");

        namaField.setText("");
        pesananField.setText("");

        saveQueue();
    }

    private void panggilAntrian() {
        Pelanggan<String> pelanggan = antrianPelanggan.poll();
        if (pelanggan != null) {
            pelanggan.setWaktuDuduk(LocalDateTime.now());
            statusArea.setText("Pelanggan atas nama " + pelanggan.getNama() + " silakan duduk di meja yang tersedia");

            saveQueue();
        } else {
            statusArea.setText("Antrian kosong, tidak ada pelanggan yang dapat dipanggil");
        }
    }

    private void saveQueue() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Pelanggan<String> pelanggan : antrianPelanggan) {
                writer.write(pelanggan.getNama() + "," + pelanggan.getNomorAntrian() + "," + 
                             pelanggan.getWaktuDatang().format(formatter) + "," + 
                             pelanggan.getPesanan() + "," +
                             (pelanggan.getWaktuDuduk() != null ? pelanggan.getWaktuDuduk().format(formatter) : "null"));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQueue() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String nama = data[0];
                    int nomorAntrian = Integer.parseInt(data[1]);
                    LocalDateTime waktuDatang = LocalDateTime.parse(data[2], formatter);
                    Pelanggan<String> pelanggan = new Pelanggan<>(nama, nomorAntrian, waktuDatang);
                    
                    // Read pesanan
                    String pesanan = data[3].replace("[", "").replace("]", ""); // Removing square brackets
                    String[] pesananArray = pesanan.split(", ");
                    for (String p : pesananArray) {
                        pelanggan.tambahPesanan(p);
                    }
    
                    // Read waktuDuduk if available
                    if (!data[4].equals("null")) {
                        LocalDateTime waktuDuduk = LocalDateTime.parse(data[4], formatter);
                        pelanggan.setWaktuDuduk(waktuDuduk);
                    }
    
                    antrianPelanggan.offer(pelanggan);
                }
            }
            nomorAntrian = antrianPelanggan.stream().mapToInt(Pelanggan::getNomorAntrian).max().orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AntrianRestoranGUI("dataAntrian.txt").setVisible(true);
            }
        });
    }

    class MenuListPage extends JFrame {
        private AntrianRestoranGUI mainPage;

        public MenuListPage(AntrianRestoranGUI mainPage) {
            this.mainPage = mainPage;

            setTitle("Menu");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            initComponents();
        }

        private void initComponents() {
            JPanel panel = new JPanel(new BorderLayout());

            JTextArea menuList = new JTextArea();
            menuList.setEditable(false);
            menuList.setText("Daftar Menu:\n\n" +
                    "1. Mie Goreng - Rp. 15.000\n" +
                    "2. Ayam Goreng - Rp. 18.000\n" +
                    "3. Nasi Goreng - Rp. 13.000\n" +
                    "4. Es teh - Rp. 5.000\n" +
                    "5. Es Jeruk - Rp. 5.000\n" +
                    "...\n" +
                    "Tulis pesanan anda.\n\n");
            JScrollPane menuScrollPane = new JScrollPane(menuList);
            panel.add(menuScrollPane, BorderLayout.CENTER);

            add(panel);
        }

        @SuppressWarnings("unused")
        private void showMainPage() {
            mainPage.setVisible(true);
            dispose();
        }
    }
}


