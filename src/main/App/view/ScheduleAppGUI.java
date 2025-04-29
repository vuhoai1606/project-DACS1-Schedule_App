package view;

import javax.swing.*;
import java.awt.*;
import java.util.TimeZone;
//import view.MiniCalendarPanel;

public class ScheduleAppGUI extends JFrame {
    public ScheduleAppGUI() {
        setTitle("Tên App");
        setSize(1440, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ------------------------- Thanh MENU -------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        menuPanel.setPreferredSize(new Dimension(240, 960));
        menuPanel.setLayout(null);

        // Panel Side Bar
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sideBarPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        sideBarPanel.setBounds(10, 20, 80, 75);
        sideBarPanel.setLayout(null);
        // icon
        ImageIcon sideBar = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\side_bar.png");
        JLabel sideBarIcon = new JLabel(sideBar);
        sideBarIcon.setBounds(0, 5, 80, 65);

        sideBarPanel.add(sideBarIcon);
        menuPanel.add(sideBarPanel);

        // Thêm Mini Calendar vào menuPanel
        MiniCalendarPanel miniCalendarPanel = new MiniCalendarPanel();
        miniCalendarPanel.setBounds(0, 115, 240, 200);
        menuPanel.add(miniCalendarPanel);

        // Panel Lịch Trình
        JPanel schedulePanel = new JPanel();
        schedulePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        schedulePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        schedulePanel.setBounds(10, 360, 220, 70);
        schedulePanel.setLayout(null);
        // chữ
        JLabel scheduleLabel = new JLabel("Lịch trình");
        scheduleLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        scheduleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        scheduleLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon schedule = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\calendar.png");
        JLabel scheduleIcon = new JLabel(schedule);
        scheduleIcon.setBounds(5, 5, 80, 60);

        schedulePanel.add(scheduleLabel);
        schedulePanel.add(scheduleIcon);
        menuPanel.add(schedulePanel);

        // Panel Tin Nhắn
        JPanel messagePanel = new JPanel();
        messagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messagePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        messagePanel.setBounds(10, 460, 220, 70);
        messagePanel.setLayout(null);
        // chữ
        JLabel messageLabel = new JLabel("Tin nhắn");
        messageLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        messageLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon message = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\speech_bubble.png");
        JLabel messageIcon = new JLabel(message);
        messageIcon.setBounds(5, 5, 80, 60);

        messagePanel.add(messageLabel);
        messagePanel.add(messageIcon);
        menuPanel.add(messagePanel);

        // Panel Profile User
        JPanel profilePanel = new JPanel();
        profilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePanel.setBackground(CommonConstants.SECONDARY_COLOR);
        profilePanel.setBounds(10, 830, 80, 75);
        profilePanel.setLayout(null);
        // icon
        ImageIcon profile = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication\\src\\main\\img\\profile_user.png");
        JLabel profileIcon = new JLabel(profile);
        profileIcon.setBounds(0, 5, 80, 65);

        profilePanel.add(profileIcon);
        menuPanel.add(profilePanel);
        add(menuPanel, BorderLayout.WEST);
        // ==================================================================

        // ------------------------- Phần Nội Dung LỊCH TRÌNH -------------------------
        JPanel scheduleContentPanel = new JPanel();
        scheduleContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        scheduleContentPanel.setPreferredSize(new Dimension(890, 960));
        scheduleContentPanel.setLayout(null);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(20, 20, 200, 30);
        searchPanel.setBackground(Color.WHITE);
//        searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

//        // Icon tìm kiếm
//        ImageIcon searchIcon = new ImageIcon("src/main/img/speech_bubble.png");
//        JLabel searchIconLabel = new JLabel(searchIcon);
//        searchIconLabel.setBounds(5, 5, 30, 30);
//        searchPanel.add(searchIconLabel);

        // TextField tìm kiếm
        JTextField searchField = new JTextField("Search...");
        searchField.setForeground(Color.GRAY);
        searchField.setBounds(40, 5, 350, 30);
        searchField.setBorder(null);

        // Placeholder xử lý
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search...");
                }
            }
        });
        searchPanel.add(searchField);

        // Month Panel
        JPanel monthPanel = new JPanel();
        monthPanel.setBounds(230, 20, 80, 30);
        monthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        monthPanel.setBackground(CommonConstants.TEXT_COLOR);
//        monthPanel.setOpaque(false);

        // Label Month
        JLabel monthLabel = new JLabel("Month");
        monthLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        monthLabel.setForeground(Color.white);
        monthPanel.add(monthLabel);

        // Icon mũi tên xuống
        ImageIcon arrowDownIcon = new ImageIcon("src/main/img/down-arrow 24px.png");
        JLabel arrowDownLabel = new JLabel(arrowDownIcon);
        monthPanel.add(arrowDownLabel);

        // Today Panel
        JPanel todayPanel = new JPanel();
        todayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        todayPanel.setBounds(340, 20, 80, 30);
        todayPanel.setBackground(CommonConstants.TEXT_COLOR);

        JLabel todayLabel = new JLabel("Today");
        todayLabel.setForeground(Color.white);
        todayLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        todayPanel.add(todayLabel);

        scheduleContentPanel.add(searchPanel);
        scheduleContentPanel.add(monthPanel);
        scheduleContentPanel.add(todayPanel);

        BigCalendarPanel calendarPanel = new BigCalendarPanel();
        calendarPanel.setBounds(2, 70, 870, 850);
        scheduleContentPanel.add(calendarPanel);

        add(scheduleContentPanel, BorderLayout.CENTER);
        // ==================================================================

