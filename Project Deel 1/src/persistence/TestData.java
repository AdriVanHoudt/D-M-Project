package persistence;

import model.Festival;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 25/10/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class TestData {
    public void generateFestivals(){
        String[] festivalNames = {"Rock Werchter","Pukkelpop","Tomorrowland","Reggea Geel","Laundry Day"};

        for(int i=0;i<5;i++){

            Festival festival = new Festival();
            festival.setName(festivalNames[i]);
            festival.setStartDate();
            festival.setEndDate();
        }
    }
}