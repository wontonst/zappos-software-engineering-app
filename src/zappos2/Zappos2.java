/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import zappos2.gui.Frame;
import zappos2.requests.Search;
import zappos2.requests.SearchEntry;

/**
 *
 * @author RoyZheng
 */
public class Zappos2 {

    public static final String ZAPPOS_URL = "http://api.zappos.com/";
    public static final String API_KEY = "52ddafbe3ee659bad97fcce7c53592916a6bfd73";
    public static final String API_TOKEN = "key=" + API_KEY;
    public static final String product_url = ZAPPOS_URL + "Product";
    public static final String search_url = ZAPPOS_URL + "Search?includes=[\"productName,productId\"]&";
    Gson gson = new Gson();
    ProductLookup p;
    Frame gui;

    public Zappos2() {
        this.gui = new Frame(this);
        try {
            this.p = ProductLookup.load();
        } catch (IOException ex) {
            this.buildProductLookup();
        }
    }

    public Search getSearch(String term) throws UnknownHostException {
        Search s = null;
        try {
            String reply = Get.sendRequest(new URL(Zappos2.buildString(search_url, "term=", term, "&", API_TOKEN)));
            System.out.println("RAW JSON:" + reply);
            s = gson.fromJson(reply, Search.class);
            s.removeDuplicates();

            System.out.println(s.toString());
        } catch (UnknownHostException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(Zappos2.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void buildProductLookup() {
        this.p = new ProductLookup();
        String[] keywords = new String[]{"boot", "kettle", "umbrella", "coat", "dress", "khaki", "pants", "teen", "adult", "suit", "swim", "shoe", "ugg", "premium", "bracelete", "ring", "child", "kid", "new", "jean", "train", "short", "long", "young", "converse", "nike", "run", "bag", "wallet", "pack", "messenger", "duffel", "sock", "coat", "under"};
        for (String i : keywords) {
            try {
                Thread.sleep(200);
                Search s = getSearch(i);
                if (!s.getEntries().isEmpty()) {
                    for (SearchEntry se : s.getEntries()) {
                        this.p.add(se);
                    }
                    System.out.println(p.entries.size());
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(Zappos2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Zappos2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.p.removeDuplicates();
        this.p.sort();
        this.p.save();
    }

    public void displayRecommendedGifts(Double donation, Integer numgifts) {
        SearchEntry s = this.p.getItemByPrice(donation / numgifts);
        StringBuilder b = new StringBuilder();
        b.append("Recommendation: \nOptimal:");
        b.append(numgifts);
        b.append(" x ");
        b.append(s.getProductName());
        b.append(" @ ");
        b.append(s.getOriginalPrice());
        b.append(" discounted at ");
        b.append(s.getPercentOff());
        b.append(" for $");
        b.append((int) (s.getPriceAfterDiscount() * 100) / 100.0);
        b.append(" each, totaling ");
        b.append(numgifts * (int) (s.getPriceAfterDiscount() * 100) / 100.0);


        List<SearchEntry> uniq = this.p.getUniqueProducts(donation, numgifts);
        b.append("\n");
        b.append("Unique: ");
        double tot = 0.0;
        for (SearchEntry se : uniq) {
            b.append(se.toPriceString());
            b.append("\n");
            tot += se.getPriceAfterDiscount();
        }
        b.append("Total: ");
        b.append(tot);
        JOptionPane.showMessageDialog(this.gui, b.toString());
        System.out.println(b.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Zappos2 z = new Zappos2();
    }

    public static String buildString(String... v) {
        StringBuilder sb = new StringBuilder();
        for (String s : v) {
            sb.append(s);
        }
        return sb.toString();
    }
}
