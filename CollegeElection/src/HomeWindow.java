import javax.swing.*; import java.awt.*; import java.sql.*;
public class HomeWindow extends JFrame {
    private User user; private Timer session;
    public HomeWindow(User u){user=u;setTitle("Student Dashboard");setSize(600,430);setLocationRelativeTo(null);setDefaultCloseOperation(DISPOSE_ON_CLOSE);JPanel p=UI.panel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JLabel h=new JLabel("Welcome, "+u.name);h.setForeground(Color.WHITE);h.setFont(new Font("SansSerif",Font.BOLD,23));h.setAlignmentX(CENTER_ALIGNMENT);p.add(h);p.add(Box.createVerticalStrut(10));
        JLabel info=new JLabel("ID: "+u.id+"    Email: "+u.email);info.setForeground(Color.WHITE);info.setAlignmentX(CENTER_ALIGNMENT);p.add(info);
        JLabel registered=new JLabel("Registered positions: "+positions());registered.setForeground(Color.WHITE);registered.setAlignmentX(CENTER_ALIGNMENT);p.add(registered);p.add(Box.createVerticalStrut(20));
        JButton reg=UI.button("REGISTER AS CANDIDATE"),vote=UI.button("VOTE NOW"),logout=UI.button("LOG OUT");for(JButton b:new JButton[]{reg,vote,logout}){b.setAlignmentX(CENTER_ALIGNMENT);p.add(b);p.add(Box.createVerticalStrut(12));}
        reg.addActionListener(e->new CandidateRegistration(this,user).setVisible(true));vote.addActionListener(e->startVote());logout.addActionListener(e->logout());add(p);
        session=new Timer(150000,e->logout());session.setRepeats(false);session.start(); addWindowListener(new java.awt.event.WindowAdapter(){public void windowClosed(java.awt.event.WindowEvent e){if(session!=null)session.stop();}});
    }
    private String positions(){try(Connection c=DbConfig.connect();PreparedStatement s=c.prepareStatement("SELECT position FROM Candidates WHERE student_id=?")){s.setString(1,user.id);try(ResultSet r=s.executeQuery()){String x="";while(r.next())x+=r.getString(1)+", ";return x.isEmpty()?"None":x.substring(0,x.length()-2);}}catch(Exception e){return "Unavailable";}}
    private void startVote(){try{if(!"active".equals(UI.electionStatus())){UI.msg(this,"Election is not active.");return;}try(Connection c=DbConfig.connect();PreparedStatement s=c.prepareStatement("UPDATE Students SET has_voted=TRUE WHERE student_id=? AND has_voted=FALSE")){s.setString(1,user.id);if(s.executeUpdate()!=1){UI.msg(this,"You have already voted.");return;}}new VotingWindow(user).setVisible(true);dispose();}catch(Exception e){UI.error(this,e);}}
    private void logout(){if(session!=null)session.stop();new Login().setVisible(true);dispose();}
}
