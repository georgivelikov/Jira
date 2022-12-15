# Jira Integration

Run JiraIntegration.jar with the following command (Java 17 is required):
<br />
java -jar JiraIntegration.jar json 100 .\repository 5
<br />
<br />
The starting parameters are:
<br />
1st parameter - json/xml - the type of the persisted data
<br />
2nd parameter - integer - from Jira API - the maxResults parameter, indicating the fetched size of issues
<br />
3rd parameter - location on the file system where issues will be persisted
<br />
4the parameter - integer - interval in seconds between each fetch call
<br />
<br />
The example command will collect 100 issues per call, with 5 seconds interval between calls. The issues will be 
persisted in json format in directory '.\repository'
<br />
<br />
If one or more command line arguments are missing or in wrong format exception will be thrown
<br />
<br />
If all command line arguments are missing, the default configuration will be loaded from local.conf 



