/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author ching
 */
public class ViewTransaction {
    
    private Font myFont2 = new Font("Serif",Font.PLAIN,18);
    private Font myFont = new Font("Serif",Font.PLAIN,24);
    private DbConnect transactionsDb;
    private Statement stmt;
    private Statement stmt2;
    
    public ViewTransaction() {
        transactionsDb = new DbConnect();
        stmt = transactionsDb.userDbConnect();
        stmt2 = transactionsDb.userDbConnect();

    }
    
    public JPanel showTransactions(JFrame f) {
        JPanel transactions = new JPanel();
        transactions.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx=0;
        gc.gridy=0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.gridwidth = 4;
        JLabel title = new JLabel("Transactions");
        title.setFont(myFont);
        title.setOpaque(true);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        transactions.add(title, gc);
        
        gc.gridx=0;
        gc.gridy=1;
        gc.weightx = 0.2;
        gc.gridwidth = 1;        
        JLabel col0 = new JLabel("  User ID");
        col0.setFont(myFont2);
        col0.setOpaque(true);
        transactions.add(col0, gc);

        gc.gridx=1;
        gc.gridy=1;
        gc.weightx = 0.5;
        gc.gridwidth = 1;        
        JLabel col1 = new JLabel("Start");
        col1.setFont(myFont2);
        col1.setOpaque(true);
        transactions.add(col1, gc);

        gc.gridx=2;
        gc.gridy=1;
        gc.weightx = 0.5;
        JLabel col2 = new JLabel("End");
        col2.setFont(myFont2);
        col2.setOpaque(true);
        transactions.add(col2, gc);
        
        gc.gridx=3;
        gc.gridy=1;
        gc.weightx = 0.5;
        JLabel col3 = new JLabel("Location");
        col3.setFont(myFont2);
        col3.setOpaque(true);
        transactions.add(col3, gc);
        
        gc.gridx=4;
        gc.gridy=1;  
        gc.weightx = 0.5;
        JLabel col4 = new JLabel("Amount");
        col4.setFont(myFont2);
        col4.setOpaque(true);
        transactions.add(col4, gc);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=2;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel idCol = new JPanel();
        idCol.setLayout(new BoxLayout(idCol, BoxLayout.Y_AXIS));
        idCol.setOpaque(true);
        transactions.add(idCol, gc);
        
        gc.gridx=1;
        gc.gridy=2;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel startCol = new JPanel();
        startCol.setLayout(new BoxLayout(startCol, BoxLayout.Y_AXIS));
        startCol.setOpaque(true);
        transactions.add(startCol, gc);

        
        gc.gridx=2;
        gc.gridy=2;
        gc.weighty = 0.5;       
        JPanel endCol = new JPanel();
        endCol.setLayout(new BoxLayout(endCol, BoxLayout.Y_AXIS));
        endCol.setOpaque(true);
        transactions.add(endCol, gc);
        
        
        gc.gridx=3;
        gc.gridy=2;
        gc.weighty = 0.5;      
        JPanel locCol = new JPanel();
        locCol.setLayout(new BoxLayout(locCol, BoxLayout.Y_AXIS));
        locCol.setOpaque(true);
        transactions.add(locCol, gc);
        
        
        gc.gridx=4;
        gc.gridy=2;
        gc.weighty =0.5;       
        JPanel amountCol = new JPanel();
        amountCol.setLayout(new BoxLayout(amountCol, BoxLayout.Y_AXIS));
        amountCol.setOpaque(true);
        transactions.add(amountCol, gc);
        
        /*gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx=2;
        gc.gridy=6;
        gc.weightx = 0.5;
        gc.anchor = GridBagConstraints.SOUTH;
        JButton topUp = new JButton("Top Up");
        topUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        topUpBalance();
                        f.getContentPane().removeAll();
                        f.add(showUsers(f));
                        f.revalidate();
                        f.repaint();
                }
        });
        transactions.add(topUp,gc);*/
        
        try {
                
                String query = "select * from transaction";
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()) {
                        String userId = "   "+ rs.getString(2);
                        String trans_start = rs.getString(4) + " " + rs.getString(5);
                        String trans_end = rs.getString(6) + " " + rs.getString(7);
                        String location_id = rs.getString(8);
                        String location ="";
                        String amount = rs.getString(9);
                        try {
                            String queryLoc = "select * from location where id = " + location_id;
                            ResultSet rs2 = stmt2.executeQuery(queryLoc);
                            while(rs2.next()) {
                                location = rs2.getString(2);
                            }

                        }
                        catch(SQLException ex) {
                            ex.printStackTrace();
                        }
                        
                        JLabel id = new JLabel(userId,SwingConstants.CENTER);
                        JLabel start = new JLabel(trans_start,SwingConstants.CENTER);
                        JLabel end = new JLabel(trans_end,SwingConstants.CENTER);
                        JLabel loc = new JLabel(location,SwingConstants.CENTER);
                        JLabel amount_label = new JLabel(amount);
                       
                        idCol.add(id);
                        startCol.add(start);
                        endCol.add(end);
                        locCol.add(loc);
                        amountCol.add(amount_label);
                        transactions.repaint();
                } 

        } 
        catch(SQLException ex) {
                ex.printStackTrace();
        }

        return transactions;
    }
}
