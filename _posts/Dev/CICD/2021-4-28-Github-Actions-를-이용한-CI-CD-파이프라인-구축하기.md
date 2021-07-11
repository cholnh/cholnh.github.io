---
layout: post
title:  "Github Actions 를 이용한 CI/CD 파이프라인 구축하기"
categories: CICD
tags: Github-Actions CI/CD
comments: true
---

#### 개요

Github Actions 는 Github 에서 제공하는 소프트웨어 개발 Workflow 를 자동화하는 도구입니다.  
CI, CD 파이프라인은 빌드/배포 과정 동안 수행해야 할 테스크가 정의된 것입니다.  
Github Actions 를 통해 CI/CD 파이프라인을 Workflow 에 정의하여 자동화하는 과정을 살펴보겠습니다.

<br/>

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/3/msa-pattern-cicd-pipeline.png"/>
  <div>CI/CD 파이프라인</div>
</div>

<hr/><br/>

### 사용 도구
Github Actions 를 이용한 CI/CD 파이프라인 구축하기 위해서 다음과 같은 도구들을 사용합니다.

- Github Repository
- Docker 이미지 (app 포함)
- GCE (Google Compute Engine) 인스턴스
- GCR (Google Container Registry)
- Slack Repository

GCE 인스턴스 생성 및 Docker 이미지 관리에 대해서는 아래링크를 참조바랍니다.  
[GCE 위에 모놀리식 스프링부트 실행시키기]({{ site.url }}/gcp/gce-위에-모놀리식-스프링부트-실행시키기.html)

<br/><br/>

### 간단한 Workflow 만들기
Github actions 에서 제공하는 Workflow 는 Github 저장소에 저장된 소스코드를 이용한  
`Build`, `Test`, `Release`, `Deploy` 등 다양한 이벤트를 자동화할 수 있습니다.  

<br/>

Workflow 는 Runners 라 불리는 **Github 에서 호스팅된 환경(Linux, macOS, Windows 등)에서** 실행됩니다.  
(사용자가 직접 호스팅하는 환경에서도 구동이 가능합니다. self hosted runner)

<br/>

