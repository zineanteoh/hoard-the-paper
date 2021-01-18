
package hoardthepaper;

public enum ID {
    Player(),   // Movable Players
    Bot(),      // Movable Bots
    Paper(),    // Movable items
    Shelf(),    // Stores movable objects
    Wall(),     // Decorations 
    Cashier(),  // Decorations
    Road(),     // Decorations
    Entrance(), // Decorations (block users from taking non-purchased items out)
    Car()       // Decorations (allow users to store paper)
}
