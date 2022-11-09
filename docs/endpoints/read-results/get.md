# Endpoint pour récupérer les données de l'élection

**URL** : `/smart-vote/api/v1/get-election/{election_name}`

**Method** : `GET`

**Api key required** : NO

## Success Responses

---

**Code** : 200 OK

**Body content** :
```json
{
  "election_name": "value",
  "blocks": [
    {
      "hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
      "data": {
        "candidates": [
          "one",
          "two"
        ],
        "starting_date": "2022-10-19T08:30:00",
        "closing_date": "2022-10-20T08:30:00",
        "election_name": "value"
      }
    },
    {
      "hash": "09e37554f4afa241919bd1a96a6e60091139e724c10226b1109f3799d3e06d1225b3ac034af15813a467de61806ee399a180d9af53a796e0f1c0e575f274883c",
      "previous_hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
      "data": {
        "election_name": "value",
        "candidate_name": "one",
        "voting_date": "2022-10-22T12:30:00"
      }
    }
  ],
  "blockchain_valid": true
}
```



## Error Responses

---

**Condition**

**Code**

**Body content** : 
