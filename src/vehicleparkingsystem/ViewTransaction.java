/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.SwingConstants;

/**
 *
 * @author ching
 */
public class ViewTransaction {
    
    private final Font myFont2 = new Font("Serif",Font.PLAIN,18);
    private final Font myFont = new Font("Serif",Font.PLAIN,24);
    private final DbConnect transactionsDb;
    
    public ViewTransaction() {
        transactionsDb = new DbConnect();
    }
    
    public JPanel showTransactions(JFrame f) {
        Statement stmt = transactionsDb.userDbConnect();
        Statement stmt2 = transactionsDb.userDbConnect();
        JPanel transactions = new JPanel();
        transactions.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        JScrollPane jsp = new JScrollPane(transactions,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_NEVER);
        JPanel wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel,BoxLayout.Y_AXIS));
        jsp.setPreferredSize(new Dimension(800,380));

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
        
        
        
        wholePanel.add(new FilterTransactions(f));
        wholePanel.add(jsp);
        return wholePanel;
    }
    
    public JPanel showFilteredTransactions(JFrame f, String[] data) {
        Statement stmt3 = transactionsDb.userDbConnect();
        Statement stmt4 = transactionsDb.userDbConnect();
        JPanel transactions = new JPanel();
        transactions.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        JPanel wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel,BoxLayout.Y_AXIS));
        

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
        
        String specificLocation = data[0];
        String amountOrder = data[1];
        String specificStartDate = data[2];
        String specificEndDate = data[3];
        String specificStartTime = data[4];
        String specificEndTime = data[5];
        String ord;
        JPanel constraintsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        constraintsPanel.setBorder(BorderFactory.createTitledBorder("Constraints Set"));
        String startTimeconstraints = "Start date: "+specificStartDate+ " " + specificStartTime ;
        String endTimeconstraints =  "End Date: " +specificEndDate+ " " + specificEndTime;
        JLabel startTimeConstraintsLabel = new JLabel(startTimeconstraints);
        JLabel endTimeConstraintsLabel = new JLabel(endTimeconstraints);
        
        if("ASC".equals(amountOrder)) 
            ord = "Ascending";
        else
            ord = "Descending";

        String locConstraints = "Location: " + specificLocation ;
        String amountConstraints = "Amount Order: "+ ord;
        JLabel locConstraintsLabel = new JLabel(locConstraints);
        JLabel amountConstraintsLabel = new JLabel(amountConstraints);
        constraintsPanel.add(startTimeConstraintsLabel);
        constraintsPanel.add(endTimeConstraintsLabel);
        constraintsPanel.add(locConstraintsLabel);
        constraintsPanel.add(amountConstraintsLabel);
        constraintsPanel.setSize(new Dimension(800,80));
        
        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder("Total"));
        int totalUsers = 0;
        double totalAmount = 0;
        String totalUsersString = "Total Users: ";
        String totalAmountString = "Total Amount: ";
        
        
        if(null != specificLocation) 
            switch (specificLocation) {
            case "Bukit Beruang":
                specificLocation = "1";
                break;
            case "Batu Berendam":
                specificLocation = "2";
                break;
            case "Melaka Raya":
                specificLocation = "3"; 
                break;
            case "Kota Laksamana":
                specificLocation = "4";
                break;
            case "Batu Baru":
                specificLocation = "5";
                break;
            default:
                break;
        }
        try {
                String query = "select * from transaction where trans_loc = "+ specificLocation + 
                        " and trans_starttime >= '" + specificStartTime + "' and trans_endtime <= '" + specificEndTime +"' ORDER by trans_amount " + amountOrder ;
                ResultSet rs = stmt3.executeQuery(query);

                while(rs.next()) {
                    if(rs.getString(4).equals(specificStartDate) && (rs.getString(6).equals(specificEndDate))) {
                        String userId = "   "+ rs.getString(2);
                        String trans_start = rs.getString(4) + " " + rs.getString(5);
                        String trans_end = rs.getString(6) + " " + rs.getString(7);
                        String location_id = rs.getString(8);
                        String location ="";
                        String amount = rs.getString(9);
                        try {
                            String queryLoc = "select * from location where id = " + location_id;
                            ResultSet rs2 = stmt4.executeQuery(queryLoc);
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
                        
                        totalUsers++;
                        totalAmount += Double.parseDouble(amount);
                        totalAmount = round(totalAmount,2);
                } 
            }
        } 
        catch(SQLException ex) {
                ex.printStackTrace();
        }
        totalUsersString += Integer.toString(totalUsers);
        totalAmountString += String.format("%.2f",totalAmount);
        JLabel totalUserLabel = new JLabel(totalUsersString);
        JLabel totalAmountLabel = new JLabel(totalAmountString);
        totalPanel.add(totalUserLabel);
        totalPanel.add(totalAmountLabel);
        
        JScrollPane jsp = new JScrollPane(transactions,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setPreferredSize(new Dimension(800,150));
        wholePanel.add(new FilterTransactions(f));
        wholePanel.add(constraintsPanel);
        wholePanel.add(jsp);
        wholePanel.add(totalPanel);
        return wholePanel;
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
}
