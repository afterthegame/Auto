/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author afterthegame
 */
package parcer;

import auto.CarEdition;
import auto.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author afterthegame
 */
public class Sort {

    public static ArrayList<BrandModelEditionBody> SortBrand(ArrayList<BrandModelEditionBody> e) {
        Comparator<BrandModelEditionBody> comparator;
        comparator = new Comparator<BrandModelEditionBody>() {
            @Override
            public int compare(BrandModelEditionBody e1, BrandModelEditionBody e2) {
                return e1.getBrand().getName().compareTo(e2.getBrand().getName());
            }
        };

        Collections.sort(e, comparator);
        return e;
    }

    public static ArrayList<BrandModelEditionBody> SortModel(ArrayList<BrandModelEditionBody> e) {
        Comparator<BrandModelEditionBody> comparator;
        comparator = new Comparator<BrandModelEditionBody>() {
            @Override
            public int compare(BrandModelEditionBody e1, BrandModelEditionBody e2) {
                String m1 = e1.getBrand().getName()+e1.getModel().getName();
                String m2 = e2.getBrand().getName()+e1.getModel().getName();
                return m1.compareTo(m2);
            }
        };

        Collections.sort(e, comparator);
        return e;
    }

    public static ArrayList<BrandModelEditionBody> SortEdition(ArrayList<BrandModelEditionBody> e) {
        Comparator<BrandModelEditionBody> comparator;
        comparator = new Comparator<BrandModelEditionBody>() {
            @Override
            public int compare(BrandModelEditionBody e1, BrandModelEditionBody e2) {
                CarEdition ed1 = e1.getEdition();
                CarEdition ed2 = e1.getEdition(); 
                if (ed1.getId() >= ed2.getId()) return ed1.getId();
                else return ed2.getId();
            }
        };

        Collections.sort(e, comparator);
        return e;
    }

    public static ArrayList<BrandModelEditionBody> SortBody(ArrayList<BrandModelEditionBody> e) {
        Comparator<BrandModelEditionBody> comparator;
        comparator = new Comparator<BrandModelEditionBody>() {
            @Override
            public int compare(BrandModelEditionBody e1, BrandModelEditionBody e2) {
                CarBody b1 = e1.getBody();
                CarBody b2 = e1.getBody();
                return b1.getName().compareTo(b2.getName());
            }
        };

        Collections.sort(e, comparator);
        return e;
    }
}
