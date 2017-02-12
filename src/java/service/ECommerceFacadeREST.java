package service;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("commerce")
public class ECommerceFacadeREST {

    @Context
    private UriInfo context;

    public ECommerceFacadeREST() {
    }

    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ECommerce
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    @POST
    @Path("createSalesRecord")
    public Response createSalesRecord(@QueryParam("memberId") long memberId, 
                                      @QueryParam("amountDue") double amountDue){
        try {
            Date currDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?zeroDateTimeBehavior=convertToNull&user=root&password=12345");
            String stmt = "INSERT into salesrecordentity (AMOUNTDUE,AMOUNTPAID,AMOUNTPAIDUSINGPOINTS,CREATEDDATE,CURRENCY,LOYALTYPOINTSDEDUCTED,POSNAME,MEMBER_ID,STORE_ID)\n" +
                          "VALUES (?,?,0,?,'SGD',0,'ECommerce',?,10001)";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, Double.toString(amountDue));
            ps.setString(2, Double.toString(amountDue));
            ps.setString(3, dateFormat.format(currDate));
            ps.setString(4, Long.toString(memberId));
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("updateStorage")
    public Response updateStorage(@QueryParam("itemId") String itemId,
                                  @QueryParam("quantity") int quantity){
        try{
            String updateQty = "-"+quantity;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?zeroDateTimeBehavior=convertToNull&user=root&password=12345");
            String stmt = "INSERT into lineitementity (QUANTITY,ITEM_ID)\n" +
                          "VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, updateQty);
            ps.setString(2, itemId);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
        
    }
    
}
//DONE