//        // ------------------------- Phần Nội Dung TIN NHẮN -------------------------
//        JPanel MessageContentPanel = new JPanel();
//        MessageContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
//        MessageContentPanel.setPreferredSize(new Dimension(890, 960));
//        add(MessageContentPanel, BorderLayout.CENTER);
//        // ==================================================================

        // ------------------------- Thanh CRUD -------------------------
        JPanel eventDetailPanel = new JPanel();
        eventDetailPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        eventDetailPanel.setPreferredSize(new Dimension(310, 960));
        eventDetailPanel.setLayout(null);

        // ==== Title ====
        JTextField titleField = new JTextField("Event");
        titleField.setBounds(20, 20, 270, 30); // Điều chỉnh chiều rộng của titleField
        titleField.setFont(new Font("Segoe UI", Font.BOLD, 18));
        eventDetailPanel.add(titleField);

        // ==== Time (Start and End Time, Editable) ====
        // Time Start
        JLabel startTimeLabel = new JLabel("Start Time:");
        startTimeLabel.setForeground(Color.WHITE);
        startTimeLabel.setBounds(20, 70, 270, 20);
        eventDetailPanel.add(startTimeLabel);

        JTextField startTimeField = new JTextField("12:00 AM");
        startTimeField.setBounds(20, 100, 130, 20); // Điều chỉnh chiều rộng của startTimeField
        startTimeField.setForeground(Color.WHITE);
        startTimeField.setBackground(new Color(30, 30, 30));
        eventDetailPanel.add(startTimeField);

        // Time End
        JLabel endTimeLabel = new JLabel("End Time:");
        endTimeLabel.setForeground(Color.WHITE);
        endTimeLabel.setBounds(160, 70, 270, 20);
        eventDetailPanel.add(endTimeLabel);

        JTextField endTimeField = new JTextField("12:30 AM");
        endTimeField.setBounds(160, 100, 130, 20); // Điều chỉnh chiều rộng của endTimeField
        endTimeField.setForeground(Color.WHITE);
        endTimeField.setBackground(new Color(30, 30, 30));
        eventDetailPanel.add(endTimeField);

        // ==== Date (Editable) ====
        JTextField dateField = new JTextField("Wed Apr 16");
        dateField.setBounds(20, 130, 270, 20); // Điều chỉnh chiều rộng của dateField
        dateField.setForeground(Color.WHITE);
        dateField.setBackground(new Color(30, 30, 30));
        eventDetailPanel.add(dateField);

        // ==== All-day ====
        JCheckBox allDayCheck = new JCheckBox("All-day");
        allDayCheck.setForeground(Color.WHITE);
        allDayCheck.setBackground(new Color(30, 30, 30));
        allDayCheck.setBounds(20, 160, 100, 20);
        eventDetailPanel.add(allDayCheck);

        // ==== Timezone (Selection) ====
        JLabel timezoneLabel = new JLabel("Timezone:");
        timezoneLabel.setForeground(Color.WHITE);
        timezoneLabel.setBounds(20, 190, 270, 20);
        eventDetailPanel.add(timezoneLabel);

        String[] timezones = TimeZone.getAvailableIDs();
        JComboBox<String> timezoneComboBox = new JComboBox<>(timezones);
        timezoneComboBox.setBounds(20, 220, 270, 25);
        eventDetailPanel.add(timezoneComboBox);

        // ==== Repeat ====
        JButton repeatBtn = new JButton("Repeat");
        repeatBtn.setBounds(20, 250, 100, 25);
        eventDetailPanel.add(repeatBtn);

        // ==== Location ====
        JTextField locationField = new JTextField("Location");
        locationField.setBounds(20, 290, 270, 25); // Điều chỉnh chiều rộng của locationField
        eventDetailPanel.add(locationField);

        // ==== Docs & Links ====
        JTextField linkField = new JTextField("Docs and links");
        linkField.setBounds(20, 330, 270, 25); // Điều chỉnh chiều rộng của linkField
        eventDetailPanel.add(linkField);

        // ==== Description ====
        JTextArea descArea = new JTextArea("Description");
        descArea.setBounds(20, 370, 270, 80); // Điều chỉnh chiều rộng của descArea
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        eventDetailPanel.add(descArea);

        // ==== Reminder (ComboBox for selection) ====
        JLabel reminderLabel = new JLabel("Reminders:");
        reminderLabel.setForeground(Color.WHITE);
        reminderLabel.setBounds(20, 460, 270, 20); // Điều chỉnh vị trí của reminderLabel
        eventDetailPanel.add(reminderLabel);

        String[] reminderOptions = {"None", "5 min before", "10 min before", "30 min before", "1 hour before"};
        JComboBox<String> reminderComboBox = new JComboBox<>(reminderOptions);
        reminderComboBox.setBounds(20, 490, 270, 25);
        eventDetailPanel.add(reminderComboBox);

        add(eventDetailPanel, BorderLayout.EAST);
        // ==================================================================
    }
}
