package Servers.Concordia;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daksh
 */
public class CONOperationsImplementation implements CONOperations {

    @Override
    public  int addItem(String managerID, String itemID, String itemName, int quantity) {
        if (Data.up.containsKey(managerID)) {
            try {
                synchronized(this){
                BookDetails bd = Data.bi.get(itemID);
                if (bd == null) {
                    Data.bi.put(itemID, new BookDetails(itemName, quantity));
                   // System.out.println("added");
                } else {
                    bd.quantity = bd.quantity + quantity;
                    Data.bi.put(itemID, bd);
                    //System.out.println("updated");
                }
                }
                return 1;                                                               //return 1 if everything perfect
            } catch (Exception e) {
                return 0;                                                           //return 0 if something got wrong
            }
        } else {
            return -1;                                                          //return -1 if not manager
        }
    }

    @Override
    public int removeItem(String managerID, String itemID, int quantity) {
        if (Data.up.containsKey(managerID)) {
            try {
                BookDetails bd = Data.bi.get(itemID);
                if (bd == null) {
                    return 3;                                                       //bookid not available
                } else {
                    int temp = bd.quantity - quantity;
                    if (temp <= 0) {
                        Data.bi.remove(itemID);
                        return 2;                                                   //return 2 if item completely deleted
                    } else {
                        bd.quantity = bd.quantity - temp;
                        Data.bi.put(itemID, bd);
                        return 1;                                                   //return 1 item quantity decreased 
                    }
                }

            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;                                                           //return 0 if something went wrong
            }
        } else {
            return -1;                                                          //invalid manager id
        }
    }

    @Override
    public  List listItemAvailability(String managerID) {
        List b = new ArrayList<String>();
        for (String id : Data.bi.keySet()) {
            String c;
            c = id + " " + Data.bi.get(id).name + " " + Data.bi.get(id).quantity;
            b.add(c);
        }
        return b;
    }

    @Override
    public int borrowItem(String userID, String itemID, String numberOfDays ,int waitWish) {
        if(Data.up.containsKey(userID))
        {
            System.out.println("not yet created");
        }
        return 0;
    }

    @Override
    public boolean findItem(String userID, String itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean returnItem(String userID, String itemID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int authuser(String id, String passcode) {
        String pass = Data.up.get(id);
        // System.out.println(pass + " " + passcode);
        if (pass == null) {
            return 0;                                   //return 0 for no userid found
        } else if (pass.equals(passcode)) {
            if (id.charAt(3) == 'M') {
                return 1;                               //returns 1 for manager
            } else if (id.charAt(3) == 'U') //returns 2 for users
            {
                return 2;
            } else {
                return 0;                               // for different user id other than m and u at 4th place
            }
        } else {
            return -1;                                  //returns -1 for password mistype             
        }
    }

}
