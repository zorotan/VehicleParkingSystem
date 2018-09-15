/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Jack
 */
public class FilterTransactions extends JPanel implements ItemListener{
    private String location = "1";
    private String amount = "ASC";
    private JComboBox startYear, startMonth, startDay;
    private JComboBox endYear, endMonth, endDay;
    /* This is the start date used for the date dropdowns */
    private Calendar startDate = Calendar.getInstance();
    private String formattedStartDate;
    /* This is the end date used for the date dropdowns */
    private Calendar endDate = Calendar.getInstance();
    private String formattedEndDate;
    private String startTime = "00:00";
    private String endTime = "24:00";

    
    public FilterTransactions (JFrame f) {
        setLayout(new GridLayout(2,2));
        JPanel locationPanel = new JPanel(new FlowLayout());
        JPanel amountPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JPanel filterPanel = new JPanel();
        JPanel startTimePanel = new JPanel();
        JPanel endTimePanel = new JPanel();
        JPanel timePanel = new JPanel();
        timePanel.setBorder(BorderFactory.createTitledBorder
            ("Time Range"));
        filterPanel.setLayout(new BoxLayout(filterPanel,BoxLayout.Y_AXIS));
        
        JLabel location_label = new JLabel("Location: ");
        JLabel amount_label = new JLabel("Amount Order: ");
        JLabel start_time_label = new JLabel("Start Time: ");
        JLabel end_time_label = new JLabel("End Time: ");
        JButton filter = new JButton("Filter");
        filter.addActionListener((ActionEvent e) -> {
            ViewTransaction vt = new ViewTransaction();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            formattedStartDate = format1.format(startDate.getTime());
            formattedEndDate = format1.format(endDate.getTime());
            f.getContentPane().removeAll();
            f.add(vt.showFilteredTransactions(f,getData()));
            f.revalidate();
            f.repaint();
        });
        JButton showAll = new JButton("Show All");
        showAll.addActionListener((ActionEvent e) -> {
            ViewTransaction vt = new ViewTransaction();
            f.getContentPane().removeAll();
            f.add(vt.showTransactions(f));
            f.revalidate();
            f.repaint();
        });
        
        String [] allLocation = {"Bukit Beruang","Batu Berendam","Melaka Raya","Kota Laksamana","Bukit Baru"};
        JComboBox locationList = new JComboBox(allLocation);
        locationList.setEditable(false);
        locationList.setSelectedIndex(0);
        locationList.addActionListener((ActionEvent e) -> {
            location = (String)locationList.getSelectedItem();
        });
        
        String [] amountOrderList = {"Ascending","Descending"};
        JComboBox amountList = new JComboBox(amountOrderList);
        amountList.setEditable(false);
        amountList.setSelectedIndex(0);
        amountList.addActionListener((ActionEvent e) -> {
            String order = (String)amountList.getSelectedItem();
            if(order == "Ascending")
                amount = "ASC";
            else
                amount = "DESC";
        });
        
        String [] timeList = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00",
            "07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00",
            "15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00","24:00"};
        JComboBox startTimePicker = new JComboBox(timeList);
        startTimePicker.setEditable(false);
        startTimePicker.setSelectedIndex(0);
        startTimePicker.addActionListener((ActionEvent e) -> {
            startTime  = (String)startTimePicker.getSelectedItem();
        });
        
        JComboBox endTimePicker = new JComboBox(timeList);
        endTimePicker.setEditable(false);
        endTimePicker.setSelectedIndex(24);
        endTimePicker.addActionListener((ActionEvent e) -> {
            endTime  = (String)endTimePicker.getSelectedItem();
        });
        
        startTimePanel.add(start_time_label);
        startTimePanel.add(startTimePicker);
        endTimePanel.add(end_time_label);
        endTimePanel.add(endTimePicker);
        
        timePanel.add(startTimePanel);
        timePanel.add(endTimePanel);
        
        JPanel datesPanel = new JPanel();
        datesPanel.setBorder(BorderFactory.createTitledBorder
            ("Specific Date"));
        JPanel datesInnerPanel = new JPanel(new GridLayout(2, 4, 5, 5));
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
        
        amountPanel.add(amount_label);
        amountPanel.add(amountList);
        
        buttonPanel.add(filter);
        buttonPanel.add(showAll);
        
        JLabel invi = new JLabel(" ");
        filterPanel.add(invi);
        filterPanel.add(locationPanel);
        filterPanel.add(amountPanel);
        
        add(filterPanel);
        add(timePanel);
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
    @Override
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

   public String[] getData() {
       String[] data = {location,amount,formattedStartDate,formattedEndDate,startTime,endTime};
       return data;
   }
   
}
