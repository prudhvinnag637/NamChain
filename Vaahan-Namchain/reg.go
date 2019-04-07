
// 

package com.namchain.chain.reg

import (
	"crypto/sha256"
	"encoding/hex"
	"encoding/json"
	"io"
	"log"
	"net/http"
	"os"
	"strconv"
	"sync"
	"time"

	"github.com/davecgh/go-spew/spew"
	"github.com/gorilla/mux"
	"github.com/joho/godotenv"
)

// Block structure of the Registration Chain
type Block struct {
	Index     int
	UUID      int
	VReg      string
	ENo       string
	CNo		  string
	VModel 	  string
	VType 	  string
	VSS		  string
	DID  	  string
	Timestamp string
	Hash      string
	PrevHash  string
}

// Blockchain is a series of validated Blocks
var Blockchain []Block

// Message takes incoming JSON payload for writing heart rate
type Message struct {
	UUID      int
	VReg      string
	ENo       string
	CNo		  string
	VModel 	  string
	VType 	  string
	VSS		  string
}

var mutex = &sync.Mutex{}

// Main function
func main() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal(err)
	}

	go func() {
		t := time.Now()
		genesisBlock := Block{}
		genesisBlock = Block{0, 0, "", "", "", "", "", "", "", t.String(), calculateHash(genesisBlock), ""}
		spew.Dump(genesisBlock)

		mutex.Lock()
		Blockchain = append(Blockchain, genesisBlock)
		mutex.Unlock()
	}()
	log.Fatal(run())

}

// web server
func run() error {
	mux := makeMuxRouter()
	httpPort := os.Getenv("PORT")
	log.Println("HTTP Server Listening on port :", httpPort)
	s := &http.Server{
		Addr:           ":" + httpPort,
		Handler:        mux,
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}

	if err := s.ListenAndServe(); err != nil {
		return err
	}

	return nil
}



// HTTP Handlers 
func makeMuxRouter() http.Handler {
	muxRouter := mux.NewRouter()
	muxRouter.HandleFunc("/", handleGetBlockchain).Methods("GET")
	muxRouter.HandleFunc("/", handleWriteBlock).Methods("POST")
	return muxRouter
}

// write blockchain when we receive an http request
func handleGetBlockchain(w http.ResponseWriter, r *http.Request) {
	bytes, err := json.MarshalIndent(Blockchain, "", "  ")
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	io.WriteString(w, string(bytes))
}

// takes JSON payload as an input for heart rate (BPM)
func handleWriteBlock(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var msg Message

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&msg); err != nil {
		respondWithJSON(w, r, http.StatusBadRequest, r.Body)
		return
	}
	defer r.Body.Close()

	mutex.Lock()
	prevBlock := Blockchain[len(Blockchain)-1]
	newBlock := generateBlock(prevBlock, msg.UUID,msg.VReg,msg.ENo,msg.CNo,msg.VModel,msg.VType,msg.VSS)

	if isBlockValid(newBlock, prevBlock) {
		Blockchain = append(Blockchain, newBlock)
		spew.Dump(Blockchain)
	}
	mutex.Unlock()

	respondWithJSON(w, r, http.StatusCreated, newBlock.DID)

}

func respondWithJSON(w http.ResponseWriter, r *http.Request, code int, payload interface{}) {
	response, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("HTTP 500: Internal Server Error"))
		return
	}
	w.WriteHeader(code)
	w.Write(response)
}

// make sure block is valid by checking index, and comparing the hash of the previous block
func isBlockValid(newBlock, oldBlock Block) bool {
	if oldBlock.Index+1 != newBlock.Index {
		return false
	}

	if oldBlock.Hash != newBlock.PrevHash {
		return false
	}

	if calculateHash(newBlock) != newBlock.Hash {
		return false
	}

	return true
}

// SHA256 hasing
func calculateHash(block Block) string {
	record := strconv.Itoa(block.Index) + block.Timestamp + strconv.Itoa(block.UUID) + block.VReg + block.ENo + block.CNo + block.VModel + block.VType + block.VSS + block.DID + block.PrevHash
	h := sha256.New()
	h.Write([]byte(record))
	hashed := h.Sum(nil)
	return hex.EncodeToString(hashed)
}

// Distributed Identifer
func calculateDID(block Block) string {
	record := strconv.Itoa(block.Index) + block.Timestamp + strconv.Itoa(block.UUID) + block.VReg + block.ENo + block.CNo + block.VModel + block.VType + block.VSS + block.PrevHash
	h := sha256.New()
	h.Write([]byte(record))
	hashed := h.Sum(nil)
	return hex.EncodeToString(hashed)
}

// create a new block using previous block's hash
func generateBlock(oldBlock Block, UUID int,VReg string,ENo string,CNo string,VModel string,VType string,VSS string) Block {

	var newBlock Block

	t := time.Now()

	newBlock.Index = oldBlock.Index + 1
	newBlock.Timestamp = t.String()
	newBlock.UUID = UUID
	newBlock.VReg = VReg
	newBlock.ENo = ENo
	newBlock.CNo = CNo
	newBlock.VModel = VModel
	newBlock.VType = VType
	newBlock.VSS = VSS
	newBlock.DID = calculateDID(newBlock)
	newBlock.PrevHash = oldBlock.Hash
	newBlock.Hash = calculateHash(newBlock)

	return newBlock
}
