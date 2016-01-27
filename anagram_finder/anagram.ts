import express = require('express');
import fs = require('fs');
import readline = require('readline');

interface WordListMap {
    // A map of string to list of strings (string -> string[])
    [key: string]: string[];
}

class Anagram {
    wordMap: WordListMap;

    private static get EMPTY(): string { return ""; }

    constructor() {
        this.wordMap = {};
    }

    private getKey(word: string): string {
        var key = Anagram.EMPTY;
        if (word && word.length > 0) {
            var arr: string[] = word.split(Anagram.EMPTY).sort();
            key = arr.join(Anagram.EMPTY);
        }
        return key;
    }

    public insert(word: string): void {
        var key: string = this.getKey(word);
        if (key.length > 0) {
            if (!(key in this.wordMap)) {
                this.wordMap[key] = new Array<string>();
            }
            this.wordMap[key].push(word);
        }
    }

    public find(word: string): string[] {
        var key: string = this.getKey(word);
        if (key in this.wordMap) {
            return this.wordMap[key];
        }
        return [];
    }

    public find_most(): string[] {
        var max_key: string = Anagram.EMPTY;
        var current_max: number = 0;
        for (var key in this.wordMap) {
            if (this.wordMap[key].length > current_max) {
                current_max = this.wordMap[key].length;
                max_key = key;
            }
        }
        return this.wordMap[max_key];
    }
}

//-------------------------------------------------------------------
// Global initialization
var lineReader: readline.ReadLine = readline.createInterface({
    input: fs.createReadStream("enable1.txt"),
    output: null
});

var anagram: Anagram = new Anagram();
lineReader.on("line", function (line) {
    anagram.insert(line);
});
//-------------------------------------------------------------------

// For routing
export function find(req: express.Request, res: express.Response) {
    var word: string = req.params.word;
    res.json(anagram.find(word.toLowerCase()));
}

export function find_most(req: express.Request, res: express.Response) {
    res.json(anagram.find_most());
}
