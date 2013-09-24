/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2.requests;

import java.util.Comparator;

/**
 *
 * @author RoyZheng
 */
public class SearchEntryFinalPriceComparator implements Comparator<SearchEntry> {

    @Override
    public int compare(SearchEntry a, SearchEntry b) {

        return (int) (a.getPriceAfterDiscount() - b.getPriceAfterDiscount());
    }
}
