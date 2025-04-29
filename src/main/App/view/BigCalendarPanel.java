package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import view.CommonConstants;

public class BigCalendarPanel extends JPanel {
    private YearMonth currentYearMonth;
    private JPanel daysPanel;
    private Map<LocalDate, DayCellPanel> dayCellMap = new HashMap<>();

    public BigCalendarPanel() {
        setLayout(new BorderLayout());
        setBackground(CommonConstants.PRIMARY_COLOR);

        currentYearMonth = YearMonth.now();

        // Header days (Sun, Mon, ...)
        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(CommonConstants.TERTIARY_COLOR);

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setForeground(CommonConstants.PRIMARY_COLOR);
            label.setFont(new Font("Tahoma", Font.BOLD, 14));
            headerPanel.add(label);
        }
        add(headerPanel, BorderLayout.NORTH);

        // Days panel
        daysPanel = new JPanel(new GridLayout(6, 7)); // 6 weeks
        daysPanel.setBackground(CommonConstants.PRIMARY_COLOR);
        add(daysPanel, BorderLayout.CENTER);

        updateCalendar();
    }

    public void updateCalendar() {
        daysPanel.removeAll();
        dayCellMap.clear();

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 (Mon) to 7 (Sun)

        int offset = startDayOfWeek % 7; // Sunday = 0
        LocalDate startDate = firstOfMonth.minusDays(offset);

        for (int i = 0; i < 42; i++) { // 6x7 = 42 cells
            LocalDate date = startDate.plusDays(i);
            DayCellPanel dayCell = new DayCellPanel(date, currentYearMonth);
            daysPanel.add(dayCell);
            dayCellMap.put(date, dayCell);
        }

        revalidate();
        repaint();
    }

    // Move to previous month
    public void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    // Move to next month
    public void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }
}