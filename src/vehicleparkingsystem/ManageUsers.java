/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author autocount
 */
public class ManageUsers {
    
    private final Font myFont2 = new Font("Serif",Font.PLAIN,18);
    private final Font myFont = new Font("Serif",Font.BOLD,24);
    private final DbConnect usersDb;
    private final Statement stmt;
    
    public ManageUsers() {
        usersDb = new DbConnect();
        stmt = usersDb.userDbConnect();
    }
    
    public JPanel showUsers(JFrame f) {
        JPanel wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel,BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel users = new JPanel();
        JScrollPane jsp = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setPreferredSize(new Dimension(800, 300));
        users.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=0;
        gc.weightx = 0.2;
        gc.gridwidth = 1;        
        JLabel col0 = new JLabel("ID");
        col0.setFont(myFont2);
        col0.setOpaque(true);
        users.add(col0, gc);

        gc.gridx=1;
        gc.gridy=0;
        gc.weightx = 0.5;
        gc.gridwidth = 1;        
        JLabel col1 = new JLabel("Name");
        col1.setFont(myFont2);
        col1.setOpaque(true);
        users.add(col1, gc);

        gc.gridx=2;
        gc.gridy=0;
        gc.weightx = 0.5;
        JLabel col2 = new JLabel("Email");
        col2.setFont(myFont2);
        col2.setOpaque(true);
        users.add(col2, gc);
        
        gc.gridx=3;
        gc.gridy=0;
        gc.weightx = 0.5;
        JLabel col3 = new JLabel("Balance");
        col3.setFont(myFont2);
        col3.setOpaque(true);
        users.add(col3, gc);
        
        gc.gridx=4;
        gc.gridy=0;  
        gc.weightx = 0.5;
        JLabel col4 = new JLabel("Car Plate No.");
        col4.setFont(myFont2);
        col4.setOpaque(true);
        users.add(col4, gc);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=1;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel noCol = new JPanel();
        noCol.setLayout(new BoxLayout(noCol, BoxLayout.Y_AXIS));
        noCol.setOpaque(true);
        users.add(noCol, gc);
        
        gc.gridx=1;
        gc.gridy=1;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(true);
        users.add(nameCol, gc);

        
        gc.gridx=2;
        gc.gridy=1;
        gc.weighty = 0.5;       
        JPanel emailCol = new JPanel();
        emailCol.setLayout(new BoxLayout(emailCol, BoxLayout.Y_AXIS));
        emailCol.setOpaque(true);
        users.add(emailCol, gc);
        
        
        gc.gridx=3;
        gc.gridy=1;
        gc.weighty = 0.5;      
        JPanel balanceCol = new JPanel();
        balanceCol.setLayout(new BoxLayout(balanceCol, BoxLayout.Y_AXIS));
        balanceCol.setOpaque(true);
        users.add(balanceCol, gc);
        
        
        gc.gridx=4;
        gc.gridy=1;
        gc.weighty =0.5;       
        JPanel carCol = new JPanel();
        carCol.setLayout(new BoxLayout(carCol, BoxLayout.Y_AXIS));
        carCol.setOpaque(true);
        users.add(carCol, gc);
        
        
        
        JButton topUp = new JButton("Top Up");
        topUp.addActionListener((ActionEvent e) -> {
            topUpBalance();
            f.getContentPane().removeAll();
            f.add(showUsers(f));
            f.revalidate();
            f.repaint();
        });
        
        
        JButton viewTransactions = new JButton("Transactions");
        viewTransactions.addActionListener((ActionEvent e) -> {
            ViewTransaction v = new ViewTransaction();
            f.getContentPane().removeAll();
            try {
                f.add(v.showTransactions(f));
            } catch (ParseException ex) {
                Logger.getLogger(ManageUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
            f.revalidate();
            f.repaint();
        });
        
        
        try {
                
                String query = "select * from users";
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()) {
                        String userName = rs.getString(4);
                        String userEmail = rs.getString(2);
                        String userBalance = rs.getString(5);
                        String userCarPlateNo = rs.getString(6);
                        String noUser = "   " +rs.getString(1);
                        
                        JLabel name = new JLabel(userName);
                        JLabel email = new JLabel(userEmail);
                        JLabel balance = new JLabel(userBalance);
                        JLabel carNo = new JLabel(userCarPlateNo);
                        JLabel num = new JLabel(noUser);
                       
                        nameCol.add(name);
                        noCol.add(num);
                        emailCol.add(email);
                        balanceCol.add(balance);
                        carCol.add(carNo);
                        users.repaint();
                } 

        } 
        catch(SQLException ex) {
                ex.printStackTrace();
        }
        buttonPanel.add(topUp);
        buttonPanel.add(viewTransactions);
        JLabel title = new JLabel("Users");
        title.setFont(myFont);
        JLabel inviBox = new JLabel(" ");
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        titlePanel.setMaximumSize(titlePanel.getPreferredSize());
        wholePanel.add(titlePanel);
        wholePanel.add(jsp);
        wholePanel.add(inviBox);
        wholePanel.add(buttonPanel);
        return wholePanel;
    }

    
    private void topUpBalance() {
        JTextField noUser = new JTextField(5);
        JTextField amount = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("ID of User:"));
        myPanel.add(noUser);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Amount:"));
        myPanel.add(amount);

        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Please Enter ID of User and Amount to Top Up", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String no = noUser.getText();
                String topAmount = amount.getText();
                String query1 = "select user_balance from users where id = " + no;
                ResultSet rs = stmt.executeQuery(query1);
                double value = 0;
                if(rs.next()) {
                    value = Double.parseDouble(rs.getString(1));
                } else {
                    JOptionPane.showMessageDialog(null,"Error");
                }
                value += Double.parseDouble(topAmount);
                topAmount = Double.toString(value);
                String query = "update users set user_balance = "+ topAmount + " where id = " + no;
                int i = stmt.executeUpdate(query);
                System.out.println("Succes top Up");
            }
           catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

  
}
