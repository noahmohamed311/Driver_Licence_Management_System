package GuiClasses.Trainer;

import CoreClasses.Trainer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TrainerLogIn extends JFrame {
    private JPanel trainerLogInPanel;
    private JPanel trainerMessagePanel;
    private JPanel trainerInputPanel;
    private JLabel trainerLbl;
    private JLabel trainerIcon;
    private JTextField trainerIdTf;
    private JPasswordField trainerPf;
    private JButton trainerLogInBtn;
    private JButton trainerCancelBtn;
    private JLabel trainerIDLbl;
    private JLabel trainerPassLbl;
    private JPanel trainerFooterPanel;

    public TrainerLogIn(JFrame parent) {
        super();
        setTitle("Trainer Login");
        setContentPane(trainerLogInPanel);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        trainerLogInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = trainerIdTf.getText();
                String password = String.valueOf(trainerPf.getPassword());
                tra = getValidatedTra(id, password);

                if(tra != null) {
                    dispose();
                    TrainerMainFrame.boot(tra);
                    System.out.println("Successful authentication of trainer: " +
                                        tra.getfName() + tra.getlName());
                    System.out.println("            " + tra.getId());
                    System.out.println("            " + tra.getPhone());
                    System.out.println("            " + tra.getAddress());
                    System.out.println("            " + tra.getEmail());
                }
                else {
                    JOptionPane.showMessageDialog(TrainerLogIn.this,
                            "Id or Password invalid", "error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        trainerCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                trainerIdTf.setText("");
                trainerPf.setText("");
            }
        });
        setVisible(true);
    }

    public Trainer tra;
    private Trainer getValidatedTra(String id, String password) {
        Trainer tra = null;

        final String dbUser = "admas";
        final String dbUrl = "jdbc:mysql://localhost:3306/DLMS";
        final String dbPass = "Pa$$w0rd121921";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Trainer WHERE Tid=? AND Pass=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tra = new Trainer();
                tra.setId(resultSet.getInt("Tid"));
                tra.setfName(resultSet.getString("FName"));
                tra.setlName(resultSet.getString("LName"));
                tra.setDOB(resultSet.getString("DOB"));
                tra.setSex(resultSet.getString("Sex"));
                tra.setPhone(resultSet.getString("Phone"));
                tra.setEmail(resultSet.getString("Email"));
                tra.setAddress(resultSet.getString("Address"));
                tra.setPass(resultSet.getString("Pass"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return tra;
    }

    public static void main(String[] args) {
        TrainerLogIn tl = new TrainerLogIn(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
