package o1.adventure


/** The class `Adventure` represents text adventure games. An adventure consists of a player and 
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very 
  * specific adventure game that involves a small trip through a twisted forest. All newly created 
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure 
  * games, you will need to modify or replace the source code of this class. */
class Adventure {

  /** The title of the adventure game. */
  val title = "Otaniemen negatiivinen seikkailu JES"
    
  private val middle      = new Area("Päälafka", "Kävelet ihmispaljoudessa päälafkalla. Tsekkailet puhelimestasi mihin luokkaan pitikään mennä\nOpiskelijat hälisevät.")
  private val northForest = new Area("SMT-huudit", "Olet jossain SMT:n huudeilla kävelemässä.\nNäet autoja, joiden valot ovat jääneet päälle ja päätät ilmoittaa tästä kuvan kera teekkariyhteisöön.\nLoskasade masentaa sinua.")
  private val southForest = new Area("Alepa", "Olet alepalla hakemassa lihistä.\nPitkä kassajono ei miellytä.")
  private val clearing    = new Area("Smökki", "Smökissä on bileet. Haalareihin pukeutuneita kännisiä miehiä kaikkialla.")
  private val tangle      = new Area("Rantsu", "Palju muistuttaa nakkikeittoa. Se on ahdettu täyteen alastomia miehiä.\n Yksi isoluisempi nainen on uskaltautunnut mukaan")
  private val home        = new Area("Koti", "Kotona pelaamassa CS hyvän mielen porukan kanssa.\n Oispa lihis")
  private val destination = home    

       middle.setNeighbors(Vector("north" -> northForest, "east" -> tangle, "south" -> southForest, "west" -> clearing   ))
  northForest.setNeighbors(Vector(                        "east" -> tangle, "south" -> middle,      "west" -> clearing   ))
  southForest.setNeighbors(Vector("north" -> middle,      "east" -> tangle, "south" -> southForest, "west" -> clearing   ))
     clearing.setNeighbors(Vector("north" -> northForest, "east" -> middle, "south" -> southForest, "west" -> northForest))
       tangle.setNeighbors(Vector("north" -> northForest, "east" -> home,   "south" -> southForest, "west" -> northForest))
         home.setNeighbors(Vector(                                                                  "west" -> tangle     ))

  // TODO: place these two items in clearing and southForest, respectively
  private val y = new Item("apa", "Kunnon hyvä olut. Olet servun keisari tämän nautittuasi.")
  private val z = new Item("ipa", "Kunnon hyvä olut. Olet servun keisari tämän nautittuasi.")
  private val f = new Item("nipa", "Kunnon hyvä olut. Olet servun keisari tämän nautittuasi.")
  private val x = new Item("teekkarilakki", "Hmm... Olen varma asiasta... Tämä teekkarilakkipitää mennä tiputtamaan Smökkiin.")
  clearing.addItem(y)
  southForest.addItem(z)
  northForest.addItem(f)
  tangle.addItem(x)

  /** The character that the player controls in the game. */
  val player = new Player(middle)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 16 


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.destination && this.player.has(y.name) && this.clearing.contains(x.name)

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Perus perjantai niemessä\nLähdetäänpä tsekkailemaan meininkejä!"

    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      "Päivän hyvä työ tehty ja teekkarilakki on löytänyt uuden omistajan. Nyt voit alkaa nauttimaan APA:a ja pelata CS. Jeee!"
    else if (this.turnCount == this.timeLimit)
      "Harmin Lista Hävisit Pelin Ja Ystäväsi Menivät Jo Nukkumaan. Et Saanut PeliSeuraa :(\nGame over!"
    else  // game over due to player quitting
      "Paras valinta" 
  }

  
  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual 
    * report of what happened, or an error message if the command was unknown. In the latter 
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) { 
      this.turnCount += 1 
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }
  
  
}

