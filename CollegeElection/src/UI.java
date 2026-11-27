
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UI {

    static final Color NAVY = new Color(15, 35, 75);
    static final Color BLUE = new Color(25, 75, 145);
    static final Color WHITE = Color.WHITE;

    // Main panel
    static JPanel panel() {

        JPanel p = new JPanel();

        p.setBackground(NAVY);
        p.setBorder(
            BorderFactory.createEmptyBorder(18, 22, 18, 22)
        );

        return p;
    }

    // White text labels
    static JLabel label(String text) {

        JLabel l = new JLabel(text);
        l.setForeground(WHITE);
        l.setFont(new Font("SansSerif", Font.PLAIN, 15));

        return l;
    }

    // Buttons
    static JButton button(String text) {

        JButton b = new JButton(text);

        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(BLUE);
        b.setForeground(WHITE);
        b.setFocusPainted(false);

        b.setBorder(
            BorderFactory.createEmptyBorder(10, 18, 10, 18)
        );

        return b;
    }

    // Message dialog
    static void msg(Component c, String s) {

        JOptionPane.showMessageDialog(c, s);
    }

    // Error dialog
    static void error(Component c, Exception e) {

        JOptionPane.showMessageDialog(
            c,
            e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // Election status
    static String electionStatus() throws Exception {

        try (
            Connection c = DbConfig.connect();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(
                "SELECT status FROM Elections WHERE id=1"
            )
        ) {

            if (r.next()) {
                return r.getString(1);
            }

            return "inactive";
        }
    }
}
