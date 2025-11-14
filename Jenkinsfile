pipeline {
  agent any
  options { timestamps(); buildDiscarder(logRotator(numToKeepStr: '30')) }

  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/main']],
                  userRemoteConfigs: [[url: 'https://github.com/abhinavdagarpolestarllp/PlaformOne.git']]])
      }
    }

    stage('Build & Run Sanity') {
      steps {
        bat 'mvn -B clean test -DsuiteXmlFile=SanityTest.xml'
      }
    }

    stage('Debug workspace (list files)') {
      steps {
        bat '''
        echo ==== WORKSPACE ====
        echo %WORKSPACE%
        echo ==== top-level ====
        dir /b
        echo ==== Run Results tree ====
        if exist "Run Results" ( dir /s "Run Results" ) else ( echo "NO Run Results folder" )
        echo ==== target tree ====
        if exist target ( dir /s target ) else ( echo "NO target folder" )
        echo ==== locate index.html under workspace ====
        for /r %cd% %%f in (index.html) do @echo "FOUND:" %%f
        '''
      }
    }

    stage('Collect Extent Report (robust)') {
      steps {
        // prepare target folder
        bat 'if not exist target\\extent-reports mkdir target\\extent-reports'

        // Robust PowerShell: find any index.html that looks like an Extent report inside a "Report" folder under Run Results,
        // or fallback to the most recently modified "Report" folder anywhere under workspace.
        bat '''
        powershell -NoProfile -Command ^
          "$ws=(Get-Location).Path; ^
           Write-Host 'Workspace:' $ws; ^
           $candidates = @(); ^
           # 1) Look for Run Results\Run-*\Report\index.html
           $rr = Join-Path $ws 'Run Results'; ^
           if (Test-Path $rr) { ^
             Get-ChildItem -Path $rr -Directory -ErrorAction SilentlyContinue | ForEach-Object { ^
               $rep = Join-Path $_.FullName 'Report'; if (Test-Path $rep) { if (Test-Path (Join-Path $rep 'index.html')) { $candidates += (Join-Path $rep 'index.html') } } ^
             } ^
           } ^
           # 2) global search for any index.html that contains 'Extent' in content (fast heuristic)
           if ($candidates.Count -eq 0) { ^
             $found = Get-ChildItem -Path $ws -Filter 'index.html' -Recurse -ErrorAction SilentlyContinue | Select-Object -First 20; ^
             foreach ($f in $found) { ^
               try { $txt = Get-Content $f.FullName -Raw -ErrorAction SilentlyContinue; if ($txt -match 'Extent' -or $txt -match 'Automation Test Report') { $candidates += $f.FullName } } catch {} ^
             } ^
           } ^
           # 3) fallback: newest Report folder anywhere
           if ($candidates.Count -eq 0) { ^
             $repDirs = Get-ChildItem -Path $ws -Directory -Recurse -ErrorAction SilentlyContinue | Where-Object { $_.Name -ieq 'Report' } | Sort-Object LastWriteTime -Descending; ^
             if ($repDirs.Count -gt 0) { $candidates += (Join-Path $repDirs[0].FullName 'index.html') } ^
           } ^
           if ($candidates.Count -eq 0) { Write-Host 'No report index.html found. Candidates empty.'; exit 0 } ^
           Write-Host 'Candidates:'; $candidates | ForEach-Object { Write-Host $_ } ; ^
           $selected = $candidates[0]; Write-Host 'Selected:' $selected; ^
           $repDir = Split-Path $selected -Parent; Write-Host 'Report directory:' $repDir; ^
           $target = Join-Path $ws 'target\\extent-reports'; if (-Not (Test-Path $target)) { New-Item -ItemType Directory -Path $target | Out-Null } ; ^
           # copy report files
           Copy-Item -Path (Join-Path $repDir '*') -Destination $target -Recurse -Force; ^
           # attempt to copy sibling Screenshot folder if exists
           $siblingSS = Join-Path (Split-Path $repDir -Parent) 'Screenshot'; ^
           if (Test-Path $siblingSS) { $ssTarget = Join-Path $target 'screenshots'; if (-Not (Test-Path $ssTarget)) { New-Item -ItemType Directory -Path $ssTarget | Out-Null } ; Copy-Item -Path (Join-Path $siblingSS '*') -Destination $ssTarget -Recurse -Force } ^
           # also copy any Screenshot folder under Report folder
           $ssInReport = Join-Path $repDir 'Screenshot'; if (Test-Path $ssInReport) { $ssTarget = Join-Path $target 'screenshots'; if (-Not (Test-Path $ssTarget)) { New-Item -ItemType Directory -Path $ssTarget | Out-Null } ; Copy-Item -Path (Join-Path $ssInReport '*') -Destination $ssTarget -Recurse -Force } ^
           Write-Host 'Copied report and screenshots to' $target; ^
           Get-ChildItem -Path $target -Recurse | Select-Object FullName,Length | ForEach-Object { Write-Host $_.FullName } ^
        '''
        // show what we copied
        bat 'dir /s target\\extent-reports || echo "target\\extent-reports not found after copy"'
      }
    }

    stage('Archive & Publish Extent') {
      steps {
        // Archive everything so raw HTML + screenshots are downloadable
        archiveArtifacts artifacts: 'target/extent-reports/**', fingerprint: true, allowEmptyArchive: false

        // Publish the HTML report (requires HTML Publisher Plugin)
        publishHTML(target: [
          reportDir: 'target/extent-reports',
          reportFiles: 'index.html',
          reportName: 'Extent Report',
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true
        ])
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
    }
  }
}
