import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("OptionPane.messageFont", new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14));
            if (DbConfig.testConnection()) new Login().setVisible(true);
        });
    }
}
