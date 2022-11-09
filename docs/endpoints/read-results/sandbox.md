# Endpoint pour récupérer les données de l'élection

**URL** : `/smart-vote/api/v1/get-sandbox/`

**Method** : `GET`

**Api key required** : YES

## Success Responses

---

**Code** : 200 OK

**Body content** :
```json
{
  "starting_date": "2022-11-01T10:00:00",
  "closing_date": "2022-11-01T21:00:00",
  "candidates_results": {
    "Candidate_1": 17,
    "Candidate_2": 17,
    "Candidate_3": 16
  }
}
```


## Error Responses

---

**Condition** : Aucune `Api Key` n'est utilisée, ou bien une fausse

**Code** : `401 Unauthorized`

**Body content** : 
```json
{
    "code": "INVALID_API_KEY",
    "message": "This API Key cannot be used for the 'sandbox' election. Any attempt at fraud will be fought back !"
}
```