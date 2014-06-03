/**
 * ***************************************************************************
 * Copyright 2010 Vandopoly Team * Licensed under the Apache License, Version
 * 2.0 (the "License"); * you may not use this file except in compliance with
 * the License. * You may obtain a copy of the License at * *
 * http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 * law or agreed to in writing, software * distributed under the License is
 * distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.* See the License for the specific language
 * governing permissions and * limitations under the License. *
 * **************************************************************************
 */
package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Font;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

import org.vandopoly.model.Card;
import org.vandopoly.model.Player;

/*
 * MessagePopUp class is a replica of the CardPanel class but used for displaying popups
 * for Corners Spaces and other necessary necessary game messages.
 * 
 * @author James Kasten & Allie Mazzia
 */
public class MessagePopUp extends JPanel implements ChangeListener {

    static final long serialVersionUID = 102;

    static final int TAX_MIN = 0;
    static final int TAX_MAX = 3500;
    static final int TAX_INIT = 0;

    private JLabel amountLabel_;

    // Constructor used to display generic messages
    public MessagePopUp(String message) {
        Vector<String> lines = processText(message);
        int numOfLabels = lines.size();

        JPanel basePanel = new JPanel();
        JPanel messagePanel = new JPanel();
        JLabel labels[] = new JLabel[numOfLabels];

        int hGap = 10, vGap = 10;
        GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
        GridLayout gridLayout2 = new GridLayout(numOfLabels, 1, 0, 0);

        basePanel.setLayout(null);
        messagePanel.setLayout(null);

        Font labelFont = new Font("arial", Font.PLAIN, 22);
        Font buttonFont = new Font("arial", Font.PLAIN, 18);
        Font titleFont = new Font("arial", Font.PLAIN, 14);

        for (int i = 0; i < numOfLabels; ++i) {
            labels[i] = new JLabel(lines.elementAt(i));
            labels[i].setFont(labelFont);
            messagePanel.add(labels[i]);
        }

        JButton ok = new JButton("OK");
        ok.setFont(buttonFont);
        ok.setVisible(true);

        basePanel.setLayout(gridLayout);
        basePanel.setBackground(new Color(236, 234, 166));
        messagePanel.setLayout(gridLayout2);
        messagePanel.setBackground(new Color(236, 234, 166));

        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, "Message");
        title.setTitleFont(titleFont);
        title.setTitleJustification(TitledBorder.LEFT);
        basePanel.setBorder(title);
        basePanel.add(messagePanel);
        basePanel.add(ok);
        basePanel.setVisible(true);

        int xCoord = (int) ((DisplayAssembler.getScreenWidth()
                - basePanel.getPreferredSize().getWidth()) / 3);
        //Instead being divided by 2, make it divided by 3 to keep the message display in the center of the board without overlapping
        //System.out.println("ScreenWidth = " + (int)DisplayAssembler.getScreenWidth());
        //System.out.println("PreferredWidth = " + (int)basePanel.getPreferredSize().getWidth());
        //System.out.println("xCoord = " + xCoord);

