/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author RoyZheng
 */
public class InputPanel extends JPanel {

    JButton go;
    JTextField money, numproducts;

    public InputPanel(ActionListener al) {

        this.go = new JButton("GO");
        this.go.addActionListener(al);
        this.money = new JTextField();
        this.numproducts = new JTextField();
        this.money.setColumns(20);
        this.numproducts.setColumns(20);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Desired number of gifts"), gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 1;
        this.add(this.numproducts, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Donation amount"), gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 1;
        this.add(this.money, gbc);
        gbc = (GridBagConstraints) gbc.clone();
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(this.go, gbc);


    }

    public String getDonation() {
        return this.money.getText();
    }

    public String getNumber() {
        return this.numproducts.getText();
    }
}
