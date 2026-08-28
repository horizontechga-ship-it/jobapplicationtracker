# Job Application Tracker --- PowerShell Commands

A compact reference for the PowerShell commands used while developing
and testing the backend.

## Compile

Run from the project root:

``` powershell
.\mvnw.cmd clean compile
```

## Run Spring Boot

Set the database password for the current PowerShell session, then run
the application:

``` powershell

.\mvnw.cmd spring-boot:run
```

## GET all applications

``` powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications" `
    -Method Get |
    ConvertTo-Json -Depth 10
```

## GET one application

Replace `1` with the desired application ID:

``` powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/1" `
    -Method Get |
    ConvertTo-Json -Depth 10
```

## GET and throw error
```powershell
try {
    Invoke-RestMethod `
        -Uri "http://localhost:8080/applications/999999" `
        -Method Get
}
catch {
    $_ | Format-List *
}
```
with error JSON
```powershell
try {
    Invoke-RestMethod `
        -Uri "http://localhost:8080/applications/999999" `
        -Method Get
}
catch {
    $response = $_.Exception.Response
    $reader = New-Object System.IO.StreamReader($response.GetResponseStream())
    $reader.ReadToEnd()
}
```


## POST a new application

``` powershell
$body = @{
    company       = "Acme Corporation"
    role          = "Java Developer"
    appliedAt     = "2026-08-24T22:45:00"
    resumeName    = "Java Backend Resume"
    resumeVersion = "spring-26"
    resumeUrl     = "https://docs.google.com/example-resume"
    companyUrl    = "https://example.com"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/applications" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body |
    ConvertTo-Json -Depth 10
```
## UPDATE an application
```powershell
$body = @{
    company       = "Acme Corporation"
    role          = "Senior Java Developer"
    appliedAt     = "2026-08-24T22:45:00"
    resumeName    = "Java Backend Resume"
    resumeVersion = "spring-26-v2"
    resumeUrl     = "https://docs.google.com/example-resume"
    companyUrl    = "https://example.com"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/4" `
    -Method Put `
    -ContentType "application/json" `
    -Body $body |
    ConvertTo-Json -Depth 10
```
## DELETE an application
```powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/4" `
    -Method Delete
```

## CREATE Interview Request 
```powershell
$body = @{
    scheduledAt = "2026-09-02T14:30:00"
    notes       = "Technical interview with engineering team"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/3/interviews" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body |
    ConvertTo-Json -Depth 10
```

## GET Interview Request
```powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/3/interviews/1" `
    -Method Get |
    ConvertTo-Json -Depth 10
```

## UPDATE Interview Request
```powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/3/interviews/2" `
    -Method Put `
    -ContentType "application/json" `
    -Body $body |
    ConvertTo-Json -Depth 10
```
## DELETE Interview Request
```powershell
Invoke-RestMethod `
    -Uri "http://localhost:8080/applications/3/interviews/2" `
    -Method Delete
```

## THROW 400 exception with JSON response
```powershell

try {
    Invoke-RestMethod `
        -Uri "http://localhost:8080/applications" `
        -Method Post `
        -ContentType "application/json" `
        -Body $body
}
catch {
    $response = $_.Exception.Response
    $reader = New-Object System.IO.StreamReader($response.GetResponseStream())
    $json = $reader.ReadToEnd()

    $json |
        ConvertFrom-Json |
        ConvertTo-Json -Depth 10
}
```

## Connect to PostgreSQL

``` powershell
psql -U jobapptracker -d jobapptracker
```

Useful commands after entering `psql`:

``` text
\dt
\d job_application
\d interview
SELECT * FROM job_application;
SELECT * FROM interview;
\q
```

## PostgreSQL client check

``` powershell
psql --version
```

## Java/JDK checks

``` powershell
java --version
javac -version
javap -version
```

## Inspect the compiled JobApplication class

Run after compiling:

``` powershell
javap -p .\target\classes\com\example\jobapptracker\application\JobApplication.class
```
