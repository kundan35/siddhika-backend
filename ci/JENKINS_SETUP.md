# Jenkins CI/CD Setup Guide

This guide walks you through setting up Jenkins for Siddhika projects.

## Quick Start with Docker

### 1. Start Jenkins and PostgreSQL

```bash
cd ci
docker-compose up -d
```

This starts:
- **Jenkins** at http://localhost:8081
- **PostgreSQL** at localhost:5432

### 2. Get Initial Admin Password

```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### 3. Access Jenkins

1. Open http://localhost:8081
2. Enter the admin password from step 2
3. Click "Install suggested plugins"
4. Create your admin user

## Configure Jenkins

### Required Plugins

Install these plugins via **Manage Jenkins > Plugins > Available**:

| Plugin | Purpose |
|--------|---------|
| **Pipeline** | Jenkinsfile support |
| **Git** | Git SCM integration |
| **GitHub** | GitHub webhooks & authentication |
| **JUnit** | Test result publishing |
| **Gradle** | Gradle build support |
| **Docker Pipeline** | Docker in pipelines |

### Configure JDK

1. Go to **Manage Jenkins > Tools**
2. Under **JDK installations**, click **Add JDK**
3. Name: `JDK17`
4. Check "Install automatically"
5. Select **AdoptOpenJDK 17** or similar
6. Save

### Configure Android SDK (for Siddhika App)

For Android builds, you need the Android SDK inside Jenkins:

```bash
# Enter Jenkins container
docker exec -it jenkins bash

# Install Android SDK
apt-get update && apt-get install -y wget unzip
mkdir -p /opt/android-sdk/cmdline-tools
cd /opt/android-sdk/cmdline-tools
wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip commandlinetools-linux-9477386_latest.zip
mv cmdline-tools latest

# Accept licenses and install build tools
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

exit
```

## Create Pipeline Jobs

### Backend Pipeline

1. Click **New Item**
2. Name: `siddhika-backend`
3. Select **Pipeline**, click OK
4. In **Pipeline** section:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/kundan35/siddhika-backend.git`
   - Branch: `*/main`
   - Script Path: `ci/Jenkinsfile`
5. Save

### App Pipeline

1. Click **New Item**
2. Name: `siddhika-app`
3. Select **Pipeline**, click OK
4. In **Pipeline** section:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/kundan35/Siddhika.git`
   - Branch: `*/main`
   - Script Path: `ci/Jenkinsfile`
5. Save

## Configure GitHub Webhooks (Optional)

For automatic builds on push:

### 1. Generate GitHub Token

1. Go to GitHub > Settings > Developer settings > Personal access tokens
2. Generate new token with `repo` and `admin:repo_hook` scopes
3. Copy the token

### 2. Add Credentials in Jenkins

1. Go to **Manage Jenkins > Credentials > Global**
2. Add credentials:
   - Kind: **Secret text**
   - Secret: your GitHub token
   - ID: `github-token`

### 3. Configure GitHub in Jenkins

1. Go to **Manage Jenkins > System**
2. Under **GitHub**, add GitHub Server:
   - Credentials: select `github-token`
   - Test connection
3. Save

### 4. Add Webhook in GitHub

1. Go to your GitHub repo > Settings > Webhooks
2. Add webhook:
   - Payload URL: `http://your-jenkins-url:8081/github-webhook/`
   - Content type: `application/json`
   - Events: Just the push event
3. Save

## Pipeline Stages

### Backend Pipeline

```
Checkout → Build → Test → Code Analysis → Build JAR
```

| Stage | Description |
|-------|-------------|
| **Checkout** | Clone repository |
| **Build** | Compile Kotlin code |
| **Test** | Run unit tests |
| **Code Analysis** | Run checks |
| **Build JAR** | Create deployable JAR |

### App Pipeline

```
Checkout → Build Shared → Test Shared → Build Android → Build Desktop
```

| Stage | Description |
|-------|-------------|
| **Checkout** | Clone repository |
| **Build Shared** | Compile shared KMP module |
| **Test Shared** | Run multiplatform tests |
| **Build Android** | Create debug APK |
| **Build Desktop** | Create desktop distribution |

## Artifacts

After successful builds:

| Project | Artifacts |
|---------|-----------|
| **Backend** | `build/libs/siddhika-backend-*.jar` |
| **App** | `androidApp/build/outputs/apk/debug/*.apk` |
| **Desktop** | `composeApp/build/compose/binaries/` |

## Troubleshooting

### Build fails with "permission denied"

```bash
docker exec jenkins chmod +x /var/jenkins_home/workspace/*/gradlew
```

### Out of memory during build

Increase Docker memory limit or add to `GRADLE_OPTS`:
```
-Xmx4g -XX:MaxMetaspaceSize=512m
```

### Android SDK not found

Ensure `ANDROID_HOME` is set in Jenkins environment:
1. Go to **Manage Jenkins > System**
2. Under **Global properties**, check **Environment variables**
3. Add: `ANDROID_HOME` = `/opt/android-sdk`

## Stop Jenkins

```bash
cd ci
docker-compose down
```

To remove all data:
```bash
docker-compose down -v
```
