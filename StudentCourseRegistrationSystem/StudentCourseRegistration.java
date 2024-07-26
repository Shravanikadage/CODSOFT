import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentCourseRegistration extends JFrame {
    private Connection connection;
    private JButton bntCourseList;
    private JButton btnRegistration;
    private JTextArea textArea;
    private JPanel studCourseRegPanel;
    private JLabel jlicon;
    private JLabel jlHeading;

    public StudentCourseRegistration() {
        setTitle("Create a new account");
        setContentPane(studCourseRegPanel);
        setPreferredSize(new Dimension(1000, 800));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bntCourseList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCourses();
            }
        });
        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentRegistration();
            }
        });

        pack();
        setVisible(true);

        // Establish database connection
        try {
            String url = "jdbc:mysql://localhost:3306/courseregistration";
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listCourses() {
        String sql = "SELECT * FROM CourseList";

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            this.textArea.setText("");

            while (rs.next()) {
                String course = String.format("%s: %s (%d) - %s\n%s\n\n",
                        rs.getString("Course code"),
                        rs.getString("Title"),
                        rs.getInt("Capacity"),
                        rs.getString("Schedule"),
                        rs.getString("Description")
                );

                this.textArea.append(course);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentCourseRegistration sr = new StudentCourseRegistration();
        sr.setVisible(true);
    }
}