또한 다른 사람들이 제작한 Workflow 가 [Github Marketplace](https://github.com/marketplace) 에 공유되어 있습니다.  
Github 을 통해 공식인증된 Workflow 를 사용할 수도 있고, 커스텀된 Workflow 를 제작할 수도 있습니다.  
간단한 커스텀 Workflow 를 만들어보겠습니다.

<br/>

Workflow 만드는 방법은 간단합니다.  
우선 자신의 프로젝트가 저장되어 있는 Github Repository 에 `.yml` 파일을 하나 생성합니다.  
위치는 프로젝트 폴더의 최상위에 `.github/workflow/*.yml` 로 위치시킵니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-file-location.png"/>
  <div>workflow 파일 생성</div>
</div>

<br/>

위 방법처럼 직접 파일을 생성하지 않아도 됩니다.  
Github 에서 제공하는 Actions UI 를 이용해 보겠습니다.

<br/>

Github Repository 탭에 `Actions` 메뉴로 들어갑니다.  
왼쪽에 `New workflow` 를 눌러 새로운 workflow 를 만들 수 있습니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-actions-menu.png"/>
  <div>workflow 파일 생성</div>
</div>

<br/>

`set up a workflow yourself` 를 눌러 커스텀 workflow 파일을 생성합니다.  

<br/>

파일 내용은 다음을 복사합니다.

```yaml
name: Java Gradle Build

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      # "master" 체크아웃
      - name: Checkout
        uses: actions/checkout@v2

      # Java + Gradle 기반 앱 테스트 및 빌드
      - name: Set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8

      - name: Source Code Test And Build
        run: |
          chmod +x gradlew
          ./gradlew build
```

<br/>

위 명령어를 하나하나 살펴보겠습니다.

- `name`  
    workflow 의 이름을 명시합니다.
    
    ```yaml
    name: Java Gradle Build
    ```
    
<br/>

- `on`  
    workflow 의 이벤트 조건을 명시합니다.  
    `push` 또는 `pull request` 동작시 작동하게 하거나 `cron` 문법을 이용해 시간을 설정할 수도 있습니다.
    
    ```yaml
    on:
      schedule:
        - cron: '*/10 * * * *'
      push:
        branches: [ master ]
      pull_request:
        branches: [ master ]
    ```

    또한 `paths` 를 이용하여 특정 패턴에 해당하는 파일이 변경되었을 때 트리거가 되도록할 수 있습니다.  
    
    ```yaml
    on:
      push:
        branches: [ master, dev ]
        paths:
          - "**.java"
          - "**.js"
        paths-ignore:
          - "doc/**"
          - "**.md"
    ```
    
<br/>

- `jobs`  
    job 은 기본 실행 단위입니다.  
    job 들은 모두 병렬적으로 실행됩니다.
    
    ```yaml
    jobs:
      some-job-id:
        ...
      some-job-id-2:
        ...
    ```
    
    + `runs-on`  
        해당 job 을 가상 실행할 컴퓨팅 환경인 `runner` 를 명시합니다.  
        ubuntu-latest, macos-latest, windows-latest 등으로 실행 가능합니다.
        
        ```yaml
        jobs:
          some-job-1:
            runs-on: ubuntu-latest
        ```
        
    <br/>
    
    + `strategy`  
        여러 환경에서의 실행을 위해 build matrix 를 설정합니다.  
        각기 다른 환경들을 명시하여 같은 job 을 동시에 실행할 수 있습니다.  
        ```yaml
        jobs:
          some-job-1:
            strategy:
              matrix:
                node-version: [10.x, 12.x]
        ```
        
    <br/>
    
    + `steps`  
        job 내부 실행 과정을 명시합니다.  
        + Github 저장소 코드 체크아웃
        + Github Marketplace 에서 import 한 actions 실행
        + shell 명령어 실행
        + 도커 이미지 빌드/배포
        + aws/gcp 등의 인프라에 서비스 배포 등
        
        각 step 은 job 의 컴퓨팅 자원에서 독립적인 프로세스로 동작하며,  
        여러가지 명령들을 순차적으로 실행합니다.  
        (또한 runner 의 파일 시스템에 접근할 수 있습니다)
        
        ```yaml
        jobs:
          some-job-1:
            runs-on: ubuntu-latest
            steps:
              # Github 저장소에 저장된 소스코드를 체크아웃
              - name: Checkout
                uses: actions/checkout@v2
        
              # Java + Gradle 기반 앱 테스트 및 빌드
              - name: Set up JDK 1.8
                uses: actions/setup-java@v1
                with:
                  java-version: 1.8
        
              - name: Source Code Test And Build
                run: |
                  chmod +x gradlew
                  ./gradlew build
        ```
        
        위 workflow 는 Github 에 저장된 소스코드를 체크아웃하고 gradlew 를 통해 build 작업이 순차적으로 실행됩니다.
        
        <br/>
        
        + `steps.name`  
            step 의 이름을 명시합니다.  
            
        <br/>
        
        + `steps.uses`  
            해당 스텝에서 사용할 action 을 명시합니다.  
            [Github Marketplace](https://github.com/marketplace) 에 많은 action 들이 있습니다.  
            
        <br/>
        
        + `steps.run`  
            runner 의 shell 을 이용하여 명시된 명령어 나열을 실행합니다.  
            
            ```yaml
            jobs:
              some-job-1:
                steps:
                  - name: My First Step
                    run: | ## 명령어를 여러 줄 사용하기 위해서는 파이프(|) 를 입력합니다.
                      npm install
                      npm test
                      npm build
            ```

<br/>

이외에 Github Actions 문법은 공식문서를 통해 확인할 수 있습니다.  
[Workflow syntax for GitHub Actions](https://docs.github.com/en/actions/reference/workflow-syntax-for-github-actions)

<br/>

이제 복사한 내용을 `commit` 하면 자동으로 Action workflow 가 실행됩니다.  
Actions UI 에서 workflow 실행 과정이 모니터링 됩니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-workflow-ui.png"/>
  <div>workflow 실행 결과</div>
</div>

<br/><br/>

### CI/CD 파이프라인 Workflow
기본적인 Github Actions 내용을 숙지했다면 CI/CD 자동화 파이프라인을 구축해보겠습니다.

<br/>

Workflow 는 다음과 같습니다.  

```yaml
name: Build-Deploy

on:
  push:
    branches: [ master ]
    paths-ignore:
      - "**.md"
  pull_request:
    branches: [ master ]
    paths-ignore:
      - "**.md"

env:
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
  SLACK_WEBHOOK_URL: ${{ secrets.SLACK_WEBHOOK_URL }}

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      # "master" 체크아웃
      - name: Checkout
        uses: actions/checkout@v2

      # Java + Gradle 기반 앱 테스트 및 빌드
      - name: Set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8

      - name: Source Code Test And Build
        run: |
          chmod +x gradlew
          ./gradlew build

      # Gcloud CLI 세팅
      - name: Set up Gcloud
        uses: google-github-actions/setup-gcloud@master
        with:
          version: '290.0.1'
          service_account_key: ${{ secrets.GCP_SA_KEY }}
          project_id: ${{ secrets.GCP_PROJECT_ID }}
          export_default_credentials: true

      # GCR 연결 위한 인증 작업 실행
      - name: Set Auth GCR
        run: gcloud --quiet auth configure-docker

      # GCR에서 이전 버전 참고하여 다음 버전 만든 후, 이미지 빌드 및 푸쉬
      - name: Build Docker Image And Delivery To GCR
        run: |
          IMAGE=gcr.io/${{ secrets.GCP_PROJECT_ID }}/${{ secrets.REPOSITORY_NAME }}
          INPUT=$(gcloud container images list-tags --format='get(tags)' $IMAGE)
          LATEST_TAG=$(echo ${INPUT[0]} | awk -F ' ' '{print $1}' | awk -F ';' '{print $1}')
          ADD=0.01
          VERSION=$(echo "${LATEST_TAG} $ADD" | awk '{print $1 + $2}')
          NEW_VERSION=$(printf "%.2g\n" "${VERSION}")
          docker build --tag $IMAGE:${NEW_VERSION} .
          docker push $IMAGE:${NEW_VERSION}
          docker tag $IMAGE:${NEW_VERSION} $IMAGE:latest
          docker push $IMAGE:latest

      # 작업 결과 슬랙 전송
      - name: Result to Slack
        uses: 8398a7/action-slack@v3
        with:
          status: ${{ job.status }}
          fields: repo,message,commit,author,action,eventName,ref,workflow,job,took
          author_name: MSA Build Result
        if: always()

  deploy:
    needs: [ build ]
    runs-on: ubuntu-latest

    steps:
      # SSH 접속을 통한 직접 배포
      - name: Deploy to GCE
        uses: appleboy/ssh-action@master
        with:
          host: ${{ secrets.SSH_HOST }}
          username: ${{ secrets.SSH_USERNAME }}
          key: ${{ secrets.SSH_KEY }}
          passphrase: ${{ secrets.SSH_PASSPHRASE }}
          script: |
            CONTAINER_NAME=hello-container
            IMAGE=gcr.io/${{ secrets.GCP_PROJECT_ID }}/${{ secrets.REPOSITORY_NAME }}
            sudo gcloud --quiet auth configure-docker
            sudo docker ps -q --filter "name=$CONTAINER_NAME" | grep -q . && sudo docker stop $CONTAINER_NAME
            sudo docker system prune -a -f
            sudo docker run -d --name "$CONTAINER_NAME" --rm -p ${{ secrets.CONTAINER_PORT }}:${{ secrets.CONTAINER_PORT }} $IMAGE:latest

      # 작업 결과 슬랙 전송
      - name: Result to Slack
        uses: 8398a7/action-slack@v3
        with:
          status: ${{ job.status }}
          fields: repo,message,commit,author,action,eventName,ref,workflow,job,took
          author_name: MSA Deploy Result
        if: always()
```

<br/>

위 명령어 하나하나 살펴보겠습니다.  
우선 트리거 조건을 명시하는 `on` 부분입니다.  
`paths-ignore` 를 통해 commit 시 무시되는 파일 확장자를 명시합니다.

```yaml
on:
  push:
    branches: [ master ]
    paths-ignore:
      - "**.md"
  pull_request:
    branches: [ master ]
    paths-ignore:
      - "**.md"
```

<br/>

`env` 는 workflow 내부에서 사용되는 환경변수입니다.  
아래 action 에서 사용될 각 변수들에 값을 넣어줍니다.  
`${{ secrets.시크릿변수 }}` 문법을 통해 `Github Secret` 에서 설정한 시크릿을 가져올 수 있습니다.  
`GITHUB_TOKEN`, `SLACK_WEBHOOK_URL` 변수들에 대한 설명은 아래에서 다루겠습니다.

```yaml
env:
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
  SLACK_WEBHOOK_URL: ${{ secrets.SLACK_WEBHOOK_URL }}
```

<br/>

runner 환경은 리눅스 `ubuntu` 를 사용하여 실행되게 합니다.

```yaml
runs-on: ubuntu-latest
```
 
<br/>

Github 저장소에서 소스코드를 체크아웃 합니다.  
체크아웃된 코드는 runner 의 가상 실행 환경에 위치됩니다.

```yaml
# "master" 체크아웃
- name: Checkout
  uses: actions/checkout@v2
```

<br/>

해당 프로젝트는 Java 프로젝트를 기반으로 진행되므로 `JDK 1.8` 환경을 runner 에 세팅해줍니다.  
체크아웃 한 코드를 `gradlew` 를 통해 Test 및 Build 작업을 실행합니다.

```yaml
# Java + Gradle 기반 앱 테스트 및 빌드
- name: Set up JDK 1.8
  uses: actions/setup-java@v1
  with:
    java-version: 1.8

- name: Source Code Test And Build
  run: |
    chmod +x gradlew
    ./gradlew build
```

<br/>

GCP 를 제어하기 위한 Gcloud CLI 를 세팅합니다.  
`service_account_key` 와 `project_id` 는 GCP 인증을 위해 GCP 서비스 계정에서 받아와 설정해줘야 하는 정보들입니다.  
Secret 설정은 아래에서 자세하게 다루겠습니다.

```yaml
# Gcloud CLI 세팅
- name: Set up Gcloud
  uses: google-github-actions/setup-gcloud@master
  with:
    version: '290.0.1'
    service_account_key: ${{ secrets.GCP_SA_KEY }}
    project_id: ${{ secrets.GCP_PROJECT_ID }}
    export_default_credentials: true
```

<br/>

GCR (Google Container Registry) 은 GCP 에서 제공하는 컨테이너 이미지 레지스트리(저장소) 입니다.  
Docker 이미지를 빌드하여 저장하기 위해 GCR 을 사용합니다.  
아래 명령어는 GCR 연결을 위한 선 인증 작업을 실행합니다.  
`run` 명령으로 실행되는 `gcloud` 사용을 위해 위에서 Gcloud CLI 세팅작업이 선결되어야 합니다.

```yaml
# GCR 연결 위한 인증 작업 실행
- name: Set Auth GCR
  run: gcloud --quiet auth configure-docker
```

<br/>

shell 명령을 사용하여 GCR 에 도커 이미지를 push 하는 과정입니다.  
GCR 에 저장되어 있는 최근 이미지의 버전을 얻어와 버전업한 후 도커 이미지를 빌드하여 저장소로 푸시하는 과정이 이뤄집니다.

```yaml
# GCR에서 이전 버전 참고하여 다음 버전 만든 후, 이미지 빌드 및 푸쉬
- name: Build Docker Image And Delivery To GCR
  run: |
    IMAGE=gcr.io/${{ secrets.GCP_PROJECT_ID }}/${{ secrets.REPOSITORY_NAME }}
    INPUT=$(gcloud container images list-tags --format='get(tags)' $IMAGE)
    LATEST_TAG=$(echo ${INPUT[0]} | awk -F ' ' '{print $1}' | awk -F ';' '{print $1}')
    ADD=0.01
    VERSION=$(echo "${LATEST_TAG} $ADD" | awk '{print $1 + $2}')
    NEW_VERSION=$(printf "%.2g\n" "${VERSION}")
    docker build --tag $IMAGE:${NEW_VERSION} .
    docker push $IMAGE:${NEW_VERSION}
    docker tag $IMAGE:${NEW_VERSION} $IMAGE:latest
    docker push $IMAGE:latest
```

<br/>

Slack 채널로 해당 작업 결과를 전송합니다.  

```yaml
# 작업 결과 슬랙 전송
- name: Result to Slack
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    fields: repo,message,commit,author,action,eventName,ref,workflow,job,took
    author_name: MSA Build Result
  if: always()
```

<br/>

GCE (Google Compute Engine) 로 SSH 접속하여 도커 이미지를 pull 하여 컨테이너에 어플리케이션이 포함된 이미지를 실행합니다.  
`script` 부분은 SSH 접속후 해당 컨테이너에서 실행할 스크립트를 명시합니다.  
컨테이너 내부에서 GCR 접속을 위해 `gcloud --quiet auth configure-docker` 명령을 통해 접근 권한을 얻습니다.  
기존 컨테이너를 정지/삭제한 뒤 버전업된 새로운 이미지를 받아와 실행시킵니다.  

```yaml
# SSH 접속을 통한 직접 배포
- name: Deploy to GCE
  uses: appleboy/ssh-action@master
  with:
    host: ${{ secrets.SSH_HOST }}
    username: ${{ secrets.SSH_USERNAME }}
    key: ${{ secrets.SSH_KEY }}
    passphrase: ${{ secrets.SSH_PASSPHRASE }}
    script: |
      CONTAINER_NAME=hello-container
      IMAGE=gcr.io/${{ secrets.GCP_PROJECT_ID }}/${{ secrets.REPOSITORY_NAME }}
      sudo gcloud --quiet auth configure-docker
      sudo docker ps -q --filter "name=$CONTAINER_NAME" | grep -q . && sudo docker stop $CONTAINER_NAME
      sudo docker system prune -a -f
      sudo docker run -d --name "$CONTAINER_NAME" --rm -p ${{ secrets.CONTAINER_PORT }}:${{ secrets.CONTAINER_PORT }} $IMAGE:latest
```

위 내용대로 workflow 설정파일을 생성합니다.   
이제 내부 환경변수에 들어갈 `Secret` 값들을 설정해보겠습니다.

<br/><br/>

### Github Secret 설정
Github 에서는 프로젝트 내에서 사용되는 민감한 정보(가령 보안과 관련된)들을 안전하게 관리해줍니다.  

<br/>

우선 Github Repository 에 `Setting` - 왼쪽 메뉴 중 `Secrets` 에 들어갑니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-menu.png"/>
  <div>Github Secret</div>
</div>

<br/>

오른쪽 상단의 `New repository secret` 을 눌러 새로운 secret 을 생성합니다.  
`Name` 부분에 secret 변수명을 입력하고, `Value` 부분에 secret 변수의 값을 입력합니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-new-secret.png"/>
  <div>Github Secret</div>
</div>

<br/>

위 예시와 같이 생성된 secret 은 다음과 같은 형태로 사용됩니다.

```yaml
${{ secrets.MY_SECRET_VALUE }}
```

<br/>

이제 위 workflow 에서 사용된 secret 을 하나하나 살펴보겠습니다.

- `GCP_PROJECT_ID`  
    GCP 프로젝트 생성 당시 발급된 고유 ID 를 등록합니다.  
    
    |Secret Name|Secret Value|
    |-|-|
    |`GCP_PROJECT_ID`|`mindful-folio-309712`|
    
    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-gcp-id.png"/>
      <div>GCP PROJECT ID</div>
    </div>
    
<br/>
    
- `GCE_INSTANCE_NAME`  
    GCE 인스턴스 이름을 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`GCE_INSTANCE_NAME`|`instance-test-2`|
   
    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-instance-name.png"/>
      <div>GCE INSTANCE NAME</div>
    </div>
   
<br/>
 
- `GCE_INSTANCE_ZONE`  
    GCE 인스턴스 영역을 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`GCE_INSTANCE_ZONE`|`asia-northeast3-a`|

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-instance-zone.png"/>
      <div>GCE INSTANCE ZONE</div>
    </div>
   
<br/>

- `GCP_SA_KEY`  
    GCP 프로젝트의 서비스 계정(Service Account) 를 등록합니다.  
    외부에서 GCP 연결을 위해 `gcloud cli` 를 이용합니다.  
    이때 서비스 계정을 통해 인증 과정을 거치게 됩니다. 
    
    <br/>
    
    우선 왼쪽 메뉴의 `IAM 및 관리자` - `서비스 계정` 으로 들어갑니다.
    
    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-sa-menu.png"/>
      <div>GCP SA</div>
    </div>
    
    <br/>
    
    자신의 GCE 인스턴스 아이디에 해당하는 항목의 맨 오른쪽 점 세개 `작업` 부분을 누르고 `키 관리` 를 선택합니다.  
    
    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-sa-main.png"/>
      <div>GCP SA</div>
    </div>
    
    <br/>
    
    왼쪽 `키 추가` - `새 키 만들기` - `JSON` 을 선택한 뒤 - `만들기` 를 눌러 SA 키를 생성합니다.  

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-secret-sa-key.png"/>
      <div>GCP SA</div>
    </div>
    
    <br/>
    
    이때, 자동으로 다운로드 되는 파일 내용을 `base64` 인코딩을 한 후 `secret` 에 등록합니다.  
    인코딩 변환 방법은 다음과 같습니다.
        
    + 인코딩 변환 사이트 : [https://www.base64decode.org/](https://www.base64decode.org/) 
    + 윈도우 명령 프롬프트 : `certutil -encode [sa key 경로] [인코딩 결과 경로]`
    + 리눅스 : `base64 [sa key 경로] > [인코딩 결과 경로]`
    + macOS : `base64 -i [sa key 경로] -o [인코딩 결과 경로]`
    
    <br/>
    
    |Secret Name|Secret Value|
    |-|-|
    |`GCP_SA_KEY`|`[ base64 인코딩된 SA KEY ]`|
    
<br/>

- `REPOSITORY_NAME`  
    해당 프로젝트의 Github Repository 저장소 이름을 등록합니다.  
    
    |Secret Name|Secret Value|
    |-|-|
    |`REPOSITORY_NAME`|`msa-helloworld`|
    
<br/>

- `CONTAINER_PORT`  
    Docker 컨테이너에서 EXPOSE 될 포트를 지정합니다.  
    기본 어플리케이션 작동 테스트가 목적이므로 `8080` 값을 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`CONTAINER_PORT`|`8080`|
    
<br/>

- `SSH_HOST`  
    GCE 인스턴스에 SSH 접속을 위한 값입니다.  
    GCE 인스턴스 메인화면에 적혀져있는 `외부 IP` 를 등록합니다. 
    
    |Secret Name|Secret Value|
    |-|-|
    |`SSH_HOST`|`34.64.154.29`|
    
<br/>

- `SSH_USERNAME`  
    GCP 프로젝트를 생성한 자신의 구글 ID 를 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`SSH_USERNAME`|`nzzi.dev`|
    
<br/>

- `SSH_KEY`  
    GCE 인스턴스 SSH 접속 역시 인증과정에 필요한 KEY 등록이 필요합니다.  
    
    <br/>
    
    우선 터미널 또는 명령 프롬프트에 다음을 입력합니다.
    
    `
    $ ssh-keygen -t rsa -f [KEY 경로] -C "[유저 아이디@gmail.com]" 
    `
    
    예시  
        
    ```
    ssh-keygen -t rsa -f ~/.ssh/rsa-gcp-key -C "nzzi.dev@gmail.com" 
    ```
    
    <br/>
    
    비밀번호(passphrase) 등록 또한 해줍니다.
    
    <br/>
    
    이때 생성된 `private key` 를 `secret` 으로 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`SSH_KEY`|`[ SSH Private KEY ]`|
    
    <br/>
    
    여기서 끝이 아닙니다.  
    생성된 `public key` 를 gcp 메타데이터에 등록해줘야 합니다.
    
    <br/>
    
    우선 GCP 왼쪽 메뉴에서 메타데이터 탭으로 들어가줍니다.  

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/gcp/gce-msa/gcp-metadata-menu.png"/>
      <div>GCP 메타데이터 메뉴</div>
    </div>
    
    `SSH 키` 탭을 선택해주고 `수정` - `항목 추가`를 선택해줍니다.  
    위에서 생성한 `public key` 내용을 복사합니다.  
    
    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/gcp/gce-msa/gcp-metadata-input.png"/>
      <div>GCP SSH 공개키 등록 예시</div>
    </div>
    
    <br/>
    
    다른 플랫폼에서의 접속방법은 다음을 참고바랍니다.  
    [GCE 인스턴스 컨테이너에 접속하기]({{ site.url }}/gcp/gce-위에.모놀리식-스프링부터-실행시키기.html)
    
<br/>

- `SSH_PASSPHRASE`  
    위 비밀번호 등록 과정에서 입력한 `passphrase` 를 등록합니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`SSH_PASSPHRASE`|`[ SSH PASSPHRASE ]`|
    
<br/>

- `SLACK_WEBHOOK_URL`  
    [SLACK](https://slack.com/intl/ko-kr/) 에 빌드 결과를 알림하기 위한 알림 URL 을 등록합니다.  
    
    <br/>
    
    우선 슬랙에 가입을 하고 워크스페이스를 생성해줍니다.  
    (기존 워크스페이스도 상관없습니다)
    
    <br/>
    
    결과를 전송받을 채널을 지정한 후 (기본 채널도 상관없습니다)  
    [Slack API 를 관리하는 곳](https://api.slack.com/apps)으로 이동합니다.
    
    <br/>
    
    `Create New App` 버튼을 눌러줍니다.

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-slack-main.png"/>
      <div>SLACK Create New App</div>
    </div>
    
    <br/>
    
    `From scratch` 선택한 후 적절한 App 이름을 적고 workspace 를 선택해줍니다.

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-slack-newapp.png"/>
      <div>SLACK Create New App</div>
    </div>
    
    <br/>
    
    `Incoming Webhooks` 을 선택하면 웹훅 URL 이 생성됩니다.

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-slack-webhook-menu.png"/>
      <div>SLACK Incoming Webhooks</div>
    </div>
    
    <br/>
    
    우측 상단에 토글 버튼을 `On` 으로 바꿔주고 아래에 `Webhook URL` 복사해준 뒤,  
    `Add New Webhook to Workspace` 를 눌러 워크스페이스에 웹훅을 추가해줍니다.

    <div class="nzzi-image-box">
      <img src="{{ site.url }}/assets/diy/cicd/cicd-slack-webhook-on.png"/>
      <div>SLACK Add New Webhooks</div>
    </div>
    
    <br/>
    
    복사한 `Webhook URL` 은  `secret` 에 등록해줍니다.
    
    |Secret Name|Secret Value|
    |-|-|
    |`SLACK_WEBHOOK_URL`|`https://hooks.slack.com/services/ASDASDASD/ZXCZXCZXC/QWEQWEQWE`|
    
<br/><br/>

### Docker file
Spring boot 로 제작한 자바 어플리케이션을 실행시키는 간단한 도커 이미지 입니다.

```dockerfile
FROM java:8
VOLUME /helloworldVolume
EXPOSE 8080
ARG JAR_FILE=build/libs/helloworld-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

<br/>

해당 파일을 Github 저장소 최상위에 위치시킵니다.

<br/><br/>

### 동작 확인
commit 한 내용들을 저장소에 `push` 할 경우,  
workflow 의 `on` 트리거 조건에 detection 되어 workflow 가 자동 실행됩니다.

<br/>

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-result-ui-1.png"/>
  <div>Workflow 실행 결과</div>
</div>

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-result-ui-2.png"/>
  <div>Workflow 실행 결과</div>
</div>

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/diy/cicd/cicd-result-ui-3.png"/>
  <div>Workflow 실행 결과</div>
</div>
