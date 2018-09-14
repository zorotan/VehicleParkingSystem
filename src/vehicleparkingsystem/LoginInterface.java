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
    public JFrame f;
    private Font myTitleFont = new Font("Serif",Font.BOLD,48);
    private Font myFont = new Font("Serif",Font.ITALIC,24);
    private Font myFont2 = new Font("Serif",Font.PLAIN,18);
    
    public LoginInterface() {
        DbConnect adminDb = new DbConnect();
        stmt = adminDb.dbConnect();
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
        f.getContentPane().setBackground(new Color(50,132,214));

        p1 = new JPanel(new FlowLayout());
        p2 = new JPanel(new FlowLayout());

        title = new Label("Vehicle Parking System");
        l1 = new Label("Admin Login");
        admin = new JLabel("Admin ID: ");
        pwd = new JLabel("Password: ");

        title.setFont(myTitleFont);
        title.setAlignment(Label.CENTER);
        
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
                    ManageUsers manage = new ManageUsers();
                    f.getContentPane().removeAll();
                    f.add(manage.showUsers(f));
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
                            ManageUsers manage = new ManageUsers();
                            f.getContentPane().removeAll();
                            f.add(manage.showUsers(f));
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
        p1.setBackground(new Color(50,132,214));
        p2.setBackground(new Color(50,132,214));
        

        f.add(title);
        f.add(l1);
        f.add(p1);
        f.add(p2);
        f.add(Box.createVerticalGlue());
        f.add(b1);
        f.add(Box.createVerticalGlue());
        f.setSize(800,500);
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
}
