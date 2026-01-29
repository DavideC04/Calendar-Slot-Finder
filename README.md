# Calendar Slot Finder

## Scopo
Questo progetto espone un servizio REST che calcola gli slot di tempo disponibili
per uno o più utenti, tenendo conto di:
- eventi già presenti in calendario
- buffer prima e dopo gli eventi
- orari di lavoro
- pause (es. pausa pranzo)
- durata richiesta dello slot

## Modello dei dati
Il sistema utilizza i seguenti oggetti principali:
1.Event
 - id: identificativo univoco
 - userId: identificativo dell’utente
 - title: titolo dell’evento
 - startTime: data e ora di inizio
 - endTime: data e ora di fine

 2.Availability (input)
 - userIds: lista di utenti coinvolti
 - from: inizio intervallo di ricerca
 - to: fine intervallo di ricerca
 - duration: durata richiesta dello slot (in minuti)
 - buffer: minuti aggiuntivi prima e dopo ogni evento
 - results: numero massimo di slot restituiti
 - workStart: inizio orario lavorativo
 - workEnd: fine orario lavorativo
 - breaks: lista di pause

 3.Break
 - startBreak: inizio pausa
 - endBreak: fine pausa

 4.Slot (output)
 - start: inizio slot disponibile
 - end: fine slot disponibile


## Come funziona
1. Recupera gli eventi dal database nel range richiesto
2. Applica un buffer agli eventi
3. Genera uno slot solo se:
   - ha la durata richiesta
   - non si sovrappone a eventi
   - rientra negli orari di lavoro
   - non cade in una pausa
4. Restituisce al massimo il numero di risultati richiesto


## Input/output (esempio)
 **Input**
```json
{
"userIds": [1, 2],
"from": "2026-01-29T11:00:00",
"to": "2026-01-29T18:00:00",
"duration": 30,
"buffer": 15,
"results": 2,
"workStart": "2026-01-29T09:00:00",
"workEnd": "2026-01-29T18:00:00",
"breaks": [
{ "startBreak": "2026-01-29T13:00:00", "endBreak": "2026-01-29T14:00:00" }
]
}
```
**output**
```json
[
    {
        "start": "2026-01-29T11:00:00",
        "end": "2026-01-29T11:30:00"
    },
    {
        "start": "2026-01-29T11:30:00",
        "end": "2026-01-29T12:00:00"
    }
]
```

## Assunzioni
- se uno slot cade anche solo parzialmente in una pausa, viene scartato
- le pause hanno priorità sugli slot disponibili


## Possibili miglioramenti
- gestione di orari diversi per ogni utente
- miglioramento delle prestazioni con molti utenti

