package main

import (
	"fmt"
	"math"
	"math/rand"
	"sort"
	"sync"
	"time"
)

var wg sync.WaitGroup

const infinity = math.MaxInt64

type Graph struct {
	mu    sync.Mutex
	graph [][]int
	num   int
}

func contains(s []int, searchterm int) bool {

	for _, v := range s {
		if v == searchterm {
			return true
		}
	}

	return false
}

func pop_front(slice []int) []int {
	return append(slice[:0], slice[1:]...)
}

func unique(intSlice []int) []int {
	keys := make(map[int]bool)
	list := []int{}
	for _, entry := range intSlice {
		if _, value := keys[entry]; !value {
			keys[entry] = true
			list = append(list, entry)
		}
	}
	return list
}

func remove(slice [][]int, i int, j int) [][]int {
	slice = append(slice[:i], slice[i+1:]...)
	length := len(slice)

	for k := 0; k < length; k++ {
		slice[k] = append(slice[k][:j], slice[k][j+1:]...)
	}

	return slice
}

// thread 1
func (gr *Graph) print() {
	gr.mu.Lock()

	defer gr.mu.Unlock()
	fmt.Println(gr.num)
}

func (gr *Graph) add() {
	gr.mu.Lock()
	gr.num++
	gr.mu.Unlock()
}

func (gr *Graph) change_price(i int, j int, price int) {
	gr.mu.Lock()
	fmt.Println("change")

	gr.graph[i][j] = price
	gr.graph[j][i] = price

	fmt.Println(gr.graph)
	gr.mu.Unlock()
}

// thread 2
func (gr *Graph) delete_trip(i int, j int) {
	gr.mu.Lock()
	fmt.Println("delete")

	gr.graph[i][j] = 0
	gr.graph[j][i] = 0

	fmt.Println(gr.graph)

	gr.mu.Unlock()
}

func (gr *Graph) add_trip(i int, j int, price int) {
	gr.mu.Lock()
	fmt.Println("add_trip")

	gr.graph[i][j] = price
	gr.graph[j][i] = price

	fmt.Println(gr.graph)

	gr.mu.Unlock()
}

// thread 3
func (gr *Graph) add_city(stays []int, price []int) {
	gr.mu.Lock()

	fmt.Println("add_city")

	length := len(gr.graph)

	gr.graph = append(gr.graph, make([]int, length+1))

	for i := 0; i < length; i++ {
		v := 0

		if len(stays) > 0 && stays[0] == i {
			v = price[0]

			stays = pop_front(stays)
			price = pop_front(price)
		}

		gr.graph[length][i] = v

		gr.graph[i] = append(gr.graph[i], v)
	}

	fmt.Println(gr.graph)

	gr.mu.Unlock()
}

func (gr *Graph) delete_city(trip_idx int) {
	gr.mu.Lock()
	fmt.Println("delete_city")
	gr.graph = remove(gr.graph, trip_idx, trip_idx)

	fmt.Println(gr.graph)

	gr.mu.Unlock()
}

// ------------

func (gr *Graph) find_trip(v int, end int) int {
	gr.mu.Lock()
	fmt.Println("find_trip")
	N := len(gr.graph)
	T := make([]int, N)

	for i := 0; i < N; i++ {
		T[i] = infinity
	}

	S := make([]int, 0)

	S = append(S, v)
	T[v] = 0

	for v != -1 {
		for _, j := range gr.get_link_v(v) {
			if !contains(S, j) {
				w := T[v] + gr.graph[v][j]
				if w < T[j] {
					T[j] = w
				}
			}
		}

		v = arg_min(T, S)
		if v >= 0 {
			S = append(S, v)
		}
	}

	res := T[end]

	if res == infinity {
		return -1
	}

	fmt.Println(gr.graph)

	gr.mu.Unlock()

	return res
}

func array_max(a []int) int {
	max := a[0]
	for _, v := range a {
		if v > max {
			max = v
		}
	}
	return max
}

func arg_min(T []int, S []int) int {
	amin := -1
	m := array_max(T)

	for i, t := range T {
		if t < m && !contains(S, i) {
			m = t
			amin = i
		}
	}
	return amin
}

func (gr *Graph) get_link_v(v int) []int {

	res := make([]int, 0)
	for i, weigth := range gr.graph[v] {
		if weigth != 0 {
			res = append(res, i)
		}
	}

	return res
}

func (gr *Graph) rand_vals() (int, int, int, []int, []int) {
	i := rand.Intn(len(gr.graph) - 1)
	j := rand.Intn(len(gr.graph) - 1)
	price := rand.Intn(100)

	stays := make([]int, 0)

	for k := 0; k < len(gr.graph); k++ {
		stays = append(stays, rand.Intn(len(gr.graph)))
	}

	stays = unique(stays)

	sort.Slice(stays, func(i, j int) bool {
		return stays[i] < stays[j]
	})

	a_price := make([]int, len(stays))

	for k := 0; k < len(a_price); k++ {
		stays[k] = rand.Intn(100)
	}

	return i, j, price, stays, a_price
}

func main() {

	rand.Seed(time.Now().UnixNano())
	var gr Graph

	gr.num = 0

	graph := [][]int{
		{0, 3, 1, 3, 0, 0},
		{3, 0, 4, 0, 0, 0},
		{1, 4, 0, 0, 7, 5},
		{3, 0, 0, 0, 0, 2},
		{0, 0, 7, 0, 0, 4},
		{0, 0, 5, 2, 4, 0}}

	for _, l := range graph {
		gr.graph = append(gr.graph, l)
	}

	for l := 0; l < 100; l++ {
		i, j, price, stays, a_price := gr.rand_vals()
		go gr.change_price(i, j, price)

		i, j, price, stays, a_price = gr.rand_vals()
		go gr.add_trip(i, j, price)

		i, j, price, stays, a_price = gr.rand_vals()
		go gr.delete_trip(i, j)

		i, j, price, stays, a_price = gr.rand_vals()
		go gr.add_city(stays, a_price)

		i, j, price, stays, a_price = gr.rand_vals()
		go gr.delete_city(i)

		i, j, price, stays, a_price = gr.rand_vals()
		go gr.find_trip(i, j)

		time.Sleep(5 * time.Second)
	}
}
