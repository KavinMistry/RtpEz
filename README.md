# RtpEz: 
This is a simple and efficent random teleport plugin, with options for range, cooldown, countdown and much more ! 

If you find any bugs please email me at : minekavincraft@gmail.com, I will fix them asap !

## Guide: 

### Commands:

```
/rtp 
```
Teleports the player to a random location in the current world, accessible to all players !
```
/rtp refresh
```
Refreshes and loads the changes made to settings.yml file, use this when you want to apply settings without restarting !
```
/rtp (player)
```
Forces a player to rtp

Refresh and force rtp commands are by default only accessible to operators, 
permission : **RtpEz.command.admin**

### Changable settings via settings.yml file:

- **Messages** ( All messages sent by the plugin are customizable )
- **Range** ( Ex: 100 - 200, this makes a square from 100x,100z to 200x,200z ) 
- **Cooldown** ( Ex: 60000 millisecond, this sets the cooldown before rtp commmand can be used again )
- **Disabled Worlds** ( Ex: world_nether, this disables rtp in all worlds in this list )
- **Disabled Blocks** ( Ex: GRASS_BLOCK, this disables rtp on all blocks in this list )
- **Move Cancel** ( Ex: true, this will cancel rtp if the player moves )
- **Depth** ( Ex: 5, this will search for a suitable location 5 times before giving up )
- **Sound Effects** ( You can choose to turn off specific sound effects )

### Note:

If you want to create a seperate section in settings.yml for a world to set range and cooldown for only that world, you can copy the default "world" and change its name to match the world you want 
