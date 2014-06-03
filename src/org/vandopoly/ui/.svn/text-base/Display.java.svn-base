/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;

import org.vandopoly.model.Space;


/*
 * Main JFrame from which Vandopoly is built into
 * 
 * @author Matt Gioia
 */

public class Display extends JFrame {
	
	Dimension screen_ = Toolkit.getDefaultToolkit().getScreenSize();
	int width_ = screen_.height - 150;
	int height_ = screen_.height - 150;
	int scaleWidth_ = 12, scaleHeight_ = 7;
	SpacePanel[][] spaces_ = new SpacePanel[40][2];
	int spacePos = 0;
	int spacesAcross_ = 9;
	int sizeAcross_ = (int)(height_ / scaleWidth_) * spacesAcross_;
	double spaceScale_ = .85;
	int boxSize_ = (int)(width_ / scaleWidth_) * 2 - scaleHeight_;
	int pos_ = boxSize_ + scaleHeight_;
	int spacePos_=0;
	int spaceWidth_ = (int)(height_ / scaleWidth_);
	int TopLeftGo_ = pos_ + sizeAcross_;
	int RightEdge_ = pos_ + sizeAcross_ + boxSize_;
	HashMap<String, SpacePanel> spaceMap_ = null;
	
	static final long serialVersionUID = 1;
	
	// Constructor effectively creates JFrame
	public Display () {
		this.setSize(screen_.width, screen_.height);
		this.setTitle("Vandopoly");
		this.setUndecorated(true);

		ToolTipManager.sharedInstance().setInitialDelay(0); 
		
		JDesktopPane board_ = new JDesktopPane();
		board_.setBackground(Color.black);
		board_.setSize(screen_.width, screen_.height);
		board_.setLayout(null);
		
		// Set the default close operation for the window, or else the
		// program won't exit when clicking close button
		// (The default is HIDE_ON_CLOSE, which just makes the window
		// invisible, and thus doesn't exit the app)
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// Setup the DisplayAssembler
		DisplayAssembler.getInstance().setDesktopPane(board_);
		DisplayAssembler.getInstance().setBoxSize(boxSize_);
		DisplayAssembler.getInstance().setRightEdge(RightEdge_);
		DisplayAssembler.getInstance().setSpaceWidth(spaceWidth_);
		DisplayAssembler.getInstance().setTopLeftGo(TopLeftGo_);
		
		this.add(board_);
		this.setVisible(true);
		board_.setVisible(true);
	}
	
	// show the board
	public void showBoard(Space[] board) {
		int start;
		
		String boardpics[] = new String[41];
		
		boardpics[0] = "go";
		boardpics[1] = "dyerhall";
		boardpics[2] = "communitychestbot";
		boardpics[3] = "mimshall";
		boardpics[4] = "paytuition";
		boardpics[5] = "vandyvanreverse";
		boardpics[6] = "tolmanhall";
		boardpics[7] = "chancebot";
		boardpics[8] = "colehall";
		boardpics[9] = "mcgillhall";
		boardpics[10] = "probationvisiting";
		boardpics[11] = "furmanhall";
		boardpics[12] = "cogenerationplant";
		boardpics[13] = "wilsonhall";
		boardpics[14] = "buttrickhall";
		boardpics[15] = "vandyvanlong";
		boardpics[16] = "lewishouse";
		boardpics[17] = "communitychestleft";
		boardpics[18] = "morganhouse";
		boardpics[19] = "chaffinplace";
		boardpics[20] = "scholorshipfund";
		boardpics[21] = "kirklandhall";
		boardpics[22] = "chancebot";
		boardpics[23] = "wyattcenter";
		boardpics[24] = "featheringillhall";
		boardpics[25] = "vandyvannormal";
		boardpics[26] = "sarattstudentcenter";
		boardpics[27] = "studentlifecenter";
		boardpics[28] = "biodieselinitiative";
		boardpics[29] = "ingramcenter";
		boardpics[30] = "goonacademicprob";
		boardpics[31] = "murrayhouse";
		boardpics[32] = "stambaughhouse";
		boardpics[33] = "communitychestright";
		boardpics[34] = "hankingramhouse";
		boardpics[35] = "vandyvanexpress";
		boardpics[36] = "chanceright";
		boardpics[37] = "mcgugincenter";
		boardpics[38] = "parkingticket";
		boardpics[39] = "commonscenter";
		
		int bp = 0;
		Color c = new Color(0, 0, 0);
		
		// bottom right corner
		addLabel(board[bp++], false, pos_ + sizeAcross_, pos_ + sizeAcross_, boxSize_, boxSize_, c, true, boardpics[bp-1]);

		// prop 7
		c = new Color(97, 73, 121);
		start = 8;
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, c, true, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, new Color(0, 0, 0), false, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, c, true, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, new Color(0, 0, 0), false, true);
		
