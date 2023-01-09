```# How to communicate with and manipulate Google Cloud SQL DB data
This application is meant to be able to run on Google Cloud Run and use a Database instance hosted on Google Cloud SQL(CSQL).
It is assumed that a DB instance is already up and running on CSQL.

### Connect to your Database with a command prompt
First off you need to know the name of the targeted DB. To find the name you first have to navigate to the [SQL service](https://console.cloud.google.com/sql).
There you will find a list of instances, click on your intended DB. To the far left there is a list of choices such as 
*Overview*, *Users*, *Databases*, pick the latter one. You should now see a list of existing databases and their names.

Next up you need to open a command shell. In the upper right you see an icon showing the account you're logged in with, 
in the same group of icons to the left of the account one you'll have a set of other icons of which one is *Activate Cloud Shell*.
Open it up and enter:

```gcloud sql connect [instance name] --database=[DB name] --user=[DB username]```  
*As a side note, you can find all registered DB Usernames by clicking on *Users* in the same list where you found *Databases*,  
and commands are case-sensitive.

Accept the authorization prompt and wait for the command shell to connect, and when the connection is established enter 
the password associated with [DB Username] *(ask devs)*. You now have access and can communicate with the DB with SQL commands.


### Some common commands

*Notes:*  
*it is important to end your SQL commands with* **;**  
*When you insert data you need to enter a value for every column*

**\dt** - *Show all tables*  
**SELECT * FROM [table];**  i.e. **SELECT * FROM users;** - *Presents all entries in selected table. Enter **Q** to exit table view*  
**SELECT * FROM [table] WHERE [column]=[columnValue];** i.e. **SELECT * FROM users WHERE id=1;** - *Presents a specific entry 
filtered by desired column value such as ID or Name.. Enter **Q** to exit table view*  
**INSERT INTO [table] VALUES ([columnValue], [columnValue], [columnValue]...);** i.e. **INSERT INTO public.reminders VALUES (-1, -9, '2022-11-15 01:00:00') ON CONFLICT DO NOTHING;** -
*Adds data to chosen table, if you get error check that all column values are defined*  
**DELETE FROM [table] WHERE [column]=[columnValue];** i.e. **DELETE FROM public.reminders WHERE id=1;**
*Deletes data row from chosen table that matches the column value you enter*  

```