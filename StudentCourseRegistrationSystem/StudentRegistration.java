import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentRegistration extends JFrame {
    private JTextField textFieldName;
    private JTextField textFieldStudentID;
    private JPanel RegisterPanel;
    private JButton registeButton;
    private JButton cancelButton;
    private JComboBox<String> comboBoxList;
    private JButton dropButton;
    private Connection connection;

    public StudentRegistration() {
        setTitle("Student Registration");
        setContentPane(RegisterPanel);
        setPreferredSize(new Dimension(1000, 800));
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        registeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCourse();
            }
        });

        // database connection
        try {
            String url = "jdbc:mysql://localhost:3306/courseregistration";
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pack(); // Adjust the frame to fit the preferred size
        setVisible(true);
    }

    private void registerUser() {
        String name = textFieldName.getText();
        String id = textFieldStudentID.getText();
        String course = (String) comboBoxList.getSelectedItem();

        if (name.isEmpty() || id.isEmpty() || course == null || course.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = addUserToDatabase(name, id, course);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dropCourse() {
        String name = JOptionPane.showInputDialog(this, "Enter student name:");
        String course = JOptionPane.showInputDialog(this, "Enter course name:");

        if (name != null && course != null) {
            if (isValidStudent(name) && isValidCourse(course) && isStudentRegisteredInCourse(name, course)) {
                String sql = "DELETE FROM register WHERE Name = ? AND Course = ?";

                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, course);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Course dropped successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to drop course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid student name or course code, or you are not registered in this course.");
            }
        }
    }

    private boolean isStudentRegisteredInCourse(String name, String course) {
        String sql = "SELECT * FROM register WHERE Name = ? AND Course = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, course);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidStudent(String name) {
        String sql = "SELECT * FROM register WHERE Name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidCourse(String courseCode) {
        String sql = "SELECT * FROM register WHERE Course = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User addUserToDatabase(String name, String id, String course) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/courseregistration";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            String sql = "INSERT INTO register (Name, Id, Course) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, course);

            // Insert row into the table
            int addedRow = preparedStatement.executeUpdate();
            if (addedRow > 0) {
                user = new User();
                user.name = name;
                user.id = id;
                user.course = course;
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    private void createUIComponents() {
        // Custom component creation
        comboBoxList = new JComboBox<>();
        // Add items to comboBoxList as needed
    }

    public static void main(String[] args) {
        new StudentRegistration();
    }
}

