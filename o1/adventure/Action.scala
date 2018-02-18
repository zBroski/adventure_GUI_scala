package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  
  /** Causes the given player to take the action represented by this object, assuming 
    * that the command was understood. Returns a description of what happened as a result 
    * of the action (such as "You go west."). The description is returned in an `Option` 
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = {                             

    if (this.verb == "go") {
      Some(actor.go(this.modifiers))
    } else if (this.verb == "olut") {
      Some(actor.olut())
    } else if (this.verb == "get") {
      Some(actor.get(this.modifiers))
    } else if (this.verb == "examine") {
      Some(actor.examine(this.modifiers))
    } else if (this.verb == "drop") {
      Some(actor.drop(this.modifiers))
    } else if (this.verb == "inventory") {
      Some(actor.inventory())
    } else if (this.verb == "xyzzy") {
      Some("The grue tastes yummy.")      
    } else if (this.verb == "quit") {
      Some(actor.quit())
    } else {
      None
    }
    
  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}

