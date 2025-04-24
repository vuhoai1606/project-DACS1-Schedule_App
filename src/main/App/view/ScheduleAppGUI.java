package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

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

        // Panel Lịch Trình
        JPanel schedulePanel = new JPanel();
        schedulePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        schedulePanel.setBounds(10, 360, 220, 70);
        schedulePanel.setLayout(null);
        // chữ
        JLabel scheduleLabel = new JLabel("Lịch trình");
        scheduleLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        scheduleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        scheduleLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon schedule = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication" +
                "\\src\\main\\App\\view\\calendar.png");
        JLabel scheduleIcon = new JLabel(schedule);
        scheduleIcon.setBounds(5, 5, 80, 60);

        schedulePanel.add(scheduleLabel);
        schedulePanel.add(scheduleIcon);
        menuPanel.add(schedulePanel);

        // Panel Tin Nhắn
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(CommonConstants.TERTIARY_COLOR);
        messagePanel.setBounds(10, 460, 220, 70);
        messagePanel.setLayout(null);
        // chữ
        JLabel messageLabel = new JLabel("Tin nhắn");
        messageLabel.setForeground(CommonConstants.PRIMARY_COLOR);
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        messageLabel.setBounds(100, 10, 100, 50);
        // icon
        ImageIcon message = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication" +
                "\\src\\main\\App\\view\\speech_bubble.png");
        JLabel messageIcon = new JLabel(message);
        messageIcon.setBounds(5, 5, 80, 60);

        messagePanel.add(messageLabel);
        messagePanel.add(messageIcon);
        menuPanel.add(messagePanel);

        // Panel Side Bar
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        sideBarPanel.setBounds(10, 20, 80, 75);
        sideBarPanel.setLayout(null);
        // icon
        ImageIcon sideBar = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication" +
                "\\src\\main\\App\\view\\side_bar.png");
        JLabel sideBarIcon = new JLabel(sideBar);
        sideBarIcon.setBounds(0, 5, 80, 65);

        sideBarPanel.add(sideBarIcon);
        menuPanel.add(sideBarPanel);

        // Panel Profile User
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(CommonConstants.SECONDARY_COLOR);
        profilePanel.setBounds(10, 830, 80, 75);
        profilePanel.setLayout(null);
        // icon
        ImageIcon profile = new ImageIcon("C:\\Users\\This PC\\IdeaProjects\\scheduleApplication" +
                "\\src\\main\\App\\view\\profile_user.png");
        JLabel profileIcon = new JLabel(profile);
        profileIcon.setBounds(0, 5, 80, 65);

        profilePanel.add(profileIcon);
        menuPanel.add(profilePanel);
        add(menuPanel, BorderLayout.WEST);
        // ==================================================================

        // ------------------------- Phần CONTENT LỊCH TRÌNH -------------------------
        JPanel scheduleContentPanel = new JPanel();
        scheduleContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        scheduleContentPanel.setPreferredSize(new Dimension(890, 960));
        scheduleContentPanel.setLayout(null);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(20, 20, 200, 30);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Month Panel
        JPanel monthPabel = new JPanel();
        monthPabel.setLayout(null);
        monthPabel.setBounds(230, 20, 80, 30);
        monthPabel.setBackground(CommonConstants.TEXT_COLOR);

        JLabel monthLabel = new JLabel("Month");
        monthLabel.setBounds(10, 5, 80, 20);
        monthLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        monthLabel.setForeground(Color.white);
        monthPabel.add(monthLabel);

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
        scheduleContentPanel.add(monthPabel);
        scheduleContentPanel.add(todayPanel);

        add(scheduleContentPanel, BorderLayout.CENTER);
        // ==================================================================

//        // ------------------------- Phần CONTENT TIN NHẮN -------------------------
//        JPanel MessageContentPanel = new JPanel();
//        MessageContentPanel.setBackground(CommonConstants.PRIMARY_COLOR);
//        MessageContentPanel.setPreferredSize(new Dimension(890, 960));
//        add(MessageContentPanel, BorderLayout.CENTER);
//        // ==================================================================

        // ------------------------- Thanh CRUD -------------------------
        JPanel crudPanel = new JPanel();
        crudPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        crudPanel.setPreferredSize(new Dimension(310, 960));
        add(crudPanel, BorderLayout.EAST);
        // ==================================================================
    }
}