        int yCoord = (int) (DisplayAssembler.getScreenHeight()
                - basePanel.getPreferredSize().getHeight()) / 2;

        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(this, basePanel, xCoord, yCoord);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
            }

        });
        popup.show();
        NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);

    }

    // Overloaded, not using processText method, show generic messages as input
    public MessagePopUp(String message, String message2, int fontSize) {

        boolean isTwoLine = true;
        JPanel basePanel = new JPanel();
        JPanel messagePanel = new JPanel();
        if (message2 == "") {
            isTwoLine = false;
        }

        int hGap = 10, vGap = 10;
        GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
        GridLayout gridLayout2 = new GridLayout(1, 1, 0, 0);

        basePanel.setLayout(null);
        messagePanel.setLayout(null);

        Font labelFont = new Font("arial", Font.PLAIN, fontSize);
        Font buttonFont = new Font("arial", Font.PLAIN, 18);
        Font titleFont = new Font("arial", Font.PLAIN, 14);

        JLabel message_ = new JLabel(message);
        message_.setFont(labelFont);
        message_.setVerticalAlignment(JLabel.CENTER);
        messagePanel.add(message_);

        if (isTwoLine) {
            gridLayout2.setRows(2);
            JLabel message_2 = new JLabel(message2);
            message_2.setFont(labelFont);
            message_2.setVerticalAlignment(JLabel.CENTER);
            messagePanel.add(message_2);
        }

        JButton ok = new JButton("OK");
        ok.setFont(buttonFont);
        ok.setVisible(true);

        basePanel.setLayout(gridLayout);
        basePanel.setBackground(new Color(236, 234, 166));
        messagePanel.setLayout(gridLayout2);
        messagePanel.setBackground(new Color(236, 234, 166));

        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, "Notice");
        title.setTitleFont(titleFont);
        title.setTitleJustification(TitledBorder.LEFT);
        basePanel.setBorder(title);
        basePanel.add(messagePanel);
        basePanel.add(ok);
        basePanel.setVisible(true);

        int xCoord = (int) ((DisplayAssembler.getScreenWidth()
                - basePanel.getPreferredSize().getWidth()) / 3);
        //Instead being divided by 2, make it divided by 3 to keep the message display in the center of the board without overlapping
        //System.out.println("ScreenWidth = " + (int)DisplayAssembler.getScreenWidth());
        //System.out.println("PreferredWidth = " + (int)basePanel.getPreferredSize().getWidth());
        //System.out.println("xCoord = " + xCoord);

        int yCoord = (int) (DisplayAssembler.getScreenHeight()
                - basePanel.getPreferredSize().getHeight()) / 3;

        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(this, basePanel, xCoord, yCoord);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
            }

        });
        popup.show();
        NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);

    }

    // Overloaded constructor used to display card messages
    public MessagePopUp(Card c, Player p, ArrayList<Player> players, String spaceName) {
        final Card card = c;
        final Player player = p;
        final ArrayList<Player> playerList = players;

        Vector<String> lines = processText(card.getMessage());
        int numOfLabels = lines.size();

        JPanel basePanel = new JPanel();
        JPanel messagePanel = new JPanel();
        JLabel labels[] = new JLabel[numOfLabels];

        int hGap = 10, vGap = 10;
        GridLayout gridLayout = new GridLayout(8, 1, hGap, vGap);
        GridLayout gridLayout2 = new GridLayout(numOfLabels, 1, 0, 0);

        basePanel.setLayout(null);
        messagePanel.setLayout(null);

        Font labelFont = new Font("arial", Font.PLAIN, 20);
        Font buttonFont = new Font("arial", Font.PLAIN, 18);
        Font titleFont = new Font("arial", Font.PLAIN, 22);

        for (int i = 0; i < numOfLabels; ++i) {
            labels[i] = new JLabel(lines.elementAt(i));
            labels[i].setFont(labelFont);
            messagePanel.add(labels[i]);
        }

        JButton ok = new JButton("OK");
        ok.setFont(buttonFont);
        ok.setVisible(true);
        basePanel.setLayout(gridLayout);
        basePanel.setBackground(new Color(33, 212, 219));
        messagePanel.setLayout(gridLayout2);
        messagePanel.setBackground(new Color(33, 212, 219));

        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, spaceName);
        title.setTitleFont(titleFont);
        title.setTitleJustification(TitledBorder.LEFT);
        basePanel.setBorder(title);
        basePanel.add(new JLabel());
        basePanel.add(new JLabel());
        basePanel.add(new JLabel());
        basePanel.add(messagePanel);
        basePanel.add(new JLabel());
        basePanel.add(new JLabel());
        basePanel.add(new JLabel());
        basePanel.add(ok);
        basePanel.setVisible(true);

        int xCoord = (int) ((DisplayAssembler.getScreenWidth()
                - basePanel.getPreferredSize().getWidth()) / 3);
        //Instead being divided by 2, make it divided by 3 to keep the message display in the center of the board without overlapping
        //System.out.println("ScreenWidth = " + (int)DisplayAssembler.getScreenWidth());
        //System.out.println("PreferredWidth = " + (int)basePanel.getPreferredSize().getWidth());
        //System.out.println("xCoord = " + xCoord);

        int yCoord = (int) (DisplayAssembler.getScreenHeight()
                - basePanel.getPreferredSize().getHeight()) / 2;

        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(this, basePanel, xCoord, yCoord);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
                card.landOn(player, playerList);
            }

        });

        popup.show();
        NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
    }

    // Overloaded constructor used to display card messages with options
    public MessagePopUp(Card c, Player p, ArrayList<Player> players, String spaceName, String options[]) {
        final Card card = c;
        final Player player = p;
        final ArrayList<Player> playerList = players;

        Vector<String> lines = processText(card.getMessage());
        int numOfLabels = lines.size();
        JLabel labels[] = new JLabel[numOfLabels];

        JPanel basePanel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        //Create layouts and buttons
        int hGap = 10, vGap = 10;
        GridLayout baseGridLayout = new GridLayout(3, 1, hGap, vGap);
        GridLayout gridLayout1 = new GridLayout(numOfLabels, 1, 0, 0);
        GridLayout gridLayout2 = new GridLayout(1, 1, 0, 0);
        GridLayout gridLayout3 = new GridLayout(2, 3, 0, 0);

        basePanel.setLayout(null);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);
        Font labelFont = new Font("arial", Font.PLAIN, 20);
        Font buttonFont = new Font("arial", Font.PLAIN, 18);
        Font titleFont = new Font("arial", Font.PLAIN, 22);

        for (int i = 0; i < numOfLabels; ++i) {
            labels[i] = new JLabel(lines.elementAt(i));
            labels[i].setFont(labelFont);
            panel1.add(labels[i]);
        }

        panel1.setLayout(gridLayout1);
        panel1.setBackground(new Color(33, 212, 219));

        JSlider taxAmount = new JSlider(JSlider.HORIZONTAL, TAX_MIN, TAX_MAX, TAX_INIT);
        taxAmount.addChangeListener(this);
        taxAmount.setMajorTickSpacing(500);
        taxAmount.setMinorTickSpacing(50);
        taxAmount.setPaintTicks(true);
        taxAmount.setPaintLabels(true);
        taxAmount.setSnapToTicks(true);
        taxAmount.setBackground(new Color(33, 212, 219));

        panel2.add(taxAmount);
        panel2.setLayout(gridLayout2);
        panel2.setBackground(new Color(33, 212, 219));

        JButton yes = new JButton(options[0]);
        yes.setFont(buttonFont);
        yes.setVisible(true);

        JButton no = new JButton(options[1]);
        no.setFont(buttonFont);
        no.setVisible(true);

        amountLabel_ = new JLabel();
        amountLabel_.setText("" + TAX_INIT);
        amountLabel_.setFont(new Font("arial", Font.PLAIN, 30));
        amountLabel_.setHorizontalAlignment(JLabel.CENTER);

        panel3.add(new JLabel());
        panel3.add(new JLabel());
        panel3.add(new JLabel());
        panel3.add(yes);
        panel3.add(amountLabel_);
        panel3.add(no);
        panel3.setLayout(gridLayout3);
        panel3.setBackground(new Color(33, 212, 219));

        basePanel.setLayout(baseGridLayout);
        basePanel.setBackground(new Color(33, 212, 219));

        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, spaceName);
        title.setTitleFont(titleFont);
        title.setTitleJustification(TitledBorder.LEFT);
        basePanel.setBorder(title);
        basePanel.add(panel1);
        basePanel.add(panel2);
        basePanel.add(panel3);
        basePanel.setVisible(true);

        int xCoord = (int) (DisplayAssembler.getScreenWidth()
                - basePanel.getPreferredSize().getWidth()) / 3;
        int yCoord = (int) (DisplayAssembler.getScreenHeight()
                - basePanel.getPreferredSize().getHeight()) / 2;

        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.TAX_AI_AMOUNT, taxAmount());
                
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
                card.setAmount(Integer.parseInt(taxAmount()));
                card.landOn(player, playerList);
            }

            public String taxAmount() {
                return amountLabel_.getText();
            }
        });
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.TAX_AI_AMOUNT, "0");
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);

            }
        });

        popup.show();
        NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
    }

    public MessagePopUp(String message, ArrayList<Player> players, String notificationName) {

        final ArrayList<Player> playerList = players;

        Vector<String> lines = processText(message);
        int numOfLabels = lines.size();
        JLabel labels[] = new JLabel[numOfLabels];

        JPanel basePanel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        Font labelFont = new Font("arial", Font.PLAIN, 26);
        Font buttonFont = new Font("arial", Font.PLAIN, 30);
        Font titleFont = new Font("arial", Font.PLAIN, 24);

        //Create layouts and buttons
        int hGap = 10, vGap = 10;
        GridLayout baseGridLayout = new GridLayout(3, 1, hGap, vGap);
        GridLayout gridLayout1 = new GridLayout(numOfLabels, 1, 0, 0);
        GridLayout gridLayout2 = new GridLayout(2, 2, hGap, vGap);
        GridLayout gridLayout3 = new GridLayout(2, 1, hGap, vGap);

        basePanel.setLayout(null);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);

        for (int i = 0; i < numOfLabels; ++i) {
            labels[i] = new JLabel(lines.elementAt(i));
            labels[i].setFont(labelFont);
            panel1.add(labels[i]);
        }

        panel1.setLayout(gridLayout1);
        panel1.setBackground(new Color(236, 234, 166));

        JLabel player1 = new JLabel("You");
        player1.setHorizontalAlignment(JLabel.CENTER);
        player1.setFont(buttonFont);
        player1.setVisible(true);
        JLabel player1Score = new JLabel();
        player1Score.setText("Score : " + (playerList.get(0).getCash()
                + playerList.get(0).getPropertiesValue_()));
        player1Score.setHorizontalAlignment(JLabel.CENTER);
        player1Score.setFont(buttonFont);
        player1Score.setVisible(true);

        JLabel player2 = new JLabel("The Other Player");
        player2.setHorizontalAlignment(JLabel.CENTER);
        player2.setFont(buttonFont);
        player2.setVisible(true);
        JLabel player2Score = new JLabel();
        if (playerList.size() > 1) {
            player2Score.setText("Score : " + (playerList.get(1).getCash()
                    + playerList.get(1).getPropertiesValue_()));
            player2Score.setHorizontalAlignment(JLabel.CENTER);
            player2Score.setFont(buttonFont);
            player2Score.setVisible(true);
        } else {
            player2Score.setText("Score : negative");
        }

        JButton next = new JButton("Next");
        next.setFont(buttonFont);
        next.setVisible(true);

        panel2.add(player1);
        panel2.add(player2);
        panel2.add(player1Score);
        panel2.add(player2Score);

        panel2.setLayout(gridLayout2);
        panel2.setBackground(new Color(236, 234, 166));

        panel3.add(new JLabel());
        panel3.add(next);
        panel3.setLayout(gridLayout3);
        panel3.setBackground(new Color(236, 234, 166));

        basePanel.setLayout(baseGridLayout);
        basePanel.setBackground(new Color(236, 234, 166));

        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, notificationName);
        title.setTitleFont(titleFont);
        title.setTitleJustification(TitledBorder.LEFT);
        basePanel.setBorder(title);
        basePanel.add(panel1);
        basePanel.add(panel2);

        basePanel.setVisible(true);

        int xCoord = (int) (DisplayAssembler.getScreenWidth()
                - basePanel.getPreferredSize().getWidth()) / 3;
        int yCoord = (int) (DisplayAssembler.getScreenHeight()
                - basePanel.getPreferredSize().getHeight()) / 2;

        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);
        double player1Payment = playerList.get(0).getCash() * 0.08;
        double player2Payment = playerList.get(1).getCash() * 0.08;

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.hide();
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
                
            }
        });

        popup.show();
        NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            String amount = "" + (int) source.getValue();
            amountLabel_.setText(amount);

        }
    }

    // Used to parse the message into appropriate-length strings
    private Vector<String> processText(String message) {

        int maxpos = 45;

        //Create a new Vector to hold the trimmed words
        Vector<String> trimmedWords = new Vector<String>(30);
        Vector<String> lines = new Vector<String>(5);

        //Use the StringTokenizer class to trim the whitespace from the words
        StringTokenizer words = new StringTokenizer(message);
        while (words.hasMoreTokens()) {
            trimmedWords.addElement(words.nextToken());
        }

        //Iterate through the Vector, printing an error message if a word is reached
        // that is longer than <maxpos> and storing a count of how many characters
        // have been printed on a line
        int count = 0;
        String temp = null;
        for (int i = 0; i < trimmedWords.size(); i = i + 1) {
            String currentWord = trimmedWords.elementAt(i);
            if (currentWord.length() > maxpos) {
                System.out.print("Error: word found longer than " + maxpos + " characters. Exiting.");
                return null;
            }
            //At the beginning of a new line, so simply print the word
            if (count == 0) {
                temp = currentWord;
                count = currentWord.length();
            } //The next word is too long for the current line, so we must
            // print the word on a new line
            else if ((count + currentWord.length() + 1) > maxpos) {
                lines.add(temp);
                temp = currentWord;
                count = currentWord.length();
            } //There is space on the current line for the next word
            else {
                temp += " " + currentWord;
                count = count + currentWord.length() + 1;
            }
        }
        lines.add(temp);
        return lines;
    }

}
