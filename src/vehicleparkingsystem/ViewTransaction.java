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
import java.awt.PopupMenu;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    
    public ViewTransaction() {
    }
    
    public JPanel showTransactions(JFrame f) throws ParseException {
        JPanel j = showTransactions(f,null);
        return j;
    }
    
    public JPanel showTransactions(JFrame f, String[] data) throws ParseException {
        boolean dataExisted = false;
        if(data!= null) {
            dataExisted = true;
        }
        Statement stmt = DbConnect.userDbConnect();
        Statement stmt2 = DbConnect.userDbConnect();
        JPanel transactionPanel = new JPanel();
        JPanel transactions = new JPanel();
        transactions.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        JPanel wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel,BoxLayout.Y_AXIS));
        JLabel transactionLabel = new JLabel("Transactions");
        transactionLabel.setFont(myFont2);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=0;
        gc.weightx = 0.2;
        gc.gridwidth = 1;        
        JLabel col0 = new JLabel("  User ID");
        col0.setFont(myFont2);
        col0.setOpaque(true);
        transactions.add(col0, gc);

        gc.gridx=1;
        gc.gridy=0;
        gc.weightx = 0.5;
        gc.gridwidth = 1;        
        JLabel col1 = new JLabel("Start");
        col1.setFont(myFont2);
        col1.setOpaque(true);
        transactions.add(col1, gc);

        gc.gridx=2;
        gc.gridy=0;
        gc.weightx = 0.5;
        JLabel col2 = new JLabel("End");
        col2.setFont(myFont2);
        col2.setOpaque(true);
        transactions.add(col2, gc);
        
        gc.gridx=3;
        gc.gridy=0;
        gc.weightx = 0.5;
        JLabel col3 = new JLabel("Location");
        col3.setFont(myFont2);
        col3.setOpaque(true);
        transactions.add(col3, gc);
        
        gc.gridx=4;
        gc.gridy=0;  
        gc.weightx = 0.5;
        JLabel col4 = new JLabel("Amount");
        col4.setFont(myFont2);
        col4.setOpaque(true);
        transactions.add(col4, gc);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=1;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel idCol = new JPanel();
        idCol.setLayout(new BoxLayout(idCol, BoxLayout.Y_AXIS));
        idCol.setOpaque(true);
        transactions.add(idCol, gc);
        
        gc.gridx=1;
        gc.gridy=1;
        gc.weighty = 0.5;
        gc.gridwidth = 1;        
        JPanel startCol = new JPanel();
        startCol.setLayout(new BoxLayout(startCol, BoxLayout.Y_AXIS));
        startCol.setOpaque(true);
        transactions.add(startCol, gc);

        
        gc.gridx=2;
        gc.gridy=1;
        gc.weighty = 0.5;       
        JPanel endCol = new JPanel();
        endCol.setLayout(new BoxLayout(endCol, BoxLayout.Y_AXIS));
        endCol.setOpaque(true);
        transactions.add(endCol, gc);
        
        
        gc.gridx=3;
        gc.gridy=1;
        gc.weighty = 0.5;      
        JPanel locCol = new JPanel();
        locCol.setLayout(new BoxLayout(locCol, BoxLayout.Y_AXIS));
        locCol.setOpaque(true);
        transactions.add(locCol, gc);
        
        
        gc.gridx=4;
        gc.gridy=1;
        gc.weighty =0.5;       
        JPanel amountCol = new JPanel();
        amountCol.setLayout(new BoxLayout(amountCol, BoxLayout.Y_AXIS));
        amountCol.setOpaque(true);
        transactions.add(amountCol, gc);
        
        String specificLocation ="";
        String amountOrder ="";
        String specificStartDate="";
        String specificEndDate = "";
        String specificStartTime ="";
        String specificEndTime = "";
        String noSpecificDate = "";
        if(dataExisted) {
        specificLocation = data[0];
        amountOrder = data[1];
        specificStartDate = data[2];
        specificEndDate = data[3];
        specificStartTime = data[4];
        specificEndTime = data[5];
        noSpecificDate = data[6];
        }
        JPanel constraintsPanel = new JPanel();
        if(dataExisted) {
            constraintsPanel.setLayout(new GridLayout(1, 4, 5, 5));
            constraintsPanel.setBorder(BorderFactory.createTitledBorder("Constraints Set"));
            String startDateconstraints = "";
            String endDateconstraints = "";
            String startTimeconstraints =specificStartTime ;
            String endTimeconstraints =specificEndTime ;
            if("1".equals(noSpecificDate)) {
                startDateconstraints ="Start date: <None>  " + startTimeconstraints;
                endDateconstraints ="End Date: <None>  " + endTimeconstraints;
            } else {
                startDateconstraints = "Start date: "+specificStartDate+ " " + specificStartTime ;
                endDateconstraints =  "End Date: " +specificEndDate+ " " + specificEndTime;
            }
            JLabel startTimeConstraintsLabel = new JLabel(startDateconstraints);
            JLabel endTimeConstraintsLabel = new JLabel(endDateconstraints);
            String locConstraints = "Location: " + specificLocation ;
            String amountConstraints = "Amount Order: "+ amountOrder;
            JLabel locConstraintsLabel = new JLabel(locConstraints);
            JLabel amountConstraintsLabel = new JLabel(amountConstraints);
            constraintsPanel.add(startTimeConstraintsLabel);
            constraintsPanel.add(endTimeConstraintsLabel);
            constraintsPanel.add(locConstraintsLabel);
            constraintsPanel.add(amountConstraintsLabel);
            constraintsPanel.setPreferredSize(new Dimension(800,30));
        }
        
        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder("Total"));
        int totalUsers = 0;
        double totalAmount = 0;
        String totalUsersString = "Total Users: ";
        String totalAmountString = "      Total Amount: ";
        
        if(dataExisted) {
            if(null != specificLocation) 
                switch (specificLocation) {
                case "<None>":
                    specificLocation = "0";
                    break;
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
                case "Bukit Baru":
                    specificLocation = "5";
                    break;
                default:
                    break;
            }
            if(null != amountOrder)
                switch (amountOrder) {
                case "<None>":
                    amountOrder = "0";
                    break;
                case "Ascending":
                    amountOrder = "ASC";
                    break;
                case "Descending":
                    amountOrder = "DESC";
                    break;
                default:
                    break;
                }
        }
        
        String query,queryLoc;
        try {
            if(dataExisted) {
                if(!"0".equals(specificLocation) && !"0".equals(amountOrder)) {
                    query = "select *  from transaction where trans_loc_id = "+ specificLocation + 
                        " and trans_starttime >= '" + specificStartTime + "' and trans_endtime <= '" + 
                            specificEndTime +"' ORDER by trans_amount  " + amountOrder ;
                }else if(!"0".equals(specificLocation) && "0".equals(amountOrder)){
                    query = "select * from transaction where trans_loc_id = "+ specificLocation + 
                        " and trans_starttime >= '" + specificStartTime + "' and trans_endtime <= '" + 
                            specificEndTime +"' ORDER by trans_start DESC" ;
                }else if("0".equals(specificLocation) && !"0".equals(amountOrder)){
                    query = "select * from transaction where trans_starttime >= '" + specificStartTime + "' and trans_endtime <= '" + 
                            specificEndTime +"' ORDER by trans_amount  " + amountOrder;
                }
                else {
                    query = "select * from transaction where trans_starttime >= '" + specificStartTime + "' and trans_endtime <= '" + 
                            specificEndTime +"' ORDER by trans_start DESC";
                }
            } else {
                query = "select * from transaction ORDER by trans_start DESC";
            }
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                if(rs.wasNull()) {
                    JOptionPane.showMessageDialog(null,"No record found.","Null",JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                String userId =null;
                String trans_start=null;
                String trans_end=null;
                String location_id= null;
                String location=null;
                String amount =null;
                if(dataExisted) {
                    if(!noSpecificDate.equals("1")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(specificStartDate);
                        Date date2 = sdf.parse(rs.getString(4));
                        Date date3 = sdf.parse(specificEndDate);
                        Date date4 = sdf.parse(rs.getString(6));

                        if(!date1.after(date2) && !date3.before(date4)) {
                            userId = "   "+ rs.getString(2);
                            trans_start = rs.getString(4) + " " + rs.getString(5);
                            trans_end = rs.getString(6) + " " + rs.getString(7);
                            location_id = rs.getString(8);
                            location ="";
                            BigDecimal amountbd = rs.getBigDecimal(9);
                            double amountDouble = amountbd.doubleValue();
                            amountDouble = round(amountDouble,2);
                            amount = String.format("%.2f",amountDouble);
                            try {
                                queryLoc = "select * from location where trans_loc_id = " + location_id;
                                ResultSet rs2 = stmt2.executeQuery(queryLoc);
                                while(rs2.next()) {
                                    location = rs2.getString(2);
                                }
                            }
                            catch(SQLException ex) {
                                ex.printStackTrace();
                            }
                        } 
                    }
                    else {
                    userId = "   "+ rs.getString(2);
                    trans_start = rs.getString(4) + " " + rs.getString(5);
                    trans_end = rs.getString(6) + " " + rs.getString(7);
                    location_id = rs.getString(8);
                    location ="";
                    BigDecimal amountbd = rs.getBigDecimal(9);
                    double amountDouble = amountbd.doubleValue();
                    amountDouble = round(amountDouble,2);
                    amount = String.format("%.2f",amountDouble);
                    try {
                        queryLoc = "select * from location where trans_loc_id = " + location_id;
                        ResultSet rs2 = stmt2.executeQuery(queryLoc);
                        while(rs2.next()) {
                            location = rs2.getString(2);
                        }
                    }
                    catch(SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                } else {
                    userId = "   "+ rs.getString(2);
                    trans_start = rs.getString(4) + " " + rs.getString(5);
                    trans_end = rs.getString(6) + " " + rs.getString(7);
                    location_id = rs.getString(8);
                    location ="";
                    BigDecimal amountbd = rs.getBigDecimal(9);
                    double amountDouble = amountbd.doubleValue();
                    amountDouble = round(amountDouble,2);
                    amount = String.format("%.2f",amountDouble);
                    try {
                        queryLoc = "select * from location where trans_loc_id = " + location_id;
                        ResultSet rs2 = stmt2.executeQuery(queryLoc);
                        while(rs2.next()) {
                            location = rs2.getString(2);
                        }
                    }
                    catch(SQLException ex) {
                        ex.printStackTrace();
                    }
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

                totalUsers += (amount == null) ? 0: 1;
                totalAmount += (amount == null) ? 0: Double.parseDouble(amount);
                totalAmount = round(totalAmount,2);
            }
        } 
        catch(SQLException ex) {
                ex.printStackTrace();
        }
        totalUsersString += Integer.toString(totalUsers);
        if(totalUsers == 0) {
            JOptionPane.showMessageDialog(null,"No record found.","Null",JOptionPane.INFORMATION_MESSAGE);
        }
        totalAmountString += String.format("%.2f",totalAmount);
        JLabel totalUserLabel = new JLabel(totalUsersString);
        JLabel totalAmountLabel = new JLabel(totalAmountString);
        totalPanel.add(totalUserLabel);
        totalPanel.add(totalAmountLabel);
        totalPanel.setPreferredSize(new Dimension(800,30));
        
        JScrollPane jsp = new JScrollPane(transactions,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_NEVER);
        wholePanel.add(new FilterTransactions(f));
        if(dataExisted) {
            wholePanel.add(constraintsPanel);
            jsp.setPreferredSize(new Dimension(790,140));
        } else {
            jsp.setPreferredSize(new Dimension(790,180));
        }
        transactionPanel.add(transactionLabel);
        transactionPanel.add(jsp);
        wholePanel.add(transactionPanel);
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
