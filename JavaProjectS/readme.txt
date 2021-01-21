--------------------------------------
Minion Valuation Server
--------------------------------------
Before use, we strongly recommend to fill the database with the values ​​contained in the Minion_Valuation.sql file.

Admin account : Login - OneWayDream
	        Password - qwerty007ru


Attention! Do not forget to register the data for connecting the database in the src / main / resources / db.properties file. 
Get your Hypixel API key - typing the "/api new" command in-game.


Database tables discription:

cookies - table for storing user sessions. Session identifier (session_id) - uuid, user_id - foreign key to the user table.

users - a table for storing users, logins and email should not be repeated according to the logic of the site.

fuels - table for fuel for minions.

item_bazaar_price - table storing non-static data - bazaar prices. When the server is running, this table is updated every 20 minutes.

item_nps_price - a table that stores prices for items from the nps server.

item_smelt - a table storing the possible transformations of the final minion production using the auto smelter.

item_super_compactor - a table storing the possible transformations of the final minion production using the super compactor 3000.

item_compact - a table storing the possible transformations of the final minion production using the compactor.

minion_upgrade_group - a table containing records of the type "minion - improvement", where the improvement can be put on this minion.

minions - a table containing information about the minion (his name and speed of action at different tiers.

production - a table containing records of the type "minion - item - chance - amount ", where minion produce item "item" with "chance" in "amount".

upgrades - a table that contains the names of the improvements and their scope. ALL means that this improvement applies to all minions.




