import javax.swing.*;
import java.awt.*;

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
