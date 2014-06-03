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
 ***************************************************************************
 */
package org.vandopoly.model;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * The CardTypeOutOfJail class represents a "Chance" or "Community Chest" card
 * that is a "Get Out of Jail Free" card
 *
 * @author Allie Mazzia
 */
public class CardTypeOutOfJail extends Card implements Serializable {

    final static long serialVersionUID = 202;

    public CardTypeOutOfJail() {
        message_ = "Get off Academic Probation FREE!";
    }

    public void landOn(Player p, ArrayList<Player> players) {
        p.gainedGetOutOfJail();
    }

    public boolean isWithQuestion() {
        return false;
    }

}
