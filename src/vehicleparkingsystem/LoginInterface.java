/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author autocount
 */
public class LoginInterface {
    private JButton b1;
    private JTextField t1;
    private JPasswordField password;
    private Statement stmt;
    private JFrame f;
    private Font myTitleFont = new Font("Serif",Font.BOLD,48);
    private Font myFont = new Font("Serif",Font.PLAIN,24);
    private Font myFont2 = new Font("Serif",Font.PLAIN,18);
    
    public LoginInterface() {
        dbConnect();
    }
    
    public void dbConnect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkmelaka_admin"+
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "testAdmin","123456");
            System.out.println("Database Connected");
            stmt=con.createStatement();  
            } catch(Exception e) {
                    e.printStackTrace();
            }
    }
    
    public void init() {
        
        BoxLayout boxlayout;
        JPanel p1,p2;
        Label title,l1;
        JLabel admin,pwd;
        

        f = new JFrame("Vehicle Parking System Admin");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boxlayout = new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS);
        f.setLayout(boxlayout);

        p1 = new JPanel(new FlowLayout());
        p2 = new JPanel(new FlowLayout());

        title = new Label("Vehicle Parking System");
        l1 = new Label("Admin Login");
        admin = new JLabel("Admin ID: ");
        pwd = new JLabel("Password: ");

        

        title.setFont(myTitleFont);
        title.setAlignment(Label.CENTER);


        f.setBackground(Color.white);
        f.setForeground(Color.black);

        l1.setFont(myFont);
        l1.setAlignment(Label.CENTER);
        admin.setFont(myFont2);
        pwd.setFont(myFont2);

        t1 = new JTextField(25);  
        password = new JPasswordField(25);
        password.setEchoChar('*');
        
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(checkIDExisted(e)) {
                    userDbConnect();
                    f.getContentPane().removeAll();
                    f.add(showUsers());
                    f.revalidate();
                    f.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wrong ID or password");
                }
            }
        };
        password.addActionListener(action);
        t1.addActionListener(action);

        b1 = new JButton("Sign In");
        b1.setBounds(50,100,60,30);
        b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(checkIDExisted(e)) {
                            userDbConnect();
                            f.getContentPane().removeAll();
                            f.add(showUsers());
                            f.revalidate();
                            f.repaint();
                        }
                }
        });

        p1.add(admin);
        p1.add(t1);
        p2.add(pwd);
        p2.add(password);
        p1.setMaximumSize(p1.getPreferredSize());
	p2.setMaximumSize(p1.getPreferredSize());

        f.add(title);
        f.add(l1);
        f.add(p1);
        f.add(p2);
        f.add(Box.createVerticalGlue());
        f.add(b1);
        f.add(Box.createVerticalGlue());
        f.setSize(800,400);
        f.setVisible(true);
    }
    
    
    private boolean checkIDExisted(ActionEvent e) {
        String adminID = t1.getText();
        String adminPwd = new String(password.getPassword());
        try {
                String query = "select * from admin where admin_ID = " + 
                adminID + " and admin_password = " + adminPwd;
                ResultSet rs = stmt.executeQuery(query);

                if(rs.next()) {
                        String adminName = rs.getString(4);
                        JOptionPane.showMessageDialog(null,"Welcome "+ adminName);
                        return true;
                } else {
                        JOptionPane.showMessageDialog(null, "Wrong ID or password");
                        return false;
                }

        } 
        catch(SQLException ex) {
                ex.printStackTrace();
        }
        return false;
    }
    
    public JPanel showUsers() {
        JPanel users = new JPanel();
        users.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx=0;
        gc.gridy=0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.gridwidth = 4;
        JLabel title = new JLabel("Users");
        title.setFont(myFont);
        title.setOpaque(true);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        users.add(title, gc);
        
        gc.gridx=0;
        gc.gridy=1;
        gc.weightx = 0.2;
        gc.gridwidth = 1;        
        JLabel col0 = new JLabel("ID");
        col0.setFont(myFont2);
        col0.setOpaque(true);
        users.add(col0, gc);

        gc.gridx=1;
        gc.gridy=1;
        gc.weightx = 0.5;
        gc.gridwidth = 1;        
        JLabel col1 = new JLabel("Name");
        col1.setFont(myFont2);
        col1.setOpaque(true);
        users.add(col1, gc);

        gc.gridx=2;
        gc.gridy=1;
        gc.weightx = 0.5;
        JLabel col2 = new JLabel("Email");
        col2.setFont(myFont2);
        col2.setOpaque(true);
        users.add(col2, gc);
        
        gc.gridx=3;
        gc.gridy=1;
        gc.weightx = 0.5;
        JLabel col3 = new JLabel("Balance");
        col3.setFont(myFont2);
        col3.setOpaque(true);
        users.add(col3, gc);
        
        gc.gridx=4;
        gc.gridy=1;  
        gc.weightx = 0.5;
        JLabel col4 = new JLabel("Car Plate No.");
        col4.setFont(myFont2);
        col4.setOpaque(true);
        users.add(col4, gc);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=2;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel noCol = new JPanel();
        noCol.setLayout(new BoxLayout(noCol, BoxLayout.Y_AXIS));
        noCol.setOpaque(true);
        users.add(noCol, gc);
        
        gc.gridx=1;
        gc.gridy=2;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(true);
        users.add(nameCol, gc);

        
        gc.gridx=2;
        gc.gridy=2;
        gc.weighty = 0.5;       
        JPanel emailCol = new JPanel();
        emailCol.setLayout(new BoxLayout(emailCol, BoxLayout.Y_AXIS));
        emailCol.setOpaque(true);
        users.add(emailCol, gc);
        
        
        gc.gridx=3;
        gc.gridy=2;
        gc.weighty = 0.5;      
        JPanel balanceCol = new JPanel();
        balanceCol.setLayout(new BoxLayout(balanceCol, BoxLayout.Y_AXIS));
        balanceCol.setOpaque(true);
        users.add(balanceCol, gc);
        
        
        gc.gridx=4;
        gc.gridy=2;
        gc.weighty =0.5;       
        JPanel carCol = new JPanel();
        carCol.setLayout(new BoxLayout(carCol, BoxLayout.Y_AXIS));
        carCol.setOpaque(true);
        users.add(carCol, gc);
        
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx=0;
        gc.gridy=6;
        gc.weightx = 0.5;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        JButton topUp = new JButton("Top Up");
        topUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        topUpBalance();
                        f.getContentPane().removeAll();
                        f.add(showUsers());
                        f.revalidate();
                        f.repaint();
                }
        });
        users.add(topUp,gc);
        
        try {
                String query = "select * from users";
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()) {
                        String userName = rs.getString(4);
                        String userEmail = rs.getString(2);
                        String userBalance = rs.getString(5);
                        String userCarPlateNo = rs.getString(6);
                        String noUser = rs.getString(1);
                        
                        JLabel name = new JLabel(userName,SwingConstants.CENTER);
                        JLabel email = new JLabel(userEmail,SwingConstants.CENTER);
                        JLabel balance = new JLabel(userBalance,SwingConstants.CENTER);
                        JLabel carNo = new JLabel(userCarPlateNo,SwingConstants.CENTER);
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

        return users;
    }
    
    
    public void userDbConnect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkmelaka"+
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "testAdmin","123456");
            System.out.println("Database Connected");
            stmt=con.createStatement();  
            } catch(Exception e) {
                e.printStackTrace();
            }
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
