import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {

    JTextField id = new JTextField(16);
    JPasswordField pass = new JPasswordField(16);

    public Login() {

        setTitle("College Election - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 470);
        setMinimumSize(new Dimension(550, 470));
        setLocationRelativeTo(null);

        // Input field sizing
        id.setPreferredSize(new Dimension(280, 42));
        pass.setPreferredSize(new Dimension(280, 42));

        id.setFont(new Font("Arial", Font.PLAIN, 16));
        pass.setFont(new Font("Arial", Font.PLAIN, 16));

        // Main panel
        JPanel p = UI.panel();
        p.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 12, 10, 12);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        // Title
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;

        JLabel title = new JLabel("ONLINE VOTING SYSTEM");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(title, g);

        // Student ID
        g.gridwidth = 1;
        g.gridy++;
        g.gridx = 0;

        JLabel idLabel = new JLabel("Student ID / Admin ID");
        idLabel.setForeground(Color.WHITE);
        p.add(idLabel, g);

        g.gridx = 1;
        p.add(id, g);

        // Password
        g.gridy++;
        g.gridx = 0;

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        p.add(passLabel, g);

        g.gridx = 1;
        p.add(pass, g);

        // Login button
        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 2;

        JButton login = UI.button("LOGIN");
        p.add(login, g);

        login.addActionListener(e -> login());

        // Results button
        g.gridy++;

        JButton results = UI.button("VIEW FINAL RESULTS");
        p.add(results, g);

        results.addActionListener(
                e -> ResultWindow.showResults(this)
        );

        // Registration button
        g.gridy++;

        JButton register = UI.button("NEW STUDENT? REGISTER");
        p.add(register, g);

        register.addActionListener(e -> {
            setVisible(false);
            new StudentRegistration(this).setVisible(true);
        });

        // Demo credentials
        g.gridy++;

        JLabel hint = new JLabel(
                "Demo: Student 01 / 0101     Admin 91 / 9191",
                SwingConstants.CENTER
        );

        hint.setForeground(Color.WHITE);
        p.add(hint, g);

        add(p);
    }

    private void login() {

        String uid = id.getText().trim();
        String pw = new String(pass.getPassword());

        if (uid.isEmpty() || pw.isEmpty()) {
            UI.msg(this, "Enter ID and password.");
            return;
        }

        try (Connection c = DbConfig.connect()) {

            // Student login
            try (PreparedStatement s = c.prepareStatement(
                    "SELECT student_id, student_name, "
                    + "student_email, has_voted "
                    + "FROM Students "
                    + "WHERE student_id=? AND password=?")) {

                s.setString(1, uid);
                s.setString(2, pw);

                try (ResultSet r = s.executeQuery()) {

                    if (r.next()) {

                        if (r.getBoolean("has_voted")) {
                            UI.msg(this, "You have already voted.");
                            return;
                        }

                        new HomeWindow(
                                new User(
                                        r.getString(1),
                                        r.getString(2),
                                        r.getString(3)
                                )
                        ).setVisible(true);

                        dispose();
                        return;
                    }
                }
            }

            // Admin login
            try (PreparedStatement s = c.prepareStatement(
                    "SELECT admin_id FROM Admin "
                    + "WHERE admin_id=? AND password=?")) {

                s.setString(1, uid);
                s.setString(2, pw);

                try (ResultSet r = s.executeQuery()) {

                    if (r.next()) {
                        new AdminWindow(uid).setVisible(true);
                        dispose();
                        return;
                    }
                }
            }

            UI.msg(this, "Invalid credentials.");

        } catch (Exception ex) {
            UI.error(this, ex);
        }
    }
}