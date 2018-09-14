/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Jack
 */
public class FilterTransactions extends JPanel implements ItemListener{
    private String location;
    private String amount;
    private JComboBox startYear, startMonth, startDay;
    private JComboBox endYear, endMonth, endDay;
    /* This is the start date used for the date dropdowns */
    private Calendar startDate = Calendar.getInstance();
    /* This is the end date used for the date dropdowns */
    private Calendar endDate = Calendar.getInstance();

    
    public FilterTransactions (JFrame f) {
        JPanel endDatePanel = new JPanel(new FlowLayout());
        JPanel locationPanel = new JPanel(new FlowLayout());
        JPanel amountPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JLabel location_label = new JLabel("Location: ");
        JLabel amount_label = new JLabel("   Amount: ");
        
        
        ViewTransaction vt = new ViewTransaction();
        
        JButton filter = new JButton("Filter");
        filter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        f.getContentPane().removeAll();
                        f.add(vt.showTransactions(f));
                        f.revalidate();
                        f.repaint();
                }
        });
        
        String [] allLocation = {"Bukit Beruang","Batu Berendam","Melaka Raya","Kota Laksamana","Bukit Baru"};
        JComboBox locationList = new JComboBox(allLocation);
        locationList.setEditable(true);
        locationList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String loc = (String)locationList.getSelectedItem();
                location = loc;
            }
        });
        
        JPanel datesPanel = new JPanel();
        datesPanel.setBorder(BorderFactory.createTitledBorder
            ("Dates of New Semester"));
        JPanel datesInnerPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JLabel startDateLabel = new JLabel("Start Date:  ", SwingConstants.RIGHT);
        startYear = new JComboBox();
        buildYearsList(startYear);
        startYear.setSelectedIndex(5);
        startMonth = new JComboBox();
        buildMonthsList(startMonth);
        startMonth.setSelectedIndex(startDate.get(Calendar.MONTH));
        startDay = new JComboBox();
        buildDaysList(startDate, startDay, startMonth);
        startDay.setSelectedItem(Integer.toString(startDate.get(Calendar.DATE)));
        startYear.addItemListener(this);
        startMonth.addItemListener(this);
        startDay.addItemListener(this);
        datesInnerPanel.add(startDateLabel);
        datesInnerPanel.add(startMonth);
        datesInnerPanel.add(startDay);
        datesInnerPanel.add(startYear);
        JLabel endDateLabel = new JLabel("End Date:  ", SwingConstants.RIGHT);
        endYear = new JComboBox();
        buildYearsList(endYear);
        endYear.setSelectedIndex(5);
        endMonth = new JComboBox();
        buildMonthsList(endMonth);
        endMonth.setSelectedIndex(endDate.get(Calendar.MONTH));
        endDay = new JComboBox();
        buildDaysList(endDate, endDay, endMonth);
        endDay.setSelectedItem(Integer.toString(endDate.get(Calendar.DATE)));
        endYear.addItemListener((ItemListener) this);
        endMonth.addItemListener((ItemListener) this);
        endDay.addItemListener((ItemListener) this);
        datesInnerPanel.add(endDateLabel);
        datesInnerPanel.add(endMonth);
        datesInnerPanel.add(endDay);
        datesInnerPanel.add(endYear);
        datesPanel.add(datesInnerPanel, BorderLayout.CENTER);
        add(datesPanel);
        
        locationPanel.add(location_label);
        locationPanel.add(locationList);
        
        add(locationPanel);
        add(amount_label);
        
        buttonPanel.add(filter);
        add(buttonPanel);
        
        
        setVisible(true);
    }
    
    /**
     * This method builds the list of years for the start
     * date and end date of the semester
     * @param yearsList The combo box containing the years
     */
    private void buildYearsList(JComboBox yearsList) {

        int currentYear = startDate.get(Calendar.YEAR);

        for (int yearCount = currentYear - 5; yearCount <= currentYear + 5; yearCount++)
            yearsList.addItem(Integer.toString(yearCount));
    }

    /**
     * This method builds the list of months for the start
     * date and end date of the semester
     * @param monthsList The combo box containing the months
     */
    private void buildMonthsList(JComboBox monthsList) {

        monthsList.removeAllItems();
        for (int monthCount = 1; monthCount < 13; monthCount++)
            monthsList.addItem(monthCount);
    }

    /**
     * This method builds the list of years for the start
     * date and end date of the semester
     * @param dateIn The current date, which will be used for
     * the initial date of the lists
     * @param daysList The combo box that will contain the days
     * @param monthsList The combo box that will contain the months
     */
    private void buildDaysList(Calendar dateIn, JComboBox daysList, JComboBox monthsList) {

        daysList.removeAllItems();
        dateIn.set(Calendar.MONTH, monthsList.getSelectedIndex());
        int lastDay = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int dayCount = 1; dayCount <= lastDay; dayCount++)
            daysList.addItem(Integer.toString(dayCount));
    }

    /**
     * This method is called when a dropdown selection
     * changes
     * @param event This occurs when a dropdown changes values
     */
    public void itemStateChanged(ItemEvent event) {

        if (event.getSource() == startYear &&
            event.getStateChange() == ItemEvent.SELECTED) {

            int year = Integer.parseInt((String)startYear.getSelectedItem());
            startDate.set(Calendar.YEAR, year);
            startMonth.setSelectedIndex(0);
            startDate.set(Calendar.MONTH, 0);
            buildDaysList(startDate, startDay, startMonth);
            startDate.set(Calendar.DATE, 1);
        }
        else if (event.getSource() == startMonth &&
            event.getStateChange() == ItemEvent.SELECTED) {

            startDate.set(Calendar.MONTH, startMonth.getSelectedIndex());
            buildDaysList(startDate, startDay, startMonth);
            startDate.set(Calendar.DATE, 1);
        }
        else if (event.getSource() == startDay &&
            event.getStateChange() == ItemEvent.SELECTED) {

            int day = Integer.parseInt((String)startDay.getSelectedItem());
            startDate.set(Calendar.DATE, day);
        }
        else if (event.getSource() == endYear &&
            event.getStateChange() == ItemEvent.SELECTED) {

            int year = Integer.parseInt((String)endYear.getSelectedItem());
            endDate.set(Calendar.YEAR, year);
            endMonth.setSelectedIndex(0);
            endDate.set(Calendar.MONTH, 0);
            buildDaysList(endDate, endDay, endMonth);
            endDate.set(Calendar.DATE, 1);
        }
        else if (event.getSource() == endMonth &&
            event.getStateChange() == ItemEvent.SELECTED) {

            endDate.set(Calendar.MONTH, endMonth.getSelectedIndex());
            buildDaysList(endDate, endDay, endMonth);
            endDate.set(Calendar.DATE, 1);
        }
        else if (event.getSource() == endDay &&
            event.getStateChange() == ItemEvent.SELECTED) {

            int day = Integer.parseInt((String)endDay.getSelectedItem());
            endDate.set(Calendar.DATE, day);
        }
    }

   

    
}
