package o1.adventure

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

  
/** A `Player` object represents a player character controlled by the real-life user of the program. 
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val inventoryy = Map[String, Item]()
  //private var beercount=0;
  
  def drop(itemName: String): String = {
    if(this.has(itemName))
    {
      currentLocation.addItem(inventoryy(itemName))
      inventoryy-=itemName
     // var ok=inventoryy.remove(itemName)
     // currentLocation.addItem(ok)
      "You drop the " + itemName + "."
    }
    else "You don't have that!"
  }
  
  def examine(itemName: String): String = {
    if(this.has(itemName))
    {
      "You look closely at the " + itemName +".\n"+inventoryy(itemName).description
    }
    else "If you want to examine something, you need to pick it up first."
  }
  
  def get(itemName: String): String = {
    if(currentLocation.contains(itemName))
    {
      //inventoryy+=itemName->currentLocation
      val itemm=currentLocation.removeItem(itemName)
      inventoryy+=itemName->itemm.get
      "You pick up the "+ itemName +"."
    }
    else "There is no " + itemName + " here to pick up."

  }
  
  def has(itemName: String): Boolean = {
    inventoryy.contains(itemName)

  }
  
  def inventory(): String = {
    if(!inventoryy.isEmpty)
      "You are carrying:\n" + inventoryy.keys.mkString("\n")
    else
       "You are empty-handed."
  }
  
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  
  /** Returns the current location of the player. */
  def location = this.currentLocation
  

  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }

  
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def olut() = {  
    "Juot yhden (1) oluen. Ota toinenkin."
  }
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }
  
  def help() = {
    "Tässä pelissä voit juoda oluita 'olut' -komennolla. Älä kuitenkaan juo liikaa."
    
  }

  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   


}


