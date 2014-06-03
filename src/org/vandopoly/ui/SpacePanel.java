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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Space;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;

/*
 * Space represents the spaces on the board and takes care of positioning,
 * creating, and performing actions for them.
 * 
 * @author Matt Gioia
 */
public class SpacePanel extends JPanel {

    private static final long serialVersionUID = 81;

    int x_;
    int y_;
    int width_;
    int height_;
    int position_;
    Color c_;
    boolean useTex_;
    String spaceName_;
    Space spaceObj_;
    boolean isProp_;
    ArrayList<String> onSpace_;
    JLabel bg_pic, houseIcon[];
    String pic_;

    public SpacePanel(int pos, Space space, boolean isProp, int x, int y, int width, int height, Color c,
            boolean useTex, String imageName) {
        houseIcon = new JLabel[6];
        x_ = x;
        y_ = y;
        width_ = width;
        height_ = height;
        c_ = c;
        useTex_ = useTex;
        spaceName_ = space.getName();
        isProp_ = isProp;
        position_ = pos;
        onSpace_ = new ArrayList<String>();
        spaceObj_ = space;
        pic_ = imageName;
        houseIcon[0] = new JLabel("houseIconLevel0");
        houseIcon[1] = new JLabel("houseIconLevel1");
        houseIcon[2] = new JLabel("houseIconLevel2");
        houseIcon[3] = new JLabel("houseIconLevel3");
        houseIcon[4] = new JLabel("houseIconLevel4");
        houseIcon[5] = new JLabel("houseIconLevel5");

        bg_pic = addSpace();
        updateStatus(spaceObj_);
        NotificationManager.getInstance().addObserver(Notification.CHANGED_OWNER, this, "updateStatus");
        NotificationManager.getInstance().addObserver(Notification.REMOVE_OWNER, this, "removeFilling");
    }

    JLabel addSpace() {
        JLabel tmp = new JLabel();
        //tmp.setOpaque(true);

        if (useTex_) {
            try {
                if (pic_ == "boardTex") {
                    tmp.setIcon(new ImageIcon("images/Spaces/" + pic_ + ".png"));
                    tmp.setText(spaceObj_.getName());
                } else if (pic_ == "blankspace") {
                    tmp.setIcon(new ImageIcon("images/Spaces/blankspace.png"));
                } else {
                    tmp.setIcon(new ImageIcon(Display.scaleImage(new FileInputStream("images/Spaces/" + pic_ + ".png"), width_, height_)));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            tmp.setOpaque(true);
            tmp.setBackground(c_);
        }

        tmp.setSize(new Dimension(width_, height_));
        tmp.setBorder(BorderFactory.createLineBorder(Color.black));

        DisplayAssembler.getInstance().addComponent(tmp, new Point(x_, y_),
                JLayeredPane.FRAME_CONTENT_LAYER);

        //System.out.println("reached the stage of PAINTING SPACE");
        return tmp;
    }

    void addOnSpace(String name) {
        onSpace_.add(name);
    }

    String getSpaceName() {
        return spaceName_;
    }

    public void setSpaceObj(Space space) {
        spaceObj_ = space;
    }

    public void updateSpaceFilling(int level, int index) {

        //hardcoded part
        String imageName = (index == 0) ? "house_red" + level : "house_blue" + level;

        int sideLength = (width_ > height_) ? (int) width_ / 4 + level * 4 : (int) height_ / 4 + level * 4;
        int xCoord = (int) (width_ - sideLength) / 2;
        int yCoord = (int) (height_ - sideLength) / 2 + 5;
        if ((useTex_) && (imageName != null)) {
            try {
                houseIcon[level].setIcon(new ImageIcon(Display.scaleImage(new FileInputStream("images/houseIcon/" + imageName + ".png"),
                        sideLength, sideLength)));
                houseIcon[level].setBounds(xCoord, yCoord, sideLength, sideLength);
                //System.out.println("current level" + level);
                bg_pic.add(houseIcon[level]);
                houseIcon[level].setVisible(true);
                for (int i = 0; i < 6; i++) {
                    if (i != level) {
                        houseIcon[i].setVisible(false);
                        bg_pic.remove(houseIcon[i]);
                        //System.out.println("level " + i + "icon removed");
                    }
                }
                //System.out.println("FINISH SETUP HOUSE ICON");
            } catch (FileNotFoundException e) {
            }
        }
    }

    public void removeFilling(Object obj) {
        Space p = (Space) obj;
        if (spaceObj_.getName() == p.getName()) {
            spaceObj_ = p;
            if (spaceObj_ instanceof PropertySpace) {
                PropertySpace propSpace = (PropertySpace) obj;
                //System.out.println("inner reached");
                for (int i = 0; i < 6; i++) {
                    houseIcon[i].setIcon(null);
                    houseIcon[i].setVisible(false);
                    bg_pic.remove(houseIcon[i]);
                }
            }
            String status = "";
            status += spaceObj_;
            bg_pic.setToolTipText(status);
        }
    }

    // add level icon to board piece
    public void updateStatus(Object obj) {
        Space p = (Space) obj;
        if (spaceObj_.getName() == p.getName()) {
            spaceObj_ = p;

            String status = "";
            status += spaceObj_;
            bg_pic.setToolTipText(status);
            if (spaceObj_ instanceof PropertySpace) {
                PropertySpace propSpace = (PropertySpace) obj;
                //System.out.println("reached updateStatus");
                Player tempPlayer = propSpace.getOwner();
                if (tempPlayer == null) {

                } else {
                    int ownerIndex = propSpace.getOwner().getIndex();

                    int level = propSpace.getState().getLevel();
                    if ((propSpace.getType().equals("Purple")) || (propSpace.getType().equals("Light Blue"))) {
                        level = level + 1;
                    }
                    if ((propSpace.getType().equals("Brown")) || (propSpace.getType().equals("Orange"))) {
                        level = level + 2;
                    }
                    if ((propSpace.getType().equals("Red")) || (propSpace.getType().equals("Yellow"))) {
                        level = level + 3;
                    }
                    updateSpaceFilling(level, ownerIndex);
                }
                //TODO
                //
                //System.out.println("property space: " + status);
            } else {
                // System.out.println("normal space: " + status);
            }
        }
    }

    public Point getCenter() {
        return new Point((int) (x_ + width_) / 2, (int) (y_ + height_) / 2);
    }
}
