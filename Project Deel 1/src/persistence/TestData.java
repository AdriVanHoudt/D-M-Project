package persistence;

import model.Festival;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestData {
    private Set<Festival> festivals = new HashSet<>();


    public static void main(String[] args) {
        TestData td = new TestData();
        td.generateFestivals();
    }


    public void generateFestivals() {
        String[] festivalNames = {"Rock Werchter", "Pukkelpop", "Tomorrowland", "Reggea Geel", "Dour"};

        for (int i = 0; i < 5; i++) {


            Festival festival = new Festival();
            festival.setName(festivalNames[i]);
            festival.setStartDate(new Date());
            festival.setEndDate(new Date());

            festivals.add(festival);
        }
    }
}
