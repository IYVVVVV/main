package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.calculator.CalorieCalculatorByDateRange;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import jfxtras.icalendarfx.components.VEvent;

/**
 * Display two calendars.
 */
public class CalendarPanel extends UiPart<AnchorPane> {
    private static final String FXML = "CalendarPanel.fxml";
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportList;
    private final CalendarPage calendarPage;
    private DaysCard daysPage;
    private MonthView monthView;
    private UpcomingList upcomingList;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private CalorieCalculatorByDateRange stats;

    @FXML
    private StackPane calendarPagePlaceholder;

    @FXML
    private AnchorPane monthViewPlaceholder;

    @FXML
    private StackPane upcomingListPlaceholder;

    @FXML
    private AnchorPane daysPagePlaceholder;

    /**
     * Creates a calendar page displaying two components from {@code }.
     */
    public CalendarPanel(ObservableList<Entry> foodList, ObservableList<Entry> sportList,
                         ObservableList<VEvent> events) {
        super(FXML);
        this.foodList = foodList;
        this.sportList = sportList;
        logger.info("Initializing Calendar Page");
        calendarPage = new CalendarPage(events);
        daysPage = new DaysCard(foodList, sportList, LocalDateTime.now());
        set(LocalDateTime.now(), "tb");
    }

    public void updateScheduler() {
        calendarPage.updateScheduler();
    }

    // set date reference based on parameter date
    public void set(LocalDateTime date, String mode) {
        getGenerator(date);
        monthView = new MonthView(date, stats);
        upcomingList = new UpcomingList(foodList, sportList, date);
        upcomingListPlaceholder.getChildren().clear();
        upcomingListPlaceholder.getChildren().add(upcomingList.getRoot());
        monthViewPlaceholder.getChildren().clear();
        monthViewPlaceholder.getChildren().add(monthView.getView());
        if ("ls".equals(mode)) {
            calendarPagePlaceholder.getChildren().clear();
            daysPagePlaceholder.getChildren().clear();
            daysPage = new DaysCard(foodList, sportList, date);
            daysPagePlaceholder.getChildren().add(daysPage.getRoot());
        } else {
            daysPagePlaceholder.getChildren().clear();
            calendarPage.setDate(date);
            calendarPage.updateScheduler();
            calendarPagePlaceholder.getChildren().clear();
            calendarPagePlaceholder.getChildren().add(calendarPage.getRoot());
        }
    }

    public void getGenerator(LocalDateTime date) {
        LocalDate givenDate = date.toLocalDate();
        LocalDate start = givenDate.withDayOfMonth(1);
        LocalDate end = givenDate.withDayOfMonth(givenDate.lengthOfMonth());
        stats = new CalorieCalculatorByDateRange(foodList, sportList, start, end);
    }
}
