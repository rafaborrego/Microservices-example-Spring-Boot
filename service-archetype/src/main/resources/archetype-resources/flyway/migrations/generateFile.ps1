

$jiraTicket = $args[0]

if ( ! $jiraTicket ) { 
    Write-Host "Please provide the JIRA ticket number" 
} else {

  [xml]$pomXml = Get-Content ..\..\pom.xml
  $version = $pomXml.project.version
  $version = $version.replace('-SNAPSHOT', '').replace('-RELEASE', '')

  $timestamp = Get-Date  -Format yyyyMMddHHmm

  $fileName= 'V' + $version + '_' + 0 + '_' + $timestamp + '__' + $jiraTicket + '.sql'

  New-Item $fileName -ItemType file
}
