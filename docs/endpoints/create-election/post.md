# Endpoint pour créer l'élection

**URL** : `/smart-vote/api/v1/create-election`

**Method** : `POST`

**Api key required** : NO

**Request**
```json
{
  "candidates": [
    "one",
    "two"
  ],
  "starting_date": "2022-10-19T08:30:00",
  "closing_date": "2022-10-20T08:30:00",
  "election_name": "value"
}
```
**Other possible request**
```json
{
  "candidates": [
    "one",
    "two"
  ],
  "starting_date": "2022-10-24T08:30:00",
  "closing_date": "2022-10-24T17:16:00",
  "election_name": "first_test",
  "count_blank_votes": "true"
}
```

## Success Responses

---

**Code** : 200 OK

**Body content** :
```json
{
  "election_name": "value",
  "blocks": [
    {
      "hash": "3a443a445d36acfad71cc15453b11267fd8b9218ded2244597203237cdf82180b61092bc963c64ab6d67d552c331c8c62947d56040182facc3d548242119fce1",
      "data": {
        "candidates": [
          "one",
          "two"
        ],
        "starting_date": "2022-10-19T08:30:00",
        "closing_date": "2022-10-20T08:30:00",
        "election_name": "value"
      }
    }
  ],
  "api_key": "KEY_GENERATED",
  "blockchain_valid": true
}
```



## Error Responses

---

**Condition**

**Code**

**Body content** : 
