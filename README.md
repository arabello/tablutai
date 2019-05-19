# IA Gioco Tablut

English version [below](#tablut-game-ai).

Progetto di concorso per il corso di Fondamenti di Intelligenza Artificiale presso Università di Bologna.

`tablutai` è una intelligenza artificiale formale basata su conoscenza.
Utilizza principalmente una ricerca negli spazi degli stati ad approfondimento iterativo con tagli Alpha Beta ([Russell-Norvig](http://aima.cs.berkeley.edu/)).

![Tavola](src/test/res/board2.png)

## Preparazione

### Server
Prima di poter iniziare occorre disporre del server relativo seguendo le istruzioni disponibili [qui](https://github.com/AGalassi/TablutCompetition).

### Installazione
Clonare il progetto:

`git clone https://github.com/arabello/tablutai.git`

### Uso
Aprire un terminale nella cartella root del progetto. Sotto la cartella `bin` è disponibile un fat jar eseguibile pronto all'uso.

Per giocare come bianco:

`java -jar bin/pelle -p w`

Per giocare come nero:

`java -jar bin/pelle -p b`

Per modificare il tempo massimo (in secondi) dedicato a turno:

`java -jar bin/pelle -p b -t 50`

Per ottenere tutte le opzioni disponibili:

`java -jar bin/pelle -h`

```
pelle 1.x
Usage: java -jar bin/pelle [options]

  -p, --player <value>     Type of player. Use 'w' or 'white' or 'WHITE' for the WHITE player. Use 'b' or 'black' or 'BLACK' for the BLACK player
  -t, --time <value>       The max player turn time in seconds. Default is 60
  -s, --serverIp <value>   The server IP. Default is localhost
  -w, --whitePort <value>  The server port for the WHITE player. Default is 5800
  -b, --blackPort <value>  The server port for the BLACK player. Default is 5801
  -d, --debug              Flag to enable debug logging
  -h, --help
```

### Compilazione
Nel caso fosse necessario generare l'eseguibile, installare [sbt](https://www.scala-sbt.org/download.html).
In ambiente Ubuntu, o altri basati su Debian, è possibile farlo attraverso i seguenti comandi:

```
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt
```
 
Successivamente, aprire un terminale nella cartella root ed eseguire:

`sbt assembly`

il quale compila, esegue tutti i test ed crea un nuovo fat jar sostituendo quello precedente sotto la cartella `bin`.

## Risoluzione dei problemi

### JRE Fatal Error
Se durante l'esecuzione del giocatore, il JRE riscontra un problema fatele simile al seguente:

```
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x00007f0b024734cd, pid=21947, tid=139676677560592
#
# JRE version: 6.0_15-b03
# Java VM: Java HotSpot(TM) 64-Bit Server VM (14.1-b02 mixed mode linux-amd64 )
# Problematic frame:
# V  [libjvm.so+0x5df4cd]
```

Aprire un terminale ed eseguire il seguente comando: 

`export LD_BIND_NOW=1`

Per i dettagli sulla problematica, [qui](http://osr507doc.sco.com/en/tools/ccs_linkedit_runtime_compat.html).

### Unsupported Class Version Error
Se l'esecuzione del jar ritorna un errore simile al seguente:
```
Exception in thread "main" java.lang.UnsupportedClassVersionError: ai/tablut/connectivity/StreamUtils has been compiled by 
a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class 
file versions up to 52.0
```

è necessario [compilare](#compilazione) il progetto.

# Tablut Game AI
Competition project of the Artificial Intelligence Fundamentals course at University of Bologna.

`tablutai` is a formal and knowledge based AI.
It mainly use an iterative deepening alpha beta search ([Russell-Norvig](http://aima.cs.berkeley.edu/)).

## Getting Started

### Server
First, you need the game server available [here](https://github.com/AGalassi/TablutCompetition).

### Install
Clone the project:

`git clone https://github.com/arabello/tablutai.git`

### Usage
Open a new terminal in the root project folder. A ready-to-use fat jar is available under the `bin` folder.

To play as white:

`java -jar bin/pelle -p w`

To play as black:

`java -jar bin/pelle -p b`

To change the maximum time (in seconds) of the player turn:

`java -jar bin/pelle -p b -t 50`

To get all the usage details:

`java -jar bin/pelle -h`

```
pelle 1.x
Usage: java -jar bin/pelle [options]

  -p, --player <value>     Type of player. Use 'w' or 'white' or 'WHITE' for the WHITE player. Use 'b' or 'black' or 'BLACK' for the BLACK player
  -t, --time <value>       The max player turn time in seconds. Default is 60
  -s, --serverIp <value>   The server IP. Default is localhost
  -w, --whitePort <value>  The server port for the WHITE player. Default is 5800
  -b, --blackPort <value>  The server port for the BLACK player. Default is 5801
  -d, --debug              Flag to enable debug logging
  -h, --help
```

### Compile
If a new executable is required, install [sbt](https://www.scala-sbt.org/download.html). 
On Ubuntu or other Debian-based systems, it can be done with the following commands:

```
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt
```

Then, open a new terminal in the root project folder and execute:

`sbt assembly`

that compiles, executes all the tests and create a new fat jar replacing the old one into the `bin` folder.

## Troubleshooting

### JRE Fatal Error
If during the runtime execution, the JRE throws a fatal error of this kind:

```
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x00007f0b024734cd, pid=21947, tid=139676677560592
#
# JRE version: 6.0_15-b03
# Java VM: Java HotSpot(TM) 64-Bit Server VM (14.1-b02 mixed mode linux-amd64 )
# Problematic frame:
# V  [libjvm.so+0x5df4cd]
```

open a termina and execute: 

`export LD_BIND_NOW=1`

For details about this issue, [here](http://osr507doc.sco.com/en/tools/ccs_linkedit_runtime_compat.html).

### Unsupported Class Version Error
If the execution of the jar file returns an error of this kind:
```
Exception in thread "main" java.lang.UnsupportedClassVersionError: ai/tablut/connectivity/StreamUtils has been compiled by 
a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class 
file versions up to 52.0
```

it is required to [compile](#compilazione) the project.

# License

MIT License

Copyright (c) 2019 Matteo Pellegrino

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.