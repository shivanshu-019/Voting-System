import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class StudentRegistration extends JFrame {

    JTextField id = new JTextField(18);
    JTextField name = new JTextField(18);
    JTextField email = new JTextField(18);
    JPasswordField password = new JPasswordField(18);
    JPasswordField confirmPassword = new JPasswordField(18);

    public StudentRegistration(JFrame loginWindow) {

        setTitle("Student Registration");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set field sizes and fonts
        Dimension fieldSize = new Dimension(280, 38);

        id.setPreferredSize(fieldSize);
        name.setPreferredSize(fieldSize);
        email.setPreferredSize(fieldSize);
        password.setPreferredSize(fieldSize);
        confirmPassword.setPreferredSize(fieldSize);

        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        id.setFont(fieldFont);
        name.setFont(fieldFont);
        email.setFont(fieldFont);
        password.setFont(fieldFont);
        confirmPassword.setFont(fieldFont);

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

        JLabel title = new JLabel("STUDENT REGISTRATION");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(title, g);

        g.gridwidth = 1;
        g.gridy++;

        // Input fields
        addField(p, g, "Student ID", id);
        addField(p, g, "Full Name", name);
        addField(p, g, "Email", email);
        addField(p, g, "Password", password);
        addField(p, g, "Confirm Password", confirmPassword);

        // Register button
        g.gridx = 0;
        g.gridy++;
        g.gridwidth = 2;

        JButton register = UI.button("REGISTER");
        p.add(register, g);

        register.addActionListener(e -> registerStudent(loginWindow));

        // Back button
        g.gridy++;

        JButton back = UI.button("BACK TO LOGIN");
        p.add(back, g);

        back.addActionListener(e -> {
            dispose();
            loginWindow.setVisible(true);
        });

        add(p);
    }

    private void addField(JPanel p, GridBagConstraints g,
                          String label, JComponent field) {

        g.gridx = 0;

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        p.add(lbl, g);

        g.gridx = 1;
        p.add(field, g);

        g.gridy++;
    }

    private void registerStudent(JFrame loginWindow) {

        String studentId = id.getText().trim();
        String studentName = name.getText().trim();
        String studentEmail = email.getText().trim();

        String studentPassword =
                new String(password.getPassword());

        String confirm =
                new String(confirmPassword.getPassword());

        // Check empty fields
        if (studentId.isEmpty() || studentName.isEmpty()
                || studentEmail.isEmpty()
                || studentPassword.isEmpty()
                || confirm.isEmpty()) {

            UI.msg(this, "Please fill all fields.");
            return;
        }

        // Validate email
        if (!studentEmail.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            UI.msg(this, "Enter a valid email address.");
            return;
        }

        // Validate password
        if (studentPassword.length() < 4) {
            UI.msg(this,
                    "Password must contain at least 4 characters.");
            return;
        }

        // Confirm password
        if (!studentPassword.equals(confirm)) {
            UI.msg(this, "Passwords do not match.");
            return;
        }

        String sql = "INSERT INTO Students "
                + "(student_id, password, student_name, "
                + "student_email, has_voted) "
                + "VALUES (?, ?, ?, ?, FALSE)";

        try (Connection c = DbConfig.connect();
             PreparedStatement s = c.prepareStatement(sql)) {

            s.setString(1, studentId);
            s.setString(2, studentPassword);
            s.setString(3, studentName);
            s.setString(4, studentEmail);

            s.executeUpdate();

            UI.msg(this,
                    "Registration successful!\nYou can now login.");

            dispose();
            loginWindow.setVisible(true);

        } catch (SQLIntegrityConstraintViolationException ex) {

            UI.msg(this,
                    "Student ID or email already exists.");

        } catch (Exception ex) {

            UI.error(this, ex);
        }
    }
}