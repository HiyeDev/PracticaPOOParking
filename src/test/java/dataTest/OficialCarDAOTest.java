package dataTest;

import cars.OficialCar;
import data.OficialCarDAO;

public class OficialCarDAOTest {
    public static void main(String[] args) {
        /*System.out.println(OficialCarDAO.existsOnOficialCarTable(new OficialCar("A")));
        System.out.println(OficialCarDAO.existsOnOficialCarTable(new OficialCar("1539BFH")));*/
        
        OficialCarDAO.registerOficialCar(new OficialCar("3427MHL"));
    }
}
