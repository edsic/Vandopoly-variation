package org.vandopoly.ui;

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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ListCellRenderer;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;

/*
 * PlayerPanel will display player names in a JTabbedPane. It will also display
 * player-specific information, like amount of cash and properties owned.
 * 
 * @author Allie Mazzia
 */
public class PlayerPanel extends JPanel {

    private static final long serialVersionUID = 80;

    Dimension screen_ = Toolkit.getDefaultToolkit().getScreenSize();
    double width_ = screen_.getWidth() - DisplayAssembler.getRightEdge();
    int height_ = screen_.height;
    //private JTabbedPane infoPanel_;
    private JPanel infoPanel_, panel_[];
    private JLabel playerNames_[], cashAmount_[], assetsValue_[], cashLabel_[],
            assetsLabel_[], propertiesValueLabel_[], propertiesValue_[], properties_[];
    private JScrollPane scrollPane_[];
    private JList list_[];
    private JLabel getOffAcProFree_[];
    Color background_;
    ImageIcon enabled_, disabled_, getOffAcProIcon_;

    private double panelScaleX_ = .88, coordScaleX_ = .1;
    private double panelScaleY_ = .64, coordScaleY_ = .18;
    ArrayList<Player> players_;

    Font propertyFont_ = new Font("arial", Font.PLAIN, 16);

