/**
 *
 */
package it.cnr.ilc.ga.action.management;

import it.cnr.ilc.ga.handlers.exist.ExistQuery;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 * @author Angelo Del Grosso
 *
 */
@ManagedBean(name = "qmanager")
@SessionScoped
public class QueryManagerBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4368268428027615736L;
    private ExistQuery eq = null;

    /**
     *
     */
    public QueryManagerBean() {
        eq = new ExistQuery();
        eq.connect(); // TODO SEVERE togliere la connect dal costruttore
    }

    public String search(
            String ntgs,
            String ntas,
            String fg1,
            String tg1,
            String pg1,
            String fg2,
            String tg2,
            String pg2,
            String fg3,
            String tg3,
            String pg3,
            String fa1,
            String ta1,
            String pa1,
            String fa2,
            String ta2,
            String pa2,
            String fa3,
            String ta3,
            String pa3,
            String bgs,
            String bas,
            String bs
    ) {
        return eq.query(
                ntgs,
                ntas,
                fg1,
                tg1,
                pg1,
                fg2,
                tg2,
                pg2,
                fg3,
                tg3,
                pg3,
                fa1,
                ta1,
                pa1,
                fa2,
                ta2,
                pa2,
                fa3,
                ta3,
                pa3,
                bgs,
                bas,
                bs
        );
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
