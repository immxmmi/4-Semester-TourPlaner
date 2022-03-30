package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourDaoImpl extends AbstractDBTable implements TourDao {

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
     static ImageDaoImpl imageDaoImpl = new ImageDaoImpl();
     static CityDaoImpl cityDaoImpl = new CityDaoImpl();

    public TourDaoImpl(){
        this.tableName = "\"tour\"";
    }

    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public Tour buildClass(ResultSet result) {
        try {
            if (result.next()) {
                Tour tour = Tour.builder()
                        .tourID(result.getString("tourid"))
                        .name(result.getString("name"))
                        .transporter(Transporter.valueOf(result.getString("transporter")))
                        .form(cityDaoImpl.getItemById(result.getString("from")))
                        .to(cityDaoImpl.getItemById(result.getString("to")))
                        .description(result.getString("description"))
                        .distance(0)
                        .time(null)
                        .routeImage(imageDaoImpl.getItemById(result.getString("routeImage")))
                        .build();

                this.closeStatement();

                return tour;
            }
        } catch (SQLException e) {
            System.out.println(TextColor.ANSI_RED + "GETOBJECT -ERRROR: " + e + TextColor.ANSI_RESET);
            e.printStackTrace();
        }
        this.closeStatement();
        return null;
    }
    /*******************************************************************/




    @Override
    public Tour getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"tourId\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public Tour insert(Tour item) {
        if (item == null) {
            return null;
        }
        if(getItemById(item.getTourID()) == null) {
            this.parameter = new String[]{
                    "" + item.getTourID(),
                    "" + item.getName(),
                    "" + item.getForm().getCityID(),
                    "" + item.getTo().getCityID(),
                    "" + item.getDistance(),
                    "" + item.getTime(),
                    "" + item.getTransporter(),
                    "" + item.getRouteImage().getImageID(),
                    "" + item.getDescription()
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourId\",name,\"from\",\"to\",\"distance\",\"time\",\"transporter\",\"routeImage\",\"description\")VALUES(?,?,?,?,?,?,?,?,?);", this.parameter);
            return getItemById(item.getTourID());
        }
        return null;
    }

    @Override
    public Tour update(Tour item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getName(),
                "" + item.getForm().getCityID(),
                "" + item.getDistance(),
                "" + item.getTime(),
                "" + item.getTransporter(),
                "" + item.getRouteImage().getImageID(),
                "" + item.getDescription(),
                "" + item.getTourID()
        };


        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET " +
                        "\"name\" = ?, " +
                        "\"from\" = ? " +
                        "distance = ? " +
                        "\"time\" = ? " +
                        "\"transporter\" = ? " +
                        "\"routeImage\" = ? " +
                        "\"description\" = ? " +



                        "WHERE \"tour_id\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourID());
    }

    @Override
    public ArrayList<Tour> getAllTourOrderByName() {

        ArrayList<Tour> allTour = new ArrayList<>();
        ArrayList<String> allTourIDs =  new ArrayList<>();

        this.parameter = new String[]{};

        this.setStatement("SELECT  *  FROM \"tour\" ORDER BY \"name\";", this.parameter);

        try{

            while (this.result.next()) {

                allTourIDs.add(result.getString("tourId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= allTourIDs.size(); i++){
            getItemById(allTourIDs.get(i));
        }

        return allTour;
    }
}
