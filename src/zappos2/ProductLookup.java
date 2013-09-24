/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import zappos2.requests.SearchEntry;
import zappos2.requests.SearchEntryFinalPriceComparator;

/**
 *
 * @author RoyZheng
 */
public class ProductLookup {

    public static final String PATH = "./products.dat";
    List<SearchEntry> entries = new ArrayList<SearchEntry>();

    public ProductLookup() {
    }

    public synchronized void add(SearchEntry e) {
        this.entries.add(e);
    }

    public static ProductLookup load() throws NoSuchFileException, IOException {
        Path file = Paths.get(PATH);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            StringBuilder b = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                b.append(line);
            }
            return (new Gson()).fromJson(b.toString(), ProductLookup.class);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            throw x;
        }
    }

    public void save() {
        Path p = Paths.get(PATH);
        Charset charset = Charset.forName("US-ASCII");
        String s = (new Gson()).toJson(this);
        try (BufferedWriter writer = Files.newBufferedWriter(p, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void removeDuplicates() {
        List<SearchEntry> nr = new ArrayList<SearchEntry>();
        for (SearchEntry se : this.entries) {
            boolean uniq = true;
            for (SearchEntry nre : nr) {
                if (nre.getProductId() == se.getProductId()) {
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                nr.add(se);;
            }
        }
        this.entries = nr;
    }

    public void sort() {
        Collections.sort(this.entries, new SearchEntryFinalPriceComparator());
    }

    public List<SearchEntry> getUniqueProducts(Double tot, Integer num) {
        Double remaining = tot;
        List<SearchEntry> products = new ArrayList<SearchEntry>();
        products.addAll(this.entries);

        List<SearchEntry> toreturn = new ArrayList<SearchEntry>();
        SearchEntry init = this.getItemByPrice(tot / num);
        int pivot = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) == init) {
                pivot = i;
                remaining -= products.get(i).getPriceAfterDiscount();
                toreturn.add(products.get(i));
                products.remove(i);
                break;
            }
        }
        while (toreturn.size() < num) {
            //no more unique products
            if (products.isEmpty()) {
                SearchEntry s = getItemByPrice(remaining / (num - toreturn.size()));
                for (int i = 0; i != num - toreturn.size(); i++) {
                    toreturn.add(s);
                }
            }
            double target_price = remaining / (num - toreturn.size());
            SearchEntry se = this.getItemByPrice(target_price, products);
            remaining -= se.getPriceAfterDiscount();
            toreturn.add(se);
            products.remove(se);
        }
        return toreturn;
    }

    public SearchEntry getItemByPrice(Double p) {
        return this.getItemByPrice(p, this.entries);
    }

    public SearchEntry getItemByPrice(Double p, List<SearchEntry> tosearch) {
        for (int i = 0; i != tosearch.size(); i++) {
            if (tosearch.get(i).getPriceAfterDiscount() > p) {
                if (i == 0) {
                    return tosearch.get(i);
                }
                return tosearch.get(i).getPriceAfterDiscount() - p > p - tosearch.get(i - 1).getPriceAfterDiscount() ? tosearch.get(i - 1) : tosearch.get(i);
            }
        }
        return tosearch.get(tosearch.size() - 1);
    }
}
