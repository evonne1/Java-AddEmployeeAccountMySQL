
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

/**
 * A Register employee GUI interface to varify if the employee exists in database, and insert record to database
 * which has foreign key relationship between tables.
 *
 * @author Yi fang Chen Date: 29/5/2018
 */
public class registerEmployee extends JFrame implements ActionListener {
    //declare components
    JLabel lblTitle = new JLabel("Employee Registration");
    JLabel lblSubTitle = new JLabel("Employee Details          ");
    JLabel lblSubTitle1 = new JLabel("Bank Details");
    JLabel lblBlank = new JLabel();
    JLabel lblEmpID = new JLabel("Employee ID: ");
    JLabel lblFName = new JLabel("First name: ");
    JLabel lblLName = new JLabel("Last name: ");
    JLabel lblBank = new JLabel("Bank: ");
    JLabel lblBSB = new JLabel("BSB number: ");
    JLabel lblAcc = new JLabel("Account number: ");
    JLabel lblResult = new JLabel();

    JTextField txfEmpID = new JTextField(10);
    JTextField txfFName = new JTextField(10);
    JTextField txfLName = new JTextField(10);
    JTextField txfBank = new JTextField(10);
    JTextField txfBSB = new JTextField(10);
    JTextField txfAcc = new JTextField(10);

    JButton btnRegist = new JButton("Register");
    JButton btnClear = new JButton("Clear");

    JPanel pnlTitle = new JPanel(new GridLayout(1, 1));

    JPanel pnlLeft = new JPanel(new GridLayout(4, 2));
    JPanel pnlRight = new JPanel(new GridLayout(4, 2));
    JPanel pnlButton = new JPanel();
    JPanel pnlSubTitle = new JPanel(new GridLayout(1, 2));
      /**
     * create layout with the components
     *
     * @param
     * @return
     */
    public registerEmployee(LoginScreen login) {
        super("Register");
        this.setTitle("Register employee");
        this.setVisible(true);
        this.setBounds(500, 350, 500, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pnlTitle.add(lblTitle);
        pnlTitle.setBorder(BorderFactory.createTitledBorder(""));
        lblTitle.setForeground(Color.black);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));

        pnlSubTitle.add(lblSubTitle);
        pnlSubTitle.add(lblSubTitle1);

        lblSubTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblSubTitle.setForeground(Color.red);
        lblSubTitle1.setFont(new Font("Arial", Font.BOLD, 20));
        lblSubTitle1.setForeground(Color.red);

        pnlLeft.add(lblEmpID);
        pnlLeft.add(txfEmpID);
        pnlLeft.add(lblFName);
        pnlLeft.add(txfFName);
        pnlLeft.add(lblLName);
        pnlLeft.add(txfLName);

        pnlRight.add(lblBank);
        pnlRight.add(txfBank);
        pnlRight.add(lblBSB);
        pnlRight.add(txfBSB);
        pnlRight.add(lblAcc);
        pnlRight.add(txfAcc);

        pnlButton.add(btnRegist);
        pnlButton.add(btnClear);
        pnlButton.add(lblResult);

        //add panels to the JFrame container
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout(20, 30));
        c.add(pnlTitle, BorderLayout.NORTH);
        c.add(pnlSubTitle, BorderLayout.NORTH);
        c.add(pnlLeft, BorderLayout.WEST);
        c.add(pnlRight, BorderLayout.EAST);
        c.add(pnlButton, BorderLayout.SOUTH);

        btnRegist.addActionListener(this);
        btnClear.addActionListener(this);

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

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRegist) {

            registerEmployee();

        }
        if (e.getSource() == btnClear) {

            clearTable();

        }

    }
    /**
     * insert records to db
     *
     * @param
     * @return
     */
    public void registerEmployee() {

        // TODO add your handling code here:
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        String inpEmpID = txfEmpID.getText();
        String inpFName = txfFName.getText();
        String inpLName = txfLName.getText();
        String inpBank = txfBank.getText();
        String inpBSB = txfBSB.getText();
        String inpAcc = txfAcc.getText();

        try {

            con = getConnection();
            stmt = con.createStatement();
            String sql
                    = "select * from tblemployee WHERE empID= '" + inpEmpID + "'";

            r = stmt.executeQuery(sql);

            int count = 0;
            while (r.next()) {
                String displayResult;

                displayResult = "Error - duplicated ID";
                lblResult.setText(displayResult);
                txfEmpID.setText("");

                count = count + 1;
            }

            if (count == 0) {
                lblResult.setText("");

                String sqlInsert
                        = "INSERT INTO tblemployee VALUES(' " + inpEmpID + " ',' " + inpFName + " ',' " + inpLName + "' )";
                String sqlInsert1
                        = "INSERT INTO tblbank(Bank, BSB,Account ,empID)\n"
                        + "SELECT '" + inpBank + " ',' " + inpBSB + "', '" + inpAcc + "', empID\n"
                        + "FROM tblemployee\n"
                        + "WHERE empID = '" + inpEmpID + "'";
                System.err.print(sqlInsert1);
                stmt.executeUpdate(sqlInsert);
                stmt.executeUpdate(sqlInsert1);

            }
            if (count == 1) {

            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Place code in here that will always be run.
        }

    }
    /**
     * reset text fields. 
     *
     * @param
     * @return
     */
    public void clearTable() {
        txfEmpID.setText("");
        txfFName.setText("");
        txfLName.setText("");
        txfBank.setText("");
        txfBSB.setText("");
        txfAcc.setText("");
    }

}
