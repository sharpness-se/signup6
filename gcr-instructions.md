## How to deploy this project to Google Cloud Run
To make it possible for this application to connect to the CloudSQL database  
after it has been pushed up to Google Cloud Run (gcr), we need to create a credentials file for the  
application to use to get access to the database by itself.  
To do this, we need to install Google Cloud CLI.  
Powershell command to install GC CLI:
```
(New-Object Net.WebClient).DownloadFile("https://dl.google.com/dl/cloudsdk/channels/rapid/GoogleCloudSDKInstaller.exe", "$env:Temp\GoogleCloudSDKInstaller.exe")
& $env:Temp\GoogleCloudSDKInstaller.exe
```  
[Link to CLI installation guide](/https://cloud.google.com/sdk/docs/install)  

After you have installed and ran GC CLI, log in with desired Google account and when prompted select your gcr project. (fast-flight-366213)  
Once you have logged in and selected the right project, create an app credential file by using this command:  
```gcloud auth application-default login```  
By default this file should be located at ```C:\Users\USER\AppData\Roaming\gcloud ``` 
with the name ```application_default_credentials.json```
Copy this file into the resources folder inside this project.  
[Googles guide on how to create a credentials file](/https://cloud.google.com/docs/authentication/provide-credentials-adc#local-dev)  

Now you are ready to deploy, so create a docker image of the project by running the gradle build config **bootBuildImage**  
To get this image to gcr, you need to push the image to docker hub.
To push the image, you first have to create a tag and then push the tag.  
Example:
```
docker tag IMAGE_NAME:VERSION DOCKER_USERNAME/REPOSITORY_NAME
docker push DOCKER_USERNAME/IMAGE_NAME
```
Once the push is complete, head over to gcr and open cloud shell.  
Then, to get the image to gcr use theese commands:  
```
docker pull DOCKER_USERNAME/REPOSITORY_NAME:latest
docker tag DOCKER_USERNAME/IMAGE_NAME gcr.io/fast-flight-366213/DOCKER_USERNAME/REPOSITORY_NAME
docker push gcr.io/fast-flight-366213/DOCKER_USERNAME/REPOSITORY_NAME
```
When the push is complete, you should be able to find the image inside the Container Registry.  
In Cloud Run select Create Service, press select container image and select the one you pushed.  
In the same window, scroll down to "Container, Connections, Security".  
Under capacity, change Memory to atleast 1GiB. Then scroll down to Environment variables  
and add the variables EMAIL and PASSWORD.      
After that, head over to the connections tab press Add Connections under  Cloud SQL connection, 
and add a connection to your database.  
Now you are good to go!

