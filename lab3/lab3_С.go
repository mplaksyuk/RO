package main

import (
	"fmt"
	"math/rand"
	"strings"
	"sync"
	"time"
)

var cigarette_receipt = []string{"tobacco", "paper", "matches"}

var cigarette_done []string

func contains(s []string, searchterm string) bool {

	for _, v := range s {
		if v == searchterm {
			return true
		}
	}

	return false
}

func manager(wg *sync.WaitGroup) {
	cigarette_done = []string{}
	var f, s int

	f = rand.Int() % len(cigarette_receipt)
	for f == s {
		s = rand.Int() % len(cigarette_receipt)
	}
	cigarette_done = append(cigarette_done, cigarette_receipt[f], cigarette_receipt[s])

	wg.Done()
}

func smoker(who string, wg *sync.WaitGroup) {

	if !contains(cigarette_done, who) {
		fmt.Printf("Now in cigarette %v\n", cigarette_done)

		fmt.Printf("Add " + who + " in cigarette\n")

		fmt.Println("\nsmoking a cigarette")
		for i := 0; i <= 3; i++ {
			fmt.Println("\n(̅_̅_̅_̅(̅(̅_̅" + strings.Repeat("_̲̅", 12-i*4) + "_̲̅_̲̅_̲̅_̲̅_̲̅_̲̅_̲̅_̅()" + strings.Repeat("~ ", 7-i*2) + "\n")
			time.Sleep(1 * time.Second)
		}
	}

	wg.Done()
}

func main() {

	var wg sync.WaitGroup

	var mutex sync.Mutex

	for i := 0; i < 3; i++ {
		mutex.Lock()
		wg.Add(1)
		manager(&wg)
		wg.Wait()

		wg.Add(3)
		go smoker("tobacco", &wg)
		go smoker("paper", &wg)
		go smoker("matches", &wg)
		wg.Wait()
		mutex.Unlock()
	}
}
