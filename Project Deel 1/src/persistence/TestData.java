package persistence;

import model.Festival;
import model.FestivalDag;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestData {
    private Set<Festival> festivals = new HashSet<>();


    public static void main(String[] args) {
        TestData td = new TestData();
        td.generateFestivals();
    }


    public void generateFestivals() {
        String[] festivalNames = {"Rock Werchter", "Pukkelpop", "Tomorrowland", "Reggea Geel", "Dour"};
        String[] festivalLocations = {"Werchter", "Kiewit", "Boom", "Geel", "Henegouwen"} ;
        int month, year, day, endMonth, endYear, endDay;
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {

            month = rand.nextInt(11);
            year = rand.nextInt(5) + 2013;
            day = rand.nextInt(31);

            endMonth = month;
            endYear = year;
            endDay = day + rand.nextInt(4) + 1;

            GregorianCalendar calendarStart = new GregorianCalendar(year, month, day);
            GregorianCalendar calendarEnd = new GregorianCalendar(endYear, endMonth, endDay);

            Date startDate = calendarStart.getTime();
            Date endDate = calendarEnd.getTime();

            Festival festival = new Festival();
            festival.setName(festivalNames[i]);
            festival.setLocation(festivalLocations[i]);

            String formattedDateStart = new SimpleDateFormat("MM/dd/yyyy 12:mm:ss").format(startDate);
            String formattedDateEnd = new SimpleDateFormat("MM/dd/yyyy 01:mm:ss").format(endDate);


            festival.setStartDate(new Date(formattedDateStart));
            festival.setEndDate(new Date(formattedDateEnd));

            //test
            System.out.println(formattedDateStart);
            System.out.println(formattedDateEnd);

            festivals.add(festival);

            generateFestivalDag(festival);
        }
    }

    public void generateFestivalDag(Festival festival){
        Date newDate = new Date();
        DateTime start = new DateTime(festival.getStartDate());
        DateTime end = new DateTime(festival.getEndDate());
        int days = Days.daysBetween(start, end).getDays() + 1;

        for(int i = 0; i < days; i++) {
            FestivalDag festivalDag = new FestivalDag();

            festivalDag.setFestival(festival);

            Calendar c = Calendar.getInstance();
            c.setTime(festival.getStartDate());
            c.add(Calendar.DATE, i);
            newDate = c.getTime();

            String formattedDate = new SimpleDateFormat("MM/dd/yyyy 12:mm:ss").format(newDate);

            festivalDag.setDate(new Date(formattedDate));

            //test
            System.out.println(formattedDate);
        }

    }
}
