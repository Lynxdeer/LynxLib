# LynxLib
***
LynxLib will be a general library for all of my (Lynxdeer's) plugins. You're free to use it if you'd like. I'll include a compiled verison in the releases tab when it's ready. Until 1.20.3 comes out (for tickrates), I'm developing this for 1.19.4.
***
# Future Features
### **General Things I Copy + Paste every project**<br>
\> A `debug` method that takes any object and sends it to any player named "Lynxdeer" (Will be configurable later)<br>
\> A `tabComplete` method that takes a string and list of strings and gets which ones start with the first string. In other words, better tab completion.<br>
\> A `tabCompletePlayers` method that gets a string and all players' names and tab completes them.<br>
\> A `hitbox` method that checks if a Location object intersects with any bounding boxes of nearby entities <br>
\> A `hitboxMovementCheck` method that checks if a Location object intersects with any bounding boxes of nearby PLAYERS expanded based on that player's motion to solve the 20 tick problem<br>
\> A `timeFormat` method that formats seconds in hh:mm:ss.<br>
### **ItemBuilder**
\> Self-explanatory.<br>
### **Display Entity Utils**
\> Pivot points<br>
\> Hitboxes<br>
\> Gravity<br>
\> Method to move to a location<br>
\> Easing<br>
### **Holograms**
\> Using both Text Displays and Armor Stands.<br>
### **NPCs using Display Entities**
\> Using mineskin allows player heads to act as body parts.<br>
\> Skins should be cached to avoid making too many requests to mineskin.<br>
\> They should also be animatable using BlockBench.<br>
### **Scoreboards & Teams**
\> Scoreboards are pretty self-explanatory.<br>
\> It's essential to have this for names above heads and such. It would also help avoid team conflicts.<br>
### **Custom Tab**
\> Names above heads<br>
\> Custom Players<br>
\> Removing players? <br>
### **Custom HUD Utils**
\> Unity/JS/Pygame/Tkinter project to move elements around<br>
\> Figure out vertical hud movement<br>
\> Figure out how to scale with screen sizes and GUI scales<br>
### **Mass fill blocks**
\> Use NMS to efficiently replace mass amounts of blocks. Maybe I could figure out how vanilla /fill works and replicate that.<br>
### **Misc**
\> Worldborder moving over time<br>