# Description

Dota 2 is a 5v5 real time strategy game, where each player controls a single hero. These heroes can buy items, cast spells, damage & kill other heroes, destroy buildings, and so much more.

You are given two sample subsets of a Dota 2 "combat log" - a human-readable record of events that happened in a match. The heroes in the game are encoded as `npc_dota_hero_{hero_name}` within the combat log, and items are encoded as `item_{item_name}` (the prefixes `npc_dota_hero_` and `item_` are not part of the names).
Each line represents one event, and it begins with a timestamp relative to the start of the match. It's normal for there to be no events for the first few minutes - this is when the players are choosing their hero for the match. If you don't understand a specific event, rather ignore it and carry on processing the rest. 

The events that we are interested in are:
* Items being purchased (including the timestamp of the purchase)
* Heroes killing each other
* Spells being cast (also has the level of the spell, there are multiple different levels for each spell)
* Damage being done to a hero (the word "hits" gives this away) could be from:
  * A normal attack (signified by dota_unknown as the damage type)
  * A spell or item

> Note that there are other, non-hero, characters in the game which can cause events.

All other events (items being used, buildings taking damage, gamestate changes, healing done, etc) can be ignored.

The sample data can be found in the `data` folder in the root of this project.

# Task

Your task is to:

* Process the combat log for both of the matches and extract the events described above.
* Store these significant events in a relational database (H2 is preconfigured). We have prepared an entity class `CombatLogEntryEntity` that you can use to persist events.
* Expose the key queries via a REST API (see below).

# Key Queries

* `POST /api/match`
An endpoint to receive a combat log text file and ingest it into the system. You can post a text file to the endpoint using the following `curl` command:

```shell
curl -H "Content-Type: text/plain" -d @combatlog_1.txt http://localhost:8080/api/match
```

* `GET /api/match/$match_id`
A list of the heroes within a match and the number of kills they made

```json
[{
   "hero": "rubick",
   "kills": 7
}, ...]
```

* `GET /api/match/$match_id/$hero_name/items`
Each item purchase (time bought as milliseconds since match start and the item name) made by the specified hero within the selected match

```json
[{
   "item": "quelling_blade",
   "timestamp": 530925
}, ...]
```

* `GET /api/match/$match_id/$hero_name/spells`
For each different spell a hero casts in a particular match, the number of times they cast said spell

```json
[{
   "spell": "abyssal_underlord_firestorm",
   "casts": 83
}, ...]
```

* `GET /api/match/$match_id/$hero_name/damage`
For each hero that was damaged by the specified hero, the number of times we damaged that hero, and the total damage done to that hero

```json
[{
   "target": "snapfire",
   "damage_instances": 67,
   "total_damage": 79254
}, ...]
```