    public PlayerPanel(ArrayList<Player> players) {

        players_ = players;
        background_ = new Color(239, 227, 160);
        enabled_ = new ImageIcon("images/Star.png");
        disabled_ = new ImageIcon("images/disabled.png");
        getOffAcProIcon_ = new ImageIcon("images/offprobation.png");

        Font nameFont = new Font("arial", Font.PLAIN, 18);

        int paneX = 0, paneY = 0;

        this.setSize((int) (panelScaleX_ * width_), (int) (panelScaleY_ * height_));
        this.setLayout(null);

        //infoPanel_ = new JTabbedPane();
        infoPanel_ = new JPanel();
        infoPanel_.setBounds(paneX, paneY, (int) (panelScaleX_ * width_), (int) (panelScaleY_ * height_));
        infoPanel_.setFont(nameFont);

        // Create and initialize arrays
        cashLabel_ = new JLabel[players.size()];
        cashAmount_ = new JLabel[players.size()];
        assetsLabel_ = new JLabel[players.size()];
        assetsValue_ = new JLabel[players.size()];
        propertiesValueLabel_ = new JLabel[players.size()];
        propertiesValue_ = new JLabel[players.size()];
        playerNames_ = new JLabel[players.size()];
        properties_ = new JLabel[players.size()];
        getOffAcProFree_ = new JLabel[players.size()];
        list_ = new JList[players.size()];

        scrollPane_ = new JScrollPane[players.size()];
        panel_ = new JPanel[players.size()];

        for (int i = 0; i < players.size(); i++) {
            cashAmount_[i] = new JLabel("");
            assetsValue_[i] = new JLabel("");
            propertiesValue_[i] = new JLabel("");
            propertiesValueLabel_[i] = new JLabel("");
            getOffAcProFree_[i] = new JLabel(getOffAcProIcon_);

            //list_ is the properties list
            list_[i] = new JList(players.get(i).getProperties().toArray());
            scrollPane_[i] = new JScrollPane(list_[i]);

            list_[i].setBackground(new Color(240, 240, 240));
            list_[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list_[i].setSelectionBackground(new Color(250, 250, 250));
            list_[i].setVisibleRowCount(16);//ToModify
            list_[i].setFont(propertyFont_);

            scrollPane_[i].setBackground(new Color(240, 240, 240));
            scrollPane_[i].setBounds(5 + i * (int) (panelScaleX_ * width_) / 2, 175, (int) (panelScaleX_ * width_) / 2 - 10, (int) (panelScaleY_ * height_) - 170);
            scrollPane_[i].setFont(propertyFont_);
        }

        // Create all panels
        //panel_[0] = createPanel(players_.get(0), cashAmount_[0], assetsValue_[0], getOffAcProFree_[0], scrollPane_[0], list_[0], 0);
        //infoPanel_.setIconAt(0, enabled_);
        //panel_[1] = createPanel(players_.get(1), cashAmount_[1], assetsValue_[1], getOffAcProFree_[1], scrollPane_[1], list_[1], (int) (panelScaleX_ * width_) / 2);
        //infoPanel_.setIconAt(1, disabled_);

        /*
         if (players_.size() > 2) {
         panel_[2] = createPanel(players_.get(2), cashAmount_[2], getOffAcProFree_[2], scrollPane_[2], list_[2]);
         infoPanel_.setIconAt(2, disabled_);
         if (players_.size() == 4) {
         panel_[3] = createPanel(players_.get(3), cashAmount_[3], getOffAcProFree_[3], scrollPane_[3], list_[3]);
         infoPanel_.setIconAt(3, disabled_);
         }
         }
         */
        Point location = new Point((int) (coordScaleX_ * width_) + DisplayAssembler.getRightEdge(), (int) (coordScaleY_ * height_));
        DisplayAssembler.getInstance().addComponent(this, location, JLayeredPane.PALETTE_LAYER);

        // The following line enables to use scrolling tabs.
        //infoPanel_.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        Font labelFont = new Font("arial", Font.PLAIN, 16);
        Font cashFont = new Font("arial", Font.PLAIN, 20);
        String[] cash_ = new String[players_.size()];
        String[] propertiesV_ = new String[players_.size()];

        for (int j = 0; j < players_.size(); j++) {
            cash_[j] = "" + players_.get(j).getCash();
            propertiesV_[j] = "" + players_.get(j).getPropertiesValue_();

            int coord = (int) (panelScaleX_ * width_) / players_.size();

            String name = players_.get(j).getName();
            playerNames_[j] = new JLabel("Player: " + name);
            playerNames_[j].setFont(nameFont);
            playerNames_[j].setOpaque(true);
            playerNames_[j].setBounds(j * coord + 10, 10, 150, 20);

            cashLabel_[j] = new JLabel("Cash: ");
            cashLabel_[j].setFont(labelFont);
            cashLabel_[j].setBounds(j * coord + 10, 30, 150, 20);

            cashAmount_[j].setText(cash_[j]);
            cashAmount_[j].setFont(cashFont);
            cashAmount_[j].setBounds(j * coord + 20, 50, 150, 20);

            getOffAcProFree_[j].setBounds((int) (panelScaleX_ * width_) - 130 + j * coord, 0, 80, 115);
            getOffAcProFree_[j].setEnabled(false);
            getOffAcProFree_[j].setVisible(false);

            propertiesValueLabel_[j] = new JLabel("Properities: ");
            propertiesValueLabel_[j].setFont(labelFont);
            propertiesValueLabel_[j].setBounds(j * coord + 10, 70, 150, 20);

            propertiesValue_[j].setText(propertiesV_[j]);
            propertiesValue_[j].setFont(cashFont);
            propertiesValue_[j].setBounds(j * coord + 20, 90, 150, 20);

            assetsLabel_[j] = new JLabel("Total: ");
            assetsLabel_[j].setFont(labelFont);
            assetsLabel_[j].setBounds(j * coord + 10, 110, 150, 20);

            int totalValue = Integer.parseInt(cash_[j]) + Integer.parseInt(propertiesV_[j]);
            assetsValue_[j].setText("" + totalValue);
            assetsValue_[j].setFont(cashFont);
            assetsValue_[j].setBounds(j * coord + 20, 130, 150, 20);

            properties_[j] = new JLabel("Owned Properties: ");
            properties_[j].setFont(labelFont);
            properties_[j].setBounds(j * coord + 10, 150, 150, 20);

            scrollPane_[j].setVisible(true);
            scrollPane_[j].setBounds(5 + j * (int) (panelScaleX_ * width_) / 2, 175, (int) (panelScaleX_ * width_) / 2 - 10, (int) (panelScaleY_ * height_) - 180);
                    
            list_[j].setVisible(true);

            this.add(cashLabel_[j]);
            this.add(cashAmount_[j]);
            this.add(playerNames_[j]);
            this.add(getOffAcProFree_[j]);
            this.add(propertiesValueLabel_[j]);
            this.add(propertiesValue_[j]);
            this.add(assetsLabel_[j]);
            this.add(assetsValue_[j]);
            this.add(properties_[j]);
            this.add(scrollPane_[j]);
            this.add(list_[j]);
        }

        this.add(infoPanel_);
        this.setVisible(true);

        playerNames_[1].setBackground(new Color(35, 35, 35));
        playerNames_[1].setForeground(new Color(255, 255, 255));

        NotificationManager.getInstance().addObserver(Notification.UPDATE_PROPERTIES, this, "updateProperties");
        NotificationManager.getInstance().addObserver(Notification.UPDATE_CASH, this, "updateCash");
        NotificationManager.getInstance().addObserver(Notification.END_TURN, this, "switchPanel");
        NotificationManager.getInstance().addObserver(Notification.USED_JAIL_CARD, this, "usedCard");
        NotificationManager.getInstance().addObserver(Notification.GAINED_JAIL_CARD, this, "gainedCard");
    }

    private JPanel createPanel(Player player, JLabel cashAmount, JLabel assetsValue, JLabel getOffAcProFree, JScrollPane scroll, JList list, int coord) {
        JPanel panel = new JPanel();
        Font labelFont = new Font("arial", Font.PLAIN, 12);
        Font cashFont = new Font("arial", Font.PLAIN, 12);

        String cash = "" + player.getCash();

        // Set up the JTabbedPane and its JPanels
        panel.setLayout(null);

        JLabel cashLabel_ = new JLabel("Total Cash: ");
        cashLabel_.setFont(labelFont);
        cashLabel_.setBounds(coord + 10, 0, 100, 20);

        cashAmount.setText(cash);
        cashAmount.setFont(cashFont);
        cashAmount.setBounds(coord + 20, 20, 100, 20);

        getOffAcProFree.setBounds((int) (panelScaleX_ * width_) - 130 + coord, 0, 80, 115);
        getOffAcProFree.setEnabled(false);
        getOffAcProFree.setVisible(false);

        JLabel properties_ = new JLabel("Properties: ");
        properties_.setFont(labelFont);
        properties_.setBounds(coord + 10, 80, 100, 20);

        scroll.setVisible(true);
        list.setVisible(true);
        UIManager.put("TabbedPane.selected", new Color(238, 238, 238));
        UIManager.put("TabbedPane.focus", new Color(238, 238, 238));

        panel.add(cashLabel_);
        panel.add(cashAmount);
        panel.add(getOffAcProFree);
        panel.add(properties_);
        panel.add(scroll);
        panel.add(list);

        //infoPanel_.addTab(player.getName(), panel);
        return panel;
    }

    // Called by the updateCash notification
    public void updateCash(final Object object) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Player player = (Player) object;
                    String cash = "" + player.getCash();
                    String propertiesV = "" + player.getPropertiesValue_();
                    cashAmount_[player.getIndex()].setText(cash);
                    propertiesValue_[player.getIndex()].setText(propertiesV);
                    int totalValue = Integer.parseInt(cash) + Integer.parseInt(propertiesV);
                    assetsValue_[player.getIndex()].setText("" + totalValue);

                } catch (ClassCastException e) {
                    System.err.println("Unexpected object passed to updateCash");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Called by the updateProperties notification
    public void updateProperties(final Object object) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Player player = (Player) object;
                    int i = player.getIndex();

                    list_[i].setListData(player.getPropertyArray());
                    list_[i].setVisibleRowCount(16);
                    list_[i].setFont(propertyFont_);
                    list_[i].setCellRenderer(new MyListCellThing());

                    scrollPane_[i].setViewportView(list_[i]);
                    scrollPane_[i].setBackground(new Color(240, 240, 240));
                    scrollPane_[i].setBounds(5 + i * (int) (panelScaleX_ * width_) / 2, 175, (int) (panelScaleX_ * width_) / 2 - 10, (int) (panelScaleY_ * height_) - 180);
                    scrollPane_[i].setVisible(true);
                    scrollPane_[i].setFont(propertyFont_);

                    //panel_[i].add(scrollPane_[i]);
                } catch (ClassCastException e) {
                    System.err.println("Unexpected object passed to updateCash");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void gainedCard(Object object) {
        try {
            Player player = (Player) object;
            int i = player.getIndex();

            getOffAcProFree_[i].setVisible(true);
            getOffAcProFree_[i].setEnabled(true);
        } catch (ClassCastException e) {
            System.err.println("Unexpected object passed to updateCard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usedCard(Object object) {
        try {
            Player player = (Player) object;
            int i = player.getIndex();

            getOffAcProFree_[i].setVisible(false);
        } catch (ClassCastException e) {
            System.err.println("Unexpected object passed to updateCard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove a player's panel
    public void removePanel(Object i) {
        infoPanel_.remove((Integer) i);
    }

    // Used to automatically switch to the player who is currently rolling
    public void switchPanel(final Object i) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Integer number = (Integer) i;
                    for (int j = 0; j < players_.size(); ++j) {
                        if (number == j) {
                            //infoPanel_.setSelectedIndex((int) number);
                            //infoPanel_.setIconAt(number, enabled_);
                            playerNames_[j].setBackground(new Color(35, 35, 35));
                            playerNames_[j].setForeground(new Color(255, 255, 255));
                        } else {
                            //infoPanel_.setIconAt(j, disabled_);
                            playerNames_[j].setBackground(new Color(240, 240, 240));
                            playerNames_[j].setForeground(new Color(0, 0, 0));
                        }
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class MyListCellThing extends JLabel implements ListCellRenderer {

        public MyListCellThing() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Assumes the stuff in the list has a pretty toString

            int color = Integer.parseInt(value.toString().substring(0, 1));
            setText(value.toString().substring(1));

            // based on the index you set the color.  This produces the every other effect.
            switch (color) {
                case 0:
                    setForeground(new Color(97, 73, 121));
                    break;
                case 1:
                    setForeground(new Color(85, 210, 117));
                    break;
                case 2:
                    setForeground(new Color(53, 53, 53));
                    break;
                case 3:
                    setForeground(new Color(228, 108, 12));
                    break;
                case 4:
                    setForeground(new Color(39, 63, 97));
                    break;
                case 5:
                    setForeground(new Color(148, 55, 53));
                    break;
                case 6:
                    setForeground(new Color(79, 99, 40));
                    break;
                case 7:
                    setForeground(new Color(255, 56, 243));
                    break;

            }

            return this;
        }
    }

}
