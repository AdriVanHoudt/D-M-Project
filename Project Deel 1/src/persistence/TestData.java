package persistence;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestData {
    private List<Festival> festivals = new ArrayList<>();
    private List<Zone> zones = new ArrayList<>();
    private List<Artiest> artists = new ArrayList<>();
    private List<FestivalDag> festivalDays = new ArrayList<>();
    private List<Optreden> optredens = new ArrayList<>();
    private List<Nummer> nummers = new ArrayList<>();
    private List<Apparatuur> apparaten = new ArrayList<>();
    private List<OptredenApparatuur> optredenApparatuurs = new ArrayList<>();
    private List<Faciliteit> faciliteiten = new ArrayList<>();
    private List<KleedkamerRegistratie> kleedkamerRegistraties = new ArrayList<>();
    private List<PersOrgaan> persOrganen = new ArrayList<>();
    private List<Perstoelating> persToelatingen = new ArrayList<>();
    private List<Tracking> trackings = new ArrayList<>();
    private List<FestivalGanger> festivalGangers = new ArrayList<>();
    private List<TicketVerkoop> ticketVerkopen = new ArrayList<>();
    private List<TicketType> ticketTypes = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<TicketZone> ticketZones = new ArrayList<>();
    private List<TicketDag> ticketDagen = new ArrayList<>();

    private static Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private static Transaction tx = session.beginTransaction();

    private int aantalTickets = 0;
    private int aantalOnlineTickets = 0;

    public static void main(String[] args) {
        TestData td = new TestData();
        td.generateFestivals();
        tx.commit();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        td.generateArtists();
        tx.commit();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        td.generateOptredens();
        tx.commit();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        td.generateFestivalgangers();
        tx.commit();
    }


    public void generateFestivals() {
        String[] festivalNames = {"Rock Werchter", "Pukkelpop", "Tomorrowland", "Reggea Geel", "Dour", "Gentse Feesten",
                "I Love Techno", "Laundry Day", "TW Classic", "Summerfestival"};
        String[] festivalLocations = {"Werchter", "Kiewit", "Boom", "Geel", "Henegouwen", "Gent", "Gent", "Antwerpen", "Werchter", "Antwerpen"};
        int month, year, day, endMonth, endYear, endDay;
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {

            month = rand.nextInt(11);
            year = rand.nextInt(5) + 2011;
            day = rand.nextInt(31);

            endMonth = month;
            endYear = year;
            //willekeurige einddatum -> willekeurig aantal festivaldagen
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
            /*   System.out.println("Festival start: " + formattedDateStart);
       System.out.println("Festival eind: " + formattedDateEnd); */

            festivals.add(festival);

            generateFestivalDag(festival);
            generateZone(festival);
        }
        for (Festival f : festivals) {
            session.saveOrUpdate(f);
        }
    }

    public void generateFestivalDag(Festival festival) {
        Date newDate = new Date();
        DateTime start = new DateTime(festival.getStartDate());
        DateTime end = new DateTime(festival.getEndDate());
        //berekent het aantal dagen per festival er zijn
        int days = Days.daysBetween(start, end).getDays() + 1;

        for (int i = 0; i < days; i++) {
            FestivalDag festivalDag = new FestivalDag();

            festivalDag.setFestival(festival);

            Calendar c = Calendar.getInstance();
            c.setTime(festival.getStartDate());
            c.add(Calendar.DATE, i);
            newDate = c.getTime();

            String formattedDate = new SimpleDateFormat("MM/dd/yyyy 12:mm:ss").format(newDate);

            festivalDag.setDate(new Date(formattedDate));

            festivalDag.setAantalBeschikbareTickets(7500);

            festivalDays.add(festivalDag);

            //test
            //System.out.println("Festivaldag " + i + " dag: " + formattedDate);
        }
        for (FestivalDag fd : festivalDays) {
            session.saveOrUpdate(fd);
        }
    }

    public void generateZone(Festival festival) {
        String[] zoneNames = {"Inkom", "VIP ruimte", "VIP ruimte Mainstage", "Backstage ruimte", "Sanitair Mainstage", "Sanitair Red Bull",
                "Camping", "Mainstage", "Festivalterrein Mainstage", "Red Bull Stage", "Festivalterrein Red Bull Stage", "The Barn",
                "Festivalterrein The Barn", "Klub C", "Festivalterrein Klub C", "Red Light Disctrict", "Festivalterrein Red Light District"};

        Random rand = new Random();

        for (int i = 0; i < zoneNames.length; i++) {
            Zone zone = new Zone();
            zone.setNaam(zoneNames[i]);
            zone.setFestival(festival);
            //hier wordt de foreign key naar zichzelf gevuld.
            if (zoneNames[i].contains("Festivalterrein")) {
                zone.setCapaciteit(3000);
                zone.setZone(zones.get(zones.size() - 1));
                //test
                // System.out.println(zone.getZone().getNaam());
            } else {
                zone.setCapaciteit(100);
            }

            //test
            /* System.out.println(zone.getFestival().getName());
           System.out.println(zone.getNaam()); */


            zones.add(zone);
            generateApparatuur(zone);
            generateFaciliteiten(zone);
            //generateTrackings(zone);
        }
        for (Zone z : zones) {
            session.saveOrUpdate(z);
        }
    }

    public void generateArtists() {
        //alle artiesten met hun bio's
        String[] artistNames = {"Eminem", "Chase & Status", "The Prodigy", "Franz Ferdinand", "Goose", "Triggerfinger", "Green Day", "Netsky",
                "Blur", "Kings Of Leon", "Rammstein", "Depeche Mode", "Tiësto", "Dimitri Vegas & Like Mike", "Armin Van Buuren", "Avicii", "David Guetta",
                "Booka Shade", "Disclosure", "Chris Liebing"};
        String[] bio = {"Eminem, artiestennaam van Marshall Bruce Mathers III, ook wel Slim Shady genoemd, (St. Joseph (Missouri), 17 oktober 1972) is een Amerikaans rapper, producer en occasioneel acteur.",
                "Chase & Status is een producersduo uit Londen dat elektronische muziek maakt, voornamelijk drum 'n' bass en dubstep. De groep bestaat uit Saul Chase Milton en Will Status Kennard en werd opgericht in 2003.",
                "The Prodigy is een Britse dance-act. The Prodigy is een van de succesvolste acts die de dansmuziek van de jaren 90 van de twintigste eeuw hebben voortgebracht.",
                "Franz Ferdinand is een Britse rockband, opgericht in 2001. De band werd in Glasgow gevormd door zanger/gitarist Alex Kapranos, gitarist Nick McCarthy, bassist Bob Hardy en drummer Paul Thomson.",
                "Goose is een Belgische elektrorockband bestaande uit Mickael Karkousse, Dave Martijn, Tom Coghe en Bert Libeert.",
                "Triggerfinger is een Belgische hardrockband afkomstig uit Antwerpen en Lier.",
                "Green Day is een Amerikaanse punkrockband uit Oakland, Californië.",
                "Netsky, de artiestennaam van Boris Daenen (Edegem, 5 april 1989), is een Belgisch dj en producent van dubstep en drum and bass.",
                "Blur is een Engelse rockband. De groep is een van de bekendste britpopbands en zorgde samen met zijn grote rivaal Oasis halverwege de jaren negentig voor het succes van dit genre.",
                "Kings of Leon is een Amerikaanse rockband uit Nashville.",
                "Rammstein is een Duitse metalband die naar eigen zeggen Tanzmetall produceert, ofwel een synthese van progressive metal, industrial en techno, met enige gothic-invloeden.",
                "Depeche Mode is een Britse band die elektronische muziek maakt.",
                "Tiësto, ook wel aangeduid als DJ Tiësto, artiestennaam van Tijs Michiel Verwest (Breda, 17 januari 1969) is een Nederlandse top-dj die vaak optreedt op grote dance-evenementen",
                "Dimitri Vegas & Like Mike is een Belgisch dj-duo, bestaande uit de twee broers Dimitri Thivaios (Dimitri Vegas) en Michael Thivaios (Like Mike).",
                "Armin van Buuren (Leiden, 25 december 1976) is een Nederlandse dj en producer.",
                "Avicii is de artiestennaam van Tim Bergling (Stockholm, 8 september 1989), een Zweedse dj, remixer en muziekproducent.",
                "David Guetta (Parijs, 7 november 1967) is een Franse dj en dance-pop-producent (vroeger housemuziek).",
                "Booka Shade is een Duits microhouse-duo.",
                "Garage/elektronische muziek duo uit het Verenigd Koninkrijk",
                "Christoph (Chris) Liebing (Gießen, 11 december 1968) is een Duitse dj en producent van techno uit Frankfurt."};

        generatePersorgaan();

        for (int i = 0; i < artistNames.length; i++) {
            Artiest artiest = new Artiest();
            artiest.setNaam(artistNames[i]);
            artiest.setBio(bio[i]);

            artists.add(artiest);

            generatePersToelating(artiest);
        }


        for (Artiest a : artists) {
            session.saveOrUpdate(a);
        }

    }

    public void generateOptredens() {
        Random rand = new Random();
        List<Zone> podia = new ArrayList<>();

        //hier worden de podiums uit de zones gehaald. omdat er bepaalde zones zijn waar geen optreden gedaan wordt
        for (Zone z : zones) {
            if (z.getNaam().contains("Festivalterrein")) {
                podia.add(z);
            }
        }
        //lus voor alle festivaldagen
        for (FestivalDag fd : festivalDays) {
            //lus voor alle podia per festivaldag
            for (int i = 0; i < podia.size(); i++) {
                Date optredenTime = fd.getDate();
                //6 optredens per podia per festivaldag
                for (int j = 0; j < 6; j++) {
                    Optreden optreden = new Optreden();
                    optreden.setFestivalDag(fd);
                    //willekeurige artiest voor een optreden
                    int index = rand.nextInt(artists.size());
                    Artiest randomArtist = artists.get(index);
                    //set de zone voor een optreden
                    optreden.setZone(podia.get(i).getZone());
                    //de startdatum van een optreden
                    optreden.setStartTime(optredenTime);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(optredenTime);
                    //de startdatum wordt met 2 uren opgeteld
                    cal.add(Calendar.HOUR_OF_DAY, 2);
                    optredenTime = cal.getTime();
                    optreden.setEndTime(optredenTime);
                    optreden.setArtiest(randomArtist);
                    //soundcheck van 10 minuten
                    optreden.setSoundcheck(10);
                    optredens.add(optreden);

                    generateNummers(optreden);
                    generateOptredenApparatuur(optreden);
                    generateKleedkamerRegistratie(optreden);
                }
            }
        }

        for (Optreden o : optredens) {
            session.saveOrUpdate(o);
        }

    }

    public void generateNummers(Optreden optreden) {

        String[] nummerArtiest = {"Eminem", "Chase & Status", "The Prodigy", "Franz Ferdinand", "Goose", "Triggerfinger", "Green Day", "Netsky",
                "Blur", "Kings Of Leon", "Rammstein", "Depeche Mode", "Tiësto", "Dimitri Vegas & Like Mike", "Armin Van Buuren", "Avicii", "David Guetta",
                "Booka Shade", "Disclosure", "Chris Liebing"};
        String[] nummerTitel = {"Sing for the Moment", "Rock Bottom", "We as Americans", "Till I Collapse", "Role Model", "Yellow Brick Road", "Guilty Conscience", "The Way I Am", "Lose Yourself", "Stan",
                "End Credits", "Let You Go", "Pieces", "Blind Faith", "Heavy", "Heartbeat", "Hypest Hype", "Time", "Flashing Lights",
                "Omen", "Breathe", "Firestarter", "Invaders Must Die", "Smack My B**** Up", "Voodoo People", "Take Me To The Hospital", "Warrior's Dance", "Spitfire", "Stand Up",
                "Take Me Out", "The Dark Of Matinee", "No You Girls", "Ulysses", "Dou You Want To", "The Fallen", "Walk Away", "What She Came For", "Lucid Dreams", "You Could Have It So Much Better",
                "British Mode", "Bring it on", "Words", "Can't stop me now", "Synrise", "Real", "Control", "Lucifer", "United", "Black Gloves",
                "All Night Long", "It Hasnt Gone Away", "Man Down", "Only Girl In The World (Cover)", "Commotion", "I Follow Rivers", "Love Lost in Love", "All This Dancin' Around", "Let It Ride", "First Taste",
                "Boulevard of Broken Dreams", "Holiday", "Guns", "Wake Me Up When September Ends", "Basket Case", "Jesus of Suburbia", "American Idiot", "Good Riddance (Time of Your Life)", "Longview", "When I Come Around",
                "Love Has Gone", "Come Alive", "Iron Heart", "Eyes Closed", "Give & Take", "Puppy", "Secret Agent", "Smile", "911", "Pirate Bay",
                "Song 2", "The Universal", "Coffee & TV", "Parklife", "Beetlebum", "Girls & Boys", "Charmless Man", "Tender", "Country House", "Sweet Song",
                "Use Somebody", "Sex On Fire", "Closer", "Pyro", "Radioactive", "Revelry", "Notion", "Knocked Up", "On Call", "Fans",
                "Du Hast", "Sonne", "Mein Teil", "Ich Will", "Feuer Frei", "Mein Herz brennt", "Ich Tu Dir Weh", "Reise, Reise", "Engel", "Amerika",
                "Enjoy the Silence", "Personal Jesus", "Precious", "Policy of Truth", "In Your Room", "Never Let Me Down Again", "It's No Good", "Walking In My Shoes", "Stripped", "Strangelove",
                "Adagio For Strings", "Urban Train", "Traffic", "Dance 4 Life", "Lethal Industry", "Maximal Crazy", "Elements of Life", "C'mon", "Magic Journey", "I Will Be Here",
                "Mammoth", "REJ", "Madness", "Phat Brahms", "The Way We See The World", "Leave The World Behind", "Momentum", "Turn It Up", "Wakanda",
                "This Is What It Feels Like", "Face to Face", "Intense", "In and Out of Love", "YouTopia", "Not Giving Up On Love", "Mirage", "Beautiful Life", "Humming the Lights", "This Light Between Us",
                "Levels", "Wake Me Up", "I Could Be The One", "Silhouettes", "Seek Bromance", "Fade Into Darkness", "Sunshine", "Superlove", "X You", "Last Dance",
                "Titanium", "Club Can't Handle Me", "Sexy Chick", "Without You", "Where Them Girls At", "Memories", "Little Bad Girl", "She Wolf", "When Love Takes Over", "Turn Me On",
                "King of Good", "Silk", "Every Day in My Life", "Stupid Questions", "Vertigo / Memento", "Body Language", "Mandarine Girl", "Darko", "In White Rooms", "Night Falls",
                "I Love… That You Know", "Manic (Disclosure Remix)", "Need U 100%", "Call On Me (Disclosure Bootleg)", "You Used To Hold Me", "My Intention is War", "Control", "Please Don’t Turn Me On (Disclosure Remix)", "Flow", "Get Close",
                "Ping Pong Pineapple", "Bossi", "Too Much Talk", "Turbular Bell", "Auf Und Ab", "Lausanne", "The Real Schranz", "Drumcook", "Stigmata", "Virton"};

        Random rand = new Random();

        //20 nummers per optreden
        for (int i = 0; i < 20; i++) {
            int indexArtiest = rand.nextInt(nummerArtiest.length);
            int indexTitel = rand.nextInt(nummerTitel.length);
            Nummer nummer = new Nummer();
            nummer.setArtiest(nummerArtiest[indexArtiest]);
            nummer.setTitel(nummerTitel[indexTitel]);
            nummer.setDuur(240); //240 seconden voor een nummer
            nummer.setOptreden(optreden);
            nummers.add(nummer);
        }

        for (Nummer n : nummers) {
            session.saveOrUpdate(n);
        }

    }

    public void generateApparatuur(Zone zone) {
        String[] apparatuurNaam = {"Soundmixer", "Boxen", "Spot", "Versterker", "Microfoon"};
        for (int i = 0; i < 5; i++) {
            Apparatuur apparatuur = new Apparatuur();
            apparatuur.setZone(zone);
            apparatuur.setNaam(apparatuurNaam[i]);
            apparatuur.setAantal(10);
            apparaten.add(apparatuur);
        }


        for (Apparatuur a : apparaten) {
            session.saveOrUpdate(a);
        }

    }

    public void generateOptredenApparatuur(Optreden optreden) {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int aantal = rand.nextInt(10);
            OptredenApparatuur optredenApparatuur = new OptredenApparatuur();
            optredenApparatuur.setOptreden(optreden);
            optredenApparatuur.setApparatuur(apparaten.get(i));
            optredenApparatuur.setAantal(aantal);
            optredenApparatuurs.add(optredenApparatuur);
        }

        for (OptredenApparatuur oa : optredenApparatuurs) {
            session.saveOrUpdate(oa);
        }
    }

    public void generateFaciliteiten(Zone zone) {
        String[] faciliteitTypes = {"Ticketcontrole", "Toilet", "EHBO post", "Refreshmentpoint", "Relaxpoint", "Security post", "Kleedkamer"};

        switch (zone.getNaam()) {
            case "Inkom":
                Faciliteit faciliteit = new Faciliteit();
                faciliteit.setZone(zone);
                faciliteit.setType(faciliteitTypes[0]);

                Faciliteit faciliteit16 = new Faciliteit();
                faciliteit16.setZone(zone);
                faciliteit16.setType(faciliteitTypes[4]);
                faciliteiten.add(faciliteit);
                faciliteiten.add(faciliteit16);
                break;
            case "VIP ruimte":
                Faciliteit faciliteit1 = new Faciliteit();
                faciliteit1.setZone(zone);
                faciliteit1.setType(faciliteitTypes[0]);

                Faciliteit faciliteit2 = new Faciliteit();
                faciliteit2.setZone(zone);
                faciliteit2.setType(faciliteitTypes[1]);

                faciliteiten.add(faciliteit1);
                faciliteiten.add(faciliteit2);
                break;
            case "VIP ruimte Mainstage":
                Faciliteit faciliteit3 = new Faciliteit();
                faciliteit3.setZone(zone);
                faciliteit3.setType(faciliteitTypes[0]);

                Faciliteit faciliteit4 = new Faciliteit();
                faciliteit4.setZone(zone);
                faciliteit4.setType(faciliteitTypes[1]);

                faciliteiten.add(faciliteit3);
                faciliteiten.add(faciliteit4);
                break;
            case "Backstage ruimte":
                Faciliteit faciliteit5 = new Faciliteit();
                faciliteit5.setZone(zone);
                faciliteit5.setType(faciliteitTypes[0]);

                Faciliteit faciliteit6 = new Faciliteit();
                faciliteit6.setZone(zone);
                faciliteit6.setType(faciliteitTypes[1]);

                Faciliteit faciliteit15 = new Faciliteit();
                faciliteit15.setZone(zone);
                faciliteit15.setType(faciliteitTypes[6]);

                faciliteiten.add(faciliteit5);
                faciliteiten.add(faciliteit6);
                faciliteiten.add(faciliteit15);
                break;
            case "Sanitair Mainstage":
                Faciliteit faciliteit7 = new Faciliteit();
                faciliteit7.setZone(zone);
                faciliteit7.setType(faciliteitTypes[3]);

                Faciliteit faciliteit8 = new Faciliteit();
                faciliteit8.setZone(zone);
                faciliteit8.setType(faciliteitTypes[1]);

                faciliteiten.add(faciliteit7);
                faciliteiten.add(faciliteit8);
                break;
            case "Sanitair Red Bull":
                Faciliteit faciliteit9 = new Faciliteit();
                faciliteit9.setZone(zone);
                faciliteit9.setType(faciliteitTypes[3]);

                Faciliteit faciliteit10 = new Faciliteit();
                faciliteit10.setZone(zone);
                faciliteit10.setType(faciliteitTypes[1]);

                faciliteiten.add(faciliteit9);
                faciliteiten.add(faciliteit10);
                break;
            case "Camping":
                Faciliteit faciliteit11 = new Faciliteit();
                faciliteit11.setZone(zone);
                faciliteit11.setType(faciliteitTypes[0]);

                Faciliteit faciliteit12 = new Faciliteit();
                faciliteit12.setZone(zone);
                faciliteit12.setType(faciliteitTypes[1]);

                Faciliteit faciliteit13 = new Faciliteit();
                faciliteit13.setZone(zone);
                faciliteit13.setType(faciliteitTypes[2]);

                Faciliteit faciliteit14 = new Faciliteit();
                faciliteit14.setZone(zone);
                faciliteit14.setType(faciliteitTypes[5]);

                faciliteiten.add(faciliteit11);
                faciliteiten.add(faciliteit12);
                faciliteiten.add(faciliteit13);
                faciliteiten.add(faciliteit14);
                break;
        }
        if (zone.getNaam().contains("Festivalterrein")) {
            Faciliteit faciliteit17 = new Faciliteit();
            faciliteit17.setZone(zone);
            faciliteit17.setType(faciliteitTypes[1]);

            Faciliteit faciliteit18 = new Faciliteit();
            faciliteit18.setZone(zone);
            faciliteit18.setType(faciliteitTypes[2]);

            Faciliteit faciliteit19 = new Faciliteit();
            faciliteit19.setZone(zone);
            faciliteit19.setType(faciliteitTypes[5]);

            faciliteiten.add(faciliteit17);
            faciliteiten.add(faciliteit18);
            faciliteiten.add(faciliteit19);
        } else {
            //do nothing
        }


        for (Faciliteit f : faciliteiten) {
            session.saveOrUpdate(f);
        }

    }

    public void generateKleedkamerRegistratie(Optreden optreden) {
        String[] kleedkamerFlyer = {"Eten", "Drinken", "Eten en drinken", "Champagne", "Snacks", "Full Option"};
        Random rand = new Random();
        int index = rand.nextInt(6);
        Faciliteit kleedkamerFaciliteit = new Faciliteit();
        Date begin = new Date();

        for (Faciliteit f : faciliteiten) {
            if (f.getType().contains("Kleedkamer")) {
                kleedkamerFaciliteit = f;
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(optreden.getStartTime());
        cal.add(Calendar.HOUR_OF_DAY, -1);
        begin = cal.getTime();

        KleedkamerRegistratie kleedkamerRegistratie = new KleedkamerRegistratie();
        kleedkamerRegistratie.setBeginDatum(begin);
        kleedkamerRegistratie.setEindDatum(optreden.getStartTime());
        kleedkamerRegistratie.setFaciliteit(kleedkamerFaciliteit);
        kleedkamerRegistratie.setFlyer(kleedkamerFlyer[index]);
        kleedkamerRegistratie.setOptreden(optreden);


        kleedkamerRegistraties.add(kleedkamerRegistratie);

        for (KleedkamerRegistratie kr : kleedkamerRegistraties) {
            session.saveOrUpdate(kr);
        }
    }

    public void generatePersorgaan() {
        String[] persNamen = {"JIM TV", "TMF", "MTV", "HLN", "GVA"};

        for (int i = 0; i < persNamen.length; i++) {
            PersOrgaan persOrgaan = new PersOrgaan();
            persOrgaan.setNaam(persNamen[i]);
            persOrganen.add(persOrgaan);
        }
    }

    public void generatePersToelating(Artiest artiest) {
        Perstoelating.ToelatingSoort[] soorten = {Perstoelating.ToelatingSoort.FILM, Perstoelating.ToelatingSoort.FOTO, Perstoelating.ToelatingSoort.FOTOFILM};
        Random rand = new Random();

        int indexPers = rand.nextInt(persOrganen.size());
        int indexSoort = rand.nextInt(3);

        Perstoelating perstoelating = new Perstoelating();
        perstoelating.setArtiest(artiest);
        perstoelating.setPersOrgaan(persOrganen.get(indexPers));
        perstoelating.setSoort(soorten[indexSoort]);
        persToelatingen.add(perstoelating);

        for (Perstoelating pt : persToelatingen) {
            session.saveOrUpdate(pt);
        }
    }

    public void generateTrackings(Zone zone) {
        int month, year, day, hourOfDay, minute, second, index;
        Boolean isIn;
        Random rand = new Random();
        List<Integer> polsbandjes = new ArrayList<>();

        index = rand.nextInt(8) + 8;

        for (int i = 0; i < 750; i++) {
            polsbandjes.add(i);
        }

        for (FestivalDag fd : festivalDays) {
            for (Integer p : polsbandjes) {
                for (int i = 0; i < index; i++) {

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fd.getDate());

                    month = cal.get(Calendar.MONTH);
                    year = cal.get(Calendar.YEAR);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    hourOfDay = rand.nextInt(12) + 12;
                    minute = rand.nextInt(60);
                    second = rand.nextInt(60);

                    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hourOfDay, minute, second);

                    Date timestamp = calendar.getTime();

                    String formattedTimeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);

                    if (i % 2 == 0) {
                        isIn = false;
                    } else {
                        isIn = true;
                    }

                    if (timestamp.before(new Date())) {
                        Tracking tracking = new Tracking();
                        tracking.setZone(zone);
                        tracking.setTimestamp(new Date(formattedTimeStamp));
                        tracking.setDirection(isIn);
                        tracking.setPolsbandId(p);
                        trackings.add(tracking);
                    } else {
                        //do nothing
                    }
                }
            }
        }

        for (Tracking t : trackings) {
            session.saveOrUpdate(t);
        }


    }

    public void generateFestivalgangers() {
        String[] names = {"Virginia King", "Ann Barnes", "Janice Ramirez", "George Gray", "Rachel Long", "Adam Allen", "Maria Wilson", "Michael White", "Pamela James",
                "Julie Turner", "Daniel Lopez", "Melissa Green", "Jose Foster", "Louis Reed", "Phyllis Patterson", "Steven Cox", "Sara Torres", "Emily Hall", "Lawrence Wood",
                "Ruth Hernandez", "Annie Watson", "Bobby Bryant", "Ronald Perry", "Irene Bell", "Kathryn Brown", "Teresa Miller", "Sean Perez", "Stephen Williams",
                "Catherine Bailey", "Nancy Hill", "Edward Taylor", "Philip Evans", "Donald Rogers", "Judith Jackson", "Evelyn Lee", "Lisa Bennett", "Laura Harris",
                "Nicole Powell", "Christina Edwards", "Victor Clark", "Jacqueline Campbell", "Carl Ward", "Carol Davis", "Lois Wright", "Kelly Diaz", "Samuel Thompson",
                "Jean Moore", "Lori Martin", "Debra Morgan", "Diane Sanders", "Barbara Stewart", "David Rivera", "Eugene Ross", "Gerald Mitchell", "Theresa Roberts",
                "Betty Nelson", "Shirley Howard", "Christopher Murphy", "Richard Butler", "Lillian Jones", "Jessica Martinez", "Ralph Kelly", "Randy Walker", "Jimmy Lewis",
                "Mildred Robinson", "Harry Hughes", "Helen Adams", "Louise Young", "Harold Gonzalez", "Mark Parker", "Tammy Henderson", "Gary Peterson", "Judy Gonzales",
                "Karen Phillips", "Gregory Smith", "Wayne Richardson", "Joe Price", "Marilyn Morris", "Matthew Cook", "Deborah Flores", "Jack Garcia", "Michelle Coleman",
                "Scott Thomas", "Kenneth Griffin", "Susan Brooks", "Jeffrey Carter", "Marie Cooper", "Ryan Sanchez", "Joyce Johnson", "Carlos Alexander", "Fred Collins",
                "Stephanie Russell", "Rebecca Jenkins", "Shawn Anderson", "Kevin Baker", "Amy Rodriguez", "Chris Scott", "Heather Simmons", "Beverly Washington", "Marta Phillips",
                "Tammy Smith", "Peter Hill", "Katherine Perez", "Jack Ramirez", "Steve Foster", "Wayne Roberts", "Gregory Simmons", "Andrew Nelson", "Carl Gonzales",
                "Ruth Davis", "Amanda Adams", "Joseph Scott", "William White", "Kenneth Wilson", "Sara Wood", "Phillip Sanders", "Ryan Bennett", "Pamela Howard", "Kelly Jenkins",
                "Nicole Lee", "Aaron Perry", "Samuel Thomas", "Russell James", "Benjamin Lopez", "Janice Coleman", "Scott Ross", "Ashley Watson", "Carlos Edwards", "Lois Kelly",
                "Joe Cooper", "Lori Barnes", "Justin Jackson", "Bobby Evans", "Jimmy Richardson", "Donald Turner", "Edward Washington", "Jeremy Griffin", "Rose Bryant",
                "Judy Henderson", "Jonathan Hughes", "Fred Clark", "Melissa Butler", "Stephen Powell", "Timothy Ward", "Doris Thompson", "Kimberly Diaz", "Christopher Morgan",
                "Terry Collins", "Elizabeth Johnson", "Douglas Russell", "Evelyn Parker", "Deborah Flores", "Angela Patterson", "Diane Gonzalez", "Joan Bailey", "Dennis Baker",
                "Julia Martin", "Louis Reed", "Helen Anderson", "Albert Taylor", "Brenda Rodriguez", "Roy Bell", "Kevin Brown", "Theresa Williams", "Charles Stewart",
                "Juan Cook", "George Mitchell", "Joshua Martinez", "Eric Walker", "Paul Hernandez", "Willie Murphy", "Jason Green", "Kathleen Miller", "David Jones",
                "Alice Alexander", "Louise Sanchez", "Chris Hall", "Ronald Campbell", "Anne Carter", "Gary Phillips", "Brian Rogers", "Cheryl Long", "Keith Morris",
                "Ralph Rivera", "Philip Lewis", "Michelle Allen", "Nicholas Harris", "Lillian Young", "John Moore", "Frances Wright", "Jean Garcia", "James Brooks", "Bonnie Cox",
                "Janet Torres", "Carolyn Robinson", "Martha Peterson", "Patricia Gray", "Shawn Price", "Jerry King", "Victor Huysmans"};

        for (int i = 0; i < names.length; i++) {
            FestivalGanger festivalGanger = new FestivalGanger();
            festivalGanger.setNaam(names[i]);
            festivalGangers.add(festivalGanger);
            generateTicketVerkoop(festivalGanger);
        }

        for (FestivalGanger fg : festivalGangers) {
            session.saveOrUpdate(fg);
        }

    }

    public void generateTicketVerkoop(FestivalGanger festivalGanger) {
        TicketVerkoop.VerkoopsType[] types = {TicketVerkoop.VerkoopsType.FESTIVAL, TicketVerkoop.VerkoopsType.VERKOOPSCENTRA, TicketVerkoop.VerkoopsType.WEB};
        Random rand = new Random();
        int index = rand.nextInt(festivals.size());
        int indexType = rand.nextInt(types.length);

        TicketVerkoop ticketVerkoop = new TicketVerkoop();
        ticketVerkoop.setFestivalGanger(festivalGanger);
        ticketVerkoop.setTimestamp(new Date());
        ticketVerkoop.setFestival(festivals.get(index));
        ticketVerkoop.setType(types[indexType]);
        ticketVerkopen.add(ticketVerkoop);
        generateTicketType();
        generateTickets(ticketVerkoop);

        for (TicketVerkoop tv : ticketVerkopen) {
            session.saveOrUpdate(tv);
        }
    }

    public void generateTicketType() {
        String naamRegular = "";
        String naamVIP = "";
        String naamPers = "";
        int prijsRegular = 0;
        int prijsVIP = 0;
        int prijsPers = 0;

        Random rand = new Random();

        for (FestivalDag fd : festivalDays) {
            TicketType regularTicketType = new TicketType();
            TicketType vipTicketType = new TicketType();
            TicketType persTicketType = new TicketType();

            String day = new SimpleDateFormat("EEEE").format(fd.getDate());
            naamRegular = "Regular dagticket van " + fd.getFestival().getName() + " op " + day;
            naamVIP = "VIP dagticket van " + fd.getFestival().getName() + " op " + day;
            naamPers = "Pers dagticket van " + fd.getFestival().getName() + " op " + day;

            prijsRegular = rand.nextInt(20) + 30;
            prijsVIP = rand.nextInt(40) + 50;
            prijsPers = rand.nextInt(10) + 20;

            regularTicketType.setNaam(naamRegular);
            regularTicketType.setPrijs(prijsRegular);
            regularTicketType.setFestival(fd.getFestival());

            vipTicketType.setNaam(naamVIP);
            vipTicketType.setPrijs(prijsVIP);
            vipTicketType.setFestival(fd.getFestival());

            persTicketType.setNaam(naamPers);
            persTicketType.setPrijs(prijsPers);
            persTicketType.setFestival(fd.getFestival());

            ticketTypes.add(regularTicketType);
            ticketTypes.add(vipTicketType);
            ticketTypes.add(persTicketType);

            generateTicketZones(regularTicketType, fd.getFestival());
            generateTicketZones(vipTicketType, fd.getFestival());
            generateTicketZones(persTicketType, fd.getFestival());

            generateTicketDag(regularTicketType);
            generateTicketDag(vipTicketType);
            generateTicketDag(persTicketType);

        }

        for (Festival f : festivals) {
            TicketType regularTicketType = new TicketType();
            TicketType vipTicketType = new TicketType();
            TicketType persTicketType = new TicketType();

            naamRegular = "Regular combiticket van " + f.getName();
            naamVIP = "VIP combiticket van " + f.getName();
            naamPers = "Pers combiticket van " + f.getName();

            prijsRegular = rand.nextInt(70) + 70;
            prijsVIP = rand.nextInt(80) + 90;
            prijsPers = rand.nextInt(20) + 30;

            vipTicketType.setNaam(naamVIP);
            vipTicketType.setPrijs(prijsVIP);
            vipTicketType.setFestival(f);

            persTicketType.setNaam(naamPers);
            persTicketType.setPrijs(prijsPers);
            persTicketType.setFestival(f);

            regularTicketType.setNaam(naamRegular);
            regularTicketType.setPrijs(prijsRegular);
            regularTicketType.setFestival(f);

            ticketTypes.add(regularTicketType);
            ticketTypes.add(vipTicketType);
            ticketTypes.add(persTicketType);

            generateTicketZones(regularTicketType, f);
            generateTicketZones(vipTicketType, f);
            generateTicketZones(persTicketType, f);

            generateTicketDag(regularTicketType);
            generateTicketDag(vipTicketType);
            generateTicketDag(persTicketType);

        }

        for (TicketType ticketType : ticketTypes) {
            session.saveOrUpdate(ticketType);
        }

    }

    public void generateTickets(TicketVerkoop ticketVerkoop) {
        Random rand = new Random();

        for (FestivalDag fd : festivalDays) {
            for (int i = 0; i < 38; i++) {
                if (aantalTickets < fd.getAantalBeschikbareTickets()) {
                    int indexType = rand.nextInt(ticketTypes.size());
                    int indexPers = rand.nextInt(persOrganen.size());
                    if (ticketTypes.get(indexType).getNaam().contains(fd.getFestival().getName())) {
                        Ticket ticket = new Ticket();
                        ticket.setTicketVerkoop(ticketVerkoop);
                        ticket.setTicketType(ticketTypes.get(indexType));
                        if (ticketTypes.get(indexType).getNaam().contains("Pers")) {
                            ticket.setPersOrgaan(persOrganen.get(indexPers));
                        }
                        tickets.add(ticket);
                        aantalTickets++;
                    } else {
                        i--;
                    }
                } else {
                    System.out.println("Tickets voor het festival " + fd.getFestival().getName() + " op dag " + fd.getDate() + " zijn uitverkocht !");
                }
            }
        }


        for (Ticket t : tickets) {
            session.saveOrUpdate(t);
        }

    }

    public void generateTicketZones(TicketType ticketType, Festival festival) {
        if (ticketType.getNaam().contains("Regular")) {
            for (Zone z : zones) {
                if ((z.getNaam().contains("Inkom") || z.getNaam().contains("Camping") || z.getNaam().contains("Festivalterrein")) && z.getFestival() == festival) {
                    TicketZone ticketZone = new TicketZone();
                    ticketZone.setTicketType(ticketType);
                    ticketZone.setZone(z);
                    ticketZones.add(ticketZone);
                }
            }
        } else if (ticketType.getNaam().contains("VIP")) {
            for (Zone z : zones) {
                if ((z.getNaam().contains("Inkom") || z.getNaam().contains("Camping") || z.getNaam().contains("Festivalterrein") || z.getNaam().contains("VIP")) && z.getFestival() == festival) {
                    TicketZone ticketZone = new TicketZone();
                    ticketZone.setTicketType(ticketType);
                    ticketZone.setZone(z);
                    ticketZones.add(ticketZone);
                }
            }
        } else {
            for (Zone z : zones) {
                if (z.getFestival() == festival) {
                    TicketZone ticketZone = new TicketZone();
                    ticketZone.setTicketType(ticketType);
                    ticketZone.setZone(z);
                    ticketZones.add(ticketZone);
                }
            }
        }

        for (TicketZone tz : ticketZones) {
            session.saveOrUpdate(tz);
        }
    }

    public void generateTicketDag(TicketType ticketType) {

        for (FestivalDag fd : festivalDays) {
            if (ticketType.getNaam().contains("combiticket")) {
                if (fd.getFestival() == ticketType.getFestival()) {
                    TicketDag ticketDag = new TicketDag();
                    ticketDag.setTicketType(ticketType);
                    ticketDag.setFestivalDag(fd);
                    ticketDagen.add(ticketDag);

                } else {
                    String day = new SimpleDateFormat("EEEE").format(fd.getDate());
                    if (ticketType.getNaam().contains(day) && ticketType.getFestival() == fd.getFestival()) {
                        TicketDag ticketDag = new TicketDag();
                        ticketDag.setTicketType(ticketType);
                        ticketDag.setFestivalDag(fd);
                        ticketDagen.add(ticketDag);

                    }
                }

                for (TicketDag td : ticketDagen) {
                    session.saveOrUpdate(td);
                }

            }


        }
    }
}