		// prop 8
		c = new Color(85, 141, 215);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, new Color(0, 0, 0), false, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, c, true, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, new Color(0, 0, 0), false, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, c, true, true);
		addSmallOp(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start--), 0, c, true, true);
		
		// bottom left corner
		addLabel(board[bp++], false, scaleHeight_, pos_ + sizeAcross_, boxSize_, boxSize_, c, true, boardpics[bp-1]);
		
		// prop 1
		start = 8;
		c = new Color(148, 55, 53);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), new Color(0, 0, 0), false, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		
		// prop 2
		c = new Color(228, 108, 12);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), new Color(0, 0, 0), false, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), new Color(0, 0, 0), false, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		addSmall(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start--), c, true, false);
		
		// top left corner
		addLabel(board[bp++], false, scaleHeight_, scaleHeight_, boxSize_, boxSize_, c, true, boardpics[bp-1]);
		
		// prop 3
		start = 0;
		c = new Color(255, 0, 0);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		
		// prop 4
		c = new Color(255, 255, 0);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmall(boardpics[bp], board[bp++], pos_ + (spaceWidth_ * start++), 0, c, true, true);
		
		// top right corner
		addLabel(board[bp++], false, pos_ + sizeAcross_, scaleHeight_, boxSize_, boxSize_, c, true, boardpics[bp-1]);
		
		// prop 5
		c = new Color(79, 99, 40);
		start = 0;
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		
		// prop 6
		c = new Color(39, 63, 97);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(boardpics[bp], board[bp++], 0, pos_ + (spaceWidth_ * start++), c, true, false);
		
		// set center of boards
		JLabel label = new JLabel();
		label.setOpaque(true);
		
		try {
			label.setIcon(new ImageIcon(scaleImage(new FileInputStream("images/center.png"),
					sizeAcross_, sizeAcross_)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		label.setSize(new Dimension(sizeAcross_, sizeAcross_));
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		DisplayAssembler.getInstance().addComponent(label, new Point(pos_, pos_), 
				JLayeredPane.FRAME_CONTENT_LAYER);
	}
	
	// adds a smaller rectangle to the board.
	// x, y specify the point to be placed on the board. c is the color of the property.
	// isProp specifies if it is a property or not, isVert specifies weather the piece goes vertically or
	// horizontally
	void addSmall(String imageName, Space space, int x, int y, Color c, boolean isProp, boolean isVert) {
		// set the larger part of the piece
		int panelWidth;
		
		if (isProp)
			panelWidth = (int)(spaceScale_ * boxSize_);
		else
			panelWidth = boxSize_;
		
		int panelHeight;
		
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
			y += scaleHeight_;
		}
		else {
			panelHeight = height_ / scaleWidth_;
			x += scaleHeight_;
		}
		
		addLabel(space, isProp, x, y, panelWidth, panelHeight,c, true, imageName);
		
		// set the smaller colored part of the piece
		if (isProp) {
			panelWidth = boxSize_ - Math.max(panelWidth, panelHeight);
			if (isVert) {
				panelHeight = panelWidth;
				panelWidth = (int)height_ / scaleWidth_;
			}
	
			if (isVert)
				addLabel(space, isProp, x, y + (int)(spaceScale_ * boxSize_), panelWidth,
						panelHeight, c, false, imageName);
			else
				addLabel(space, isProp, x + (int)(spaceScale_ * boxSize_), y, panelWidth, 
						panelHeight, c, false, imageName);	
		}
	}
	
	// same as addSmall, but used for bottom and right side of board
	void addSmallOp(String imageName, Space space, int x, int y, Color c, boolean isProp, boolean isVert) {
		// set the larger part of the piece	
		int panelWidth;

		if (isProp)
			panelWidth = (int)(spaceScale_ * boxSize_);
		else
			panelWidth = boxSize_;
		
		int panelHeight;
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
		}
		else 
			panelHeight = height_ / scaleWidth_;

		if(isVert)
			y += pos_ + sizeAcross_;
		else
			x += pos_ + sizeAcross_;

		// set the smaller colored part of the piece
		int panelWidthS = panelWidth, panelHeightS = panelHeight;
		if (isProp) {
			panelWidthS = boxSize_ - Math.max(panelWidth, panelHeight);
			if (isVert) {
				panelHeightS = panelWidthS;
				panelWidthS = height_ / scaleWidth_;
			}
			addLabel(space, isProp, x, y, panelWidthS, panelHeightS, c, false, imageName);
		} 
		else {
			panelWidthS = 0;
			panelHeightS = 0;
		}
		
		// set the larger part
		if (isVert)
			addLabel(space, isProp, x, y + panelHeightS, panelWidth, panelHeight, c, true, imageName);
		else
			addLabel(space, isProp, x + panelWidthS, y, panelWidth, panelHeight, c, true, imageName);	
	}

	// make a space and add it to the board
	void addLabel(Space space, boolean isProp, int x, int y, int width, int height, 
			Color c, boolean useTex, String imageName) {
		new SpacePanel(spacePos_++, space, isProp, x, y, width, height, c, useTex, imageName);
	}
	
	// return point of the center of given space
	public Point getCenter(int spaceNum) {
		return spaces_[spaceNum][0].getCenter();
	}
	
	// resize an image to the specified width and height and return a BufferedImage
	public static BufferedImage scaleImage(InputStream p_image, int p_width, int p_height) {
	   InputStream imageStream = new BufferedInputStream(p_image);
	   Image image = null;
		try {
			image = (Image) ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	 
        // Draw the scaled image
        BufferedImage thumbImage = new BufferedImage(p_width, 
          p_height, BufferedImage.TRANSLUCENT);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, p_width, p_height, null);
 
        return thumbImage;        
    }
}