# Java Minesweeper

A fun little side project I made using Java.

Features 3 different board sizes:
	easy: an 8x8 board with 10 mines (default)
 	medium: a 16x16 board with 40 bombs
	hard: 22x22 board with 100 bombs

Ability to specify whether you want to reveal the tile or mark it as a potential bomb (equivalent to right clicking in the Windows version).
	Prevents from revealing tiles marked as potential bombs.

If a cell clicked has no nearby bombs, all cells around it are revealed similarly to the normal Minesweeper game.

The game ends when the player reveals a bomb tile, has revealed all the non-bomb squares or has marked all the bombs.

TODO: Fix countBombs in Board.java to use a loop instead of the 8 if statements currently used. Look at revealNearbyZeroes in Minesweeper.java as a guide to do this.
