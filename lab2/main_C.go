package main

import (
	"fmt"
	"math/rand"
	"time"
)

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func battle(fighters []int, next_fight chan int) int {
	fmt.Println(fighters)

	if len(fighters) == 1 {
		next_fight <- fighters[0]
		return fighters[0]
	}

	var winners []int
	for i := 0; i < len(fighters); i += 2 {
		winners = append(winners, max(fighters[i], fighters[i+1]))
	}

	return battle(winners, next_fight)
}

func main() {
	var fighters [64]int
	rand.Seed(time.Now().Unix())

	for i := 0; i < len(fighters); i++ {
		fighters[i] = rand.Intn(500)
	}

	last_fight := make(chan int, 2)

	go battle(fighters[:len(fighters)/2], last_fight)
	go battle(fighters[len(fighters)/2:], last_fight)

	leftWinner := <-last_fight
	rightWinner := <-last_fight
	fmt.Printf("Winner is %d", max(rightWinner, leftWinner))
}
