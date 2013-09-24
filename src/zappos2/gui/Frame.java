/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import zappos2.Zappos2;

/**
 *
 * @author RoyZheng
 */
public class Frame extends JFrame implements ActionListener {

    InputPanel ip;
    Zappos2 zappos;

    public Frame(Zappos2 z) {
        this.zappos = z;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(this.ip = new InputPanel(this));
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Double d;
        Integer i;
        try {
            d = Double.parseDouble(this.ip.getDonation());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid donation amount");
            return;
        }
        try {
            i = Integer.parseInt(this.ip.getNumber());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter an integer for gifts to donate");
            return;
        }
        this.zappos.displayRecommendedGifts(d, i);
    }
}
