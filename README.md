<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->


<!-- PROJECT LOGO -->
<div align="center">
<h3 align="center">TRIP TODO API</h3>
  <p align="center">
    API Server for TRIP TODO, a mobile todolist application that assists you with planning a trip 
  </p>
</div>



<!-- TABLE OF CONTENTS -->



<!-- ABOUT THE PROJECT -->
## About The Project

  TRIP TODO is a mobile todolist application that assists you with planning a trip. This repository builds API server for TRIP TODO. Built with spring boot, java.

### Built With

* [![Spring][Spring]][Spring-url]
* [![Java][Java]][Java-url]
  


<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Get Google Cloud Platform Info
  
  A.  Google Cloud Platform Project id : [Creating and managing projects](https://cloud.google.com/resource-manager/docs/creating-managing-projects)

  
  B.  Google Cloud Platform Service Account Credentials: [Create access credentials](https://developers.google.com/workspace/guides/create-credentials)

  
* Run Frontend Dev Server

  If you want to run a demo app using this api server, please refer to [trip-todo](https://github.com/EAexist/trip-todo).
  



### Installation

1. Get **A. Google Cloud Platfor Project id** and **B. Google Cloud Platform Service Account Credentials**. Refer to **Prerequisites**.
2. Clone the repo
   ```sh
   git clone https://github.com/EAexist/trip-todo-api.git
   ```
3. Create a new file `application.yml` at `src/main/resources` and enter following environment variables.
   ```sh
    spring:
        datasource:
            driver-class-name: org.postgresql.Driver
            url: jdbc:postgresql://localhost:5432/trip-todo
            username: guest
            password: hello_guest
            driver-class-name: org.postgresql.Driver
        jpa:
          hibernate:
            ddl-auto: update
        security:
            oauth2:
                resourceserver:
                    jwt:
                        kakao:
                            issuer-uri: https://kauth.kakao.com
                        google:
                            issuer-uri: https://accounts.google.com
        ai:
            vertex:
                ai:
                    gemini:
                        project-id: trip-todo
                        location: asia-northeast3
                        chat:
                            options:
                                model: gemini-2.0-flash-lite 
        cloud:
            gcp:
                project-id: 'YOUR GOOGLE CLOUD PROJECT ID'
                credentials:
                      location: 'PATH TO YOUR GOOGLE SERVICE ACCOUNT KEY FILE (Place it under src/main/resources)'
    cors:
      allowed-origins: http://localhost:8081 (or the url you're running client app)
   ```
4. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
   ```
5. Run the dev server. 
   ```sh
   bash local.sh
   ```
6. Your server will run in `http://localhost:5000` or on other port if 5000 is occupied. Check the console output for detailed information about the running server.  
   



<!-- CONTRIBUTING -->
## Contributing

We do not approve any code contribution to this repository. However, we welcome any feedbacks and suggestions. If you have a suggestion that would make this better, please feel free to contact us by hyeon.expression@gmail.com. Thanks.  




<!-- LICENSE -->
## License

Distributed under the project_license. See `LICENSE.txt` for more information.  




<!-- CONTACT -->
## Contact

EAexist: hyeon.expression@gmail.com


Project Link: [https://github.com/EAexist/trip-todo-api](https://github.com/EAexist/trip-todo-api)




<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Google Cloud Vision API](https://cloud.google.com/vision/docs)
* [Gemini Developer API](https://ai.google.dev/gemini-api/docs)
* [Spring VertexAI Gemini Chat](https://docs.spring.io/spring-ai/reference/api/chat/vertexai-gemini-chat.html)
* [Amadeus](https://developers.amadeus.com/)
* [Best-README-Template](https://github.com/EAexist/Best-README-Template)



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Spring]: https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white
[Spring-url]: https://docs.spring.io/spring-boot/index.html
[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.oracle.com/java/
