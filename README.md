# Dungeons Akimbo
Dungeons Akimbo is an action packed top down 2D dungeon crawler shooter! You can play as
four different characters each with their own unique weapon in their main hand, and a pistol
in the offhand. Players progress through the game by killing all the enemies on each level
before moving onto the next level. Players can use a dodge mechanic to become immune to damage
for a brief instance of time and slightly increasing speed. Each weapon has its own unique attributes 
such as damage, fire rate, ammo size, and reload speed. Each level provides a different action packed experience 
with different terrain and enemies! Boss levels are even more hectic and force players to adapt
their strategy! Collect gold and health potions from chests scattered throughout the map to
increase points or survivability.


## Project Status: Complete

## Gameplay

### Controls
Movement: W A S D or directional buttons on joy-cons (with the joy-con held horizontally)
Shoot: Mouse Left Click or SR button on joy-cons
Dodge: Space Bar or press joystick on joy-cons
Change Weapon: Q or ZL/ZR on joy-cons
God Mode: G
Enemy Pathing: P

Note: using a singular joy-con to play this game will likely be slightly uncomfortable for those with large hands.

### Weapons
	Shotgun (Character 1):
		Fire a short burst of 8 bullets arcing away from player with short range.
		5 shots per clip.
		1.5s reload time.
	Sniper (Character 2):
		Fire a fast moving hard hitting single bullet with long range.
		10 shots per clip.
		2s reload time.
	Assault Rifle (Character 3):
		Fire bullets in bursts of 3 with decent range (semi automatic)
		24 shots per clip.
		1s reload time.
	Sub Machine Gun (Character 4):
		Fires with an extremely fast fire rate (automtic) with short range.
		20 shots per clip.
		0.75s reload time.
	Pistol (Every Player Offhand) (Character 4):
		Fires a single bullet with a quick fire rate.
		10 shots per clip.
		1s reload time.

## Project Goals

### Low Bar (Implementation)

## Game Notes
Multiple different character classes are implemented with unique sprites and attributes, but they are not currently visiable in the game when running. 
Multiple levels are implemented but also not accessible due to not being able to fix level transitions in time.
Networking is implemented with a UDP chat client with player positioning information being sent through the packets.

- [x] 2 to 4 playable characters
- [x] Weapons that have different mechanics
    - [x] Piercing Bullets
    - [x] Long Distance Bullets
    - [x] Rapid Fire
- [x] 1 to 4 maps with scrollable rooms
- [x] 2 bosses
    - [x] 1 mini-boss
    - [x] 1 final boss
- [x] 4 enemies with differenet behavior
- [x] Point System based on treasures collected
- [x] Co-Op using controllers
    - [x] Handle joy-con controls
- [x] Implement Drops/Pick-ups

### High Bar
- [ ] Additoinal weapons
- [ ] Additional mob mechanics
- [ ] Improve Individual Player Mechanics
- [ ] Each map has a mini-boss
- [ ] Upgrade weapon system
- [ ] Implement leader enemies
- [ ] Implement upgrade weapon system
- [ ] Allow weapons to be thrown as an attack
- [x] Network Communication (currently done via UDP over localhost)
- [ ] New Game+ (Replayabllity + Increased Difficulty)

## License
Our game falls under an Apache-2.0 License.
