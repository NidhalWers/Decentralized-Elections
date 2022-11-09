# Endpoint pour enregistrer un vote dans la blockchain

**URL** : `/smart-vote/api/v1/vote`

**Method** : `POST`

**Api key required** : YES

**Request**
```json
{
  "election_name" : "value",
  "candidate_name" : "one",
  "voting_time" : "2022-10-22T12:30:00"
}
```

## Success Responses

---

**Code** : 200 OK

**Body content** :
```json
{
  "hash": "09e37554f4afa241919bd1a96a6e60091139e724c10226b1109f3799d3e06d1225b3ac034af15813a467de61806ee399a180d9af53a796e0f1c0e575f274883c",
  "previous_hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
  "data": {
    "election_name": "value",
    "candidate_name": "one",
    "voting_date": "2022-10-22T12:30:00"
  }
}
```



## Error Responses

---

**Condition** : L'`Api Key` ne peut être utilisée pour cette élection,<br>
ou bien aucune `Api Key` n'est envoyée

**Code** : `401 Unauthorized`

**Body content** : 
```json
{
  "code": "INVALID_API_KEY",
  "message": "This API Key cannot be used for the 'first_test' election. Any attempt at fraud will be fought back !"
}
```

---

**Condition** : Ce votant a déjà voté pour cette élection

**Code** : `405 Method Not Allowed`

**Body content** :
```json
{
  "code": "HAS_ALREADY_VOTED",
  "message": "voter3 has already voted for the election named 'first_test'"
}
```

---

**Condition** : Le vote n'indique pas un candidat faisant réellement partie de l'élection

**Code** : `400 bad Request`

**Body content** :
```json
{
  "code": "INVALID_PARAMETER",
  "message": "the name 'false_candidate' is not part of the first_test's candidates"
}
```

---

**Condition** : Le vote est effectué avant le début de l'élection

**Code** : `405 Method Not Allowed`

**Body content** :
```json
{
  "code": "ELECTION_NOT_STARTED",
  "message": "Election 'first_test' has not started yet, wait until 2022-10-24T08:30"
}
```

---

**Condition** : Le vote est effectué après la fin de l'élection

**Code** : `405 Method Not Allowed`

**Body content** :
```json
{
  "code": "ELECTION_ALREADY_FINISHED",
  "message": "Election 'first_test' is already finished since 2022-10-24T17:16"
}
```
