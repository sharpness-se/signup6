# SignUp 6
6th version of SignUp

[![Java CI with Gradle](https://github.com/sharpness-se/signup6/actions/workflows/gradle.yml/badge.svg)](https://github.com/sharpness-se/signup6/actions/workflows/gradle.yml)
[![Automated API tests using Postman CLI](https://github.com/sharpness-se/signup6/actions/workflows/postman.yml/badge.svg)](https://github.com/sharpness-se/signup6/actions/workflows/postman.yml)
[![CodeQL](https://github.com/sharpness-se/signup6/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/sharpness-se/signup6/actions/workflows/codeql-analysis.yml)

## Swagger and SpringDoc
The URL to the API description is http://localhost:8080/api/swagger.html when the server is running locally.
Check https://springdoc.org/

## Environment variables
The production environment depends on the following environment variables

| Name                       | Value                            | Description                                                                    |
|----------------------------|----------------------------------|--------------------------------------------------------------------------------|
| SIGNUP_BACKEND_BASE_URL    | https://signup.sharpness.se:8080 | The URL to where SignUp backend is deployed. Used to generate links in emails. |
| SIGNUP_FRONTEND_BASE_URL   | https://signup.sharpness.se      | The URL to where SignUp frontend is deployed. Used to do redirects to UI.      |
| SIGNUP_REMINDER_SCHEDULE   | "-" or a Cron schedule string    | When and how often the system looks for reminders to send.                     |
| SIGNUP_ACTIVATE_MOCK_EMAIL | true/false                       | If true, mails will not be sent from the server - only logged.                 |
| SIGNUP_SMTP_HOST           | smtp.gmail.com                   | The SMTP host used to send email reminders                                     |
| SIGNUP_SMTP_PORT           | 25 or 587 (TLS)                  | The port that the SMTP server listens to                                       |
| SIGNUP_SMTP_USERNAME       | <username>                       | The username to access the SMTP server                                         |
| SIGNUP_SMTP_PASSWORD       | <password>                       | The password to access the SMTP server                                         |

