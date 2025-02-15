# Deploying on Heroku
Instead of deploying on Google Cloud Run/SQL, Singup6 can be deployed on the cloud service [Heroku](https://heroku.com)

## Prepare yourself
1. Create an account on heroku.com
2. Install the [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
3. Go to the top of the source code tree in a terminal window and do:
```
heroku login
```

## Create the Heroku app/instance
This only has to be done _once_ for all team members. You should probably skip this step if someone else already has done this ini your team.

The app is created in the Heorku cloud in EU with three addons (PostgreSQL database, a log viewer app and a scheduler to run commands att different times):
```
heroku apps:create --region eu signup6
heroku addons:create heroku-postgresql:mini
heroku addons:create papertrail:choklad
heroku addons:create scheduler:standard
```

Set some environment variables on Heroku:
```
heroku config:add TZ="Europe/Stockholm"
heroku config:add SIGNUP_SMTP_HOST=smtp.gmail.com
heroku config:add SIGNUP_SMTP_PORT=587
heroku config:add SIGNUP_SMTP_USERNAME=signup-noreply@sharpness.se
heroku config:add SIGNUP_SMTP_PASSWORD=<password>
heroku config:add SIGNUP_ACTIVATE_MOCK_EMAIL=false
```

## Prepare your local GIT repository
On Heroku you generally don't deploy Docker Containers. Instead, they use a "build pack" approach, meaning that Heroku will access your source code, build it and then deploy it on its own. 

For that reason you have to connect your local GIT repository to the repository used by the Heroku instance. Basically theis means setting up the GIT repo on Heroku as a remote to your local repository.

You already have _one_ remote on GitHub (called "origin"). Now we just add another called "heroku". You can use the Heroku CLI to do this:
```
heroku git:remote --app signup6
```

## Deploy on Heroku
Deploying the app on heroku is done by pushing to the Heroku GIT repository:
```
git push heroku master
```

View the exposed API at https://signup6.herokuapp.com/api/swagger.html

## Run Singup6 locally Heroku-style
```
gradle clean build
heroku local -f Procfile.dev
```
The command `heroku local` will launch the app locally in a similar way that the Heroku cloud service will.
When Heroku launches the app after building it, the default [Procfile](../Procfile) is used to know what command to execute. Since we want to use a local database and not the one on the Heroku cloud, we have [Procfile.dev](../Procfile.dev) for local use that wil launch using the dev profile in Spring Boot.
