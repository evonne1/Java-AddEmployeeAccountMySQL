
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.PasswordField;
import javax.swing.*;

/**
 * A Login GUI interface to varify the login and different level of access to display optional button for execute a query 
 * to create new tables to MySQL. 
 *
 * @author Yi fang Chen Date: 29/5/2018
 */
public class LoginScreen extends JFrame implements ActionListener {

    // login time calculate !! declare this in here
    int numAttempts = 0;
    // declare components
    JLabel lblUsername = new JLabel("Username: ");
    JLabel lblPassword = new JLabel("Password");
    JLabel lblResult = new JLabel();

    JTextField txfUsername = new JTextField(10);
    JPasswordField  txfPassword = new JPasswordField(10);
    JButton btnLogin = new JButton("Login");
    JButton btnRegister = new JButton("Register Employee");

      /**
     * create layout with the components
     *
     * @param
     * @return
     */
    public LoginScreen() {
        super("Login");
        this.setTitle("Login screen");
        this.setVisible(true);
        this.setBounds(500, 350, 250, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(lblUsername);
        this.add(txfUsername);

        this.add(lblPassword);
        this.add(txfPassword);

        this.add(btnLogin);
        this.add(lblResult);

        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);

        this.setLayout(new FlowLayout());

    }
    /**
     * connect to the MySQL with login data in Connection java class, everytime other function execute sql
     * query, need to run this method to link to database.
     *
     * @param
     * @return
     */
    public Connection getConnection() {
        Connection con = null;
        /* ************* GET MYSQL DETAILS ********************************/
        String url = ConnectionDetails.getUrl();
        String userName = ConnectionDetails.getUserName();
        String password = ConnectionDetails.getPassWord();
        try {
            // load the MYSQL driver
            Class.forName(ConnectionDetails.getDriver());
            con = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
        } catch (ClassNotFoundException e) {
        } finally {
            // Place code in here that will always be run.
        }
        return con;
    } // End method getConnection

    /**
     * method - what activities will be triggered when button clicked.
     *
     * @param
     * @return
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnLogin) {

            displayResult();

        }
        if (e.getSource() == btnRegister) {

            createTable();

        }

    }
    /**
     * method - connect to db, varify the login, display optional function for admin, and lock the button out if user login 
     * false many times.
     *
     * @param
     * @return
     */
    public void displayResult() {
        lblResult.setText("");
        // TODO add your handling code here:
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        String inpUsername = txfUsername.getText();
        String inpPW = txfPassword.getText();

        try {

            con = getConnection();
            stmt = con.createStatement();
            String sql
                    = "select * from tblusers WHERE userName= '" + inpUsername + "'";

            r = stmt.executeQuery(sql);

            int count = 0;
            while (r.next()) {
                String displayResult;
                if (r.getString("txtPassword").equals(inpPW)) {
                    if (r.getString("AccessLevel").equals("2")) {
                        displayResult = "User name found, password correct.";
                        this.add(btnRegister);
                        btnRegister.setVisible(true);
                        lblResult.setText(displayResult);
                    }
                    if (r.getString("AccessLevel").equals("1")) {

                        displayResult = "Login successful";
                        lblResult.setText(displayResult);
                        btnRegister.setVisible(false);
                    }

                }
                if (!(r.getString("txtPassword").equals(inpPW))) {
                    
                    lblResult.setText("Incorrect password.");
                    numAttempts = numAttempts + 1;
                    System.out.print(numAttempts);
                    
                    if (numAttempts > 3) {
                        lblResult.setText("“LOCKED OUT");
                        btnLogin.setEnabled(false);
                        
                    }

                }
                count = count + 1;
            }

            if (count == 0) {
                lblResult.setText("User name not found.");

                numAttempts = numAttempts + 1;
                if (numAttempts > 3) {
                    lblResult.setText("“LOCKED OUT");
                    btnLogin.setEnabled(false);
                }
            }
            if (count == 1) {

            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Place code in here that will always be run.
        }

        //stuModel.getDataFromDatabase();   //get data from database again
        // stuModel.fireTableDataChanged(); //refresh the JTable after changes 
        //    this.dispose();  //close this frame
        //parent.setVisible(true);  //make parent visible again
    }
    /**
     * method - create table in db.
     *
     * @param
     * @return
     */
    public void createTable() {
        Connection con = null;
        Statement stmt = null;

        try {

            con = getConnection();
            stmt = con.createStatement();
            String sql
                    = "CREATE TABLE if not exists tblEmployee(\n"
                    + "empID integer(3) NOT NULL,\n"
                    + "firstName varchar(20),\n"
                    + "lastName varchar(20),\n"
                    + "CONSTRAINT tblEmployee_pk PRIMARY KEY(empID)\n"
                    + ");";

            String sql1 = "CREATE TABLE if not exists tblBank(\n"
                    + "BankAcID integer(5) NOT NULL AUTO_INCREMENT,\n"
                    + "Bank varchar(20),\n"
                    + "BSB integer(20),\n"
                    + "Account integer(20),\n"
                    + "empID integer(3),\n"
                    + "CONSTRAINT tblBank_pk PRIMARY KEY(BankAcID),\n"
                    + "CONSTRAINT tblBank_fk FOREIGN KEY(empID) REFERENCES tblemployee(empID)\n"
                    + ");";

            System.out.print(sql);
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql1);
            JOptionPane.showMessageDialog(null, "new table \"tblEmployee\", \"tblBank\" created.");

            registerEmployee re = new registerEmployee(this);//run next dialog box once add button is sel
            this.setVisible(false);//set the current JFrame to invisible        
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Place code in here that will always be run.
        }
    }
}
