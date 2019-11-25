# Changelog of gradle-quality-plugin

## 1.0.3
### No issue

**fix(deploy): snapshot doesn't deploy anymore because of http instead of https, and update gpg key**


[1ae389831a1cb42](https://github.com/ekino/gradle-java-plugin/commit/1ae389831a1cb42) saugereau *2019-11-19 10:27:39*

**feat(quality): prefix sonar projectkey with ekino_**


[d01bd5809d8e951](https://github.com/ekino/gradle-java-plugin/commit/d01bd5809d8e951) saugereau *2019-11-13 08:22:09*

**feat(quality): add sonarcloud.io badge**


[703ac0decf85084](https://github.com/ekino/gradle-java-plugin/commit/703ac0decf85084) saugereau *2019-11-12 15:50:21*

**feat(quality): add junit.reportPaths**


[e7d174aaf44ed7a](https://github.com/ekino/gradle-java-plugin/commit/e7d174aaf44ed7a) saugereau *2019-11-12 15:35:16*

**feat(quality): simplify configuration for jacoco -> sonarqube**


[0d83aa99c0b6e75](https://github.com/ekino/gradle-java-plugin/commit/0d83aa99c0b6e75) saugereau *2019-11-12 15:08:18*

**feat(quality): move to manual sonar configuration with travis-ci**


[f254de6b8e21c92](https://github.com/ekino/gradle-java-plugin/commit/f254de6b8e21c92) saugereau *2019-11-12 14:47:16*

**feat(quality): add sonarcloud configuration**


[85abe7f01bcae4d](https://github.com/ekino/gradle-java-plugin/commit/85abe7f01bcae4d) saugereau *2019-11-12 11:07:18*

**Publish github release as Draft**


[7376778c81855c5](https://github.com/ekino/gradle-java-plugin/commit/7376778c81855c5) Jean-Baptiste Hembise *2019-09-20 13:35:15*

**docs: Java version override example**


[66cd8065de6664d](https://github.com/ekino/gradle-java-plugin/commit/66cd8065de6664d) Fabien Thouraud *2019-08-29 13:36:46*

**feat: override Java version through dedicated configuration**


[09b66f62f199289](https://github.com/ekino/gradle-java-plugin/commit/09b66f62f199289) Fabien Thouraud *2019-08-29 13:36:46*

**chore: upgrade Gradle to 5.6.1**


[31615e5996eeaac](https://github.com/ekino/gradle-java-plugin/commit/31615e5996eeaac) Clement Stoquart *2019-08-28 08:57:57*

**Migrate code to Kotlin**


[344dc58a6f6a307](https://github.com/ekino/gradle-java-plugin/commit/344dc58a6f6a307) Clement Stoquart *2019-06-05 08:00:05*

**Add unit and integration tests**


[ffb8bb4b44ae8a2](https://github.com/ekino/gradle-java-plugin/commit/ffb8bb4b44ae8a2) Clement Stoquart *2019-06-04 12:52:42*

**[CHORE] Rewrite build script in Kotlin**


[80f1a5a40130cbb](https://github.com/ekino/gradle-java-plugin/commit/80f1a5a40130cbb) Clement Stoquart *2019-06-03 13:24:23*

**Upgrade Gradle to 5.4.1**


[45bf476f810d8cf](https://github.com/ekino/gradle-java-plugin/commit/45bf476f810d8cf) Clement Stoquart *2019-05-29 13:37:51*

**chore(github): update travis-ci github's api_key with ekino-ci**


[3c043c543ddfa0f](https://github.com/ekino/gradle-java-plugin/commit/3c043c543ddfa0f) saugereau *2019-05-15 14:19:51*

**chore(changelog): add git-changelog-gradle-plugin for generate changelog**


[efdd0da5e642f2a](https://github.com/ekino/gradle-java-plugin/commit/efdd0da5e642f2a) saugereau *2019-05-15 14:15:20*


## 1.0.2
### No issue

**chore(publish): remove pom dependency from pom section because it causes release task fail for dependency check**


[eac387c4f5bbe3a](https://github.com/ekino/gradle-java-plugin/commit/eac387c4f5bbe3a) saugereau *2019-04-11 14:14:49*

**chore(publish): add dependency in generated plugin marker jar and update README.md with snapshot repository**


[485b228f5863603](https://github.com/ekino/gradle-java-plugin/commit/485b228f5863603) saugereau *2019-04-11 08:28:40*

**chore(publish): add sourcesJar, javadocJar tasks and pluginMarker pom and signing for sonatype checks**


[8f06bfe4d97768b](https://github.com/ekino/gradle-java-plugin/commit/8f06bfe4d97768b) saugereau *2019-04-09 13:54:15*


## 1.0.0
### No issue

**chore(travis): update PUBLISH_RELEASE_REPO_URL**


[cb9e9f254dea84c](https://github.com/ekino/gradle-java-plugin/commit/cb9e9f254dea84c) saugereau *2019-04-05 09:47:57*

**chore(build): add release plugin**


[30a8aa41e7afd4f](https://github.com/ekino/gradle-java-plugin/commit/30a8aa41e7afd4f) saugereau *2019-04-05 09:02:22*

**chore(README): add badges and set version to 1.0.0-SNAPSHOT**


[59a228733483dec](https://github.com/ekino/gradle-java-plugin/commit/59a228733483dec) saugereau *2019-04-02 12:58:34*

**chore(deploy): fix deploy url in build.gradle, fix pluginid in README.md**


[6a390490e490233](https://github.com/ekino/gradle-java-plugin/commit/6a390490e490233) saugereau *2019-04-01 15:59:30*

**chore(version): set project as snapshot will deploy a snapshot version on maven central**


[40bfc9c40a9c6db](https://github.com/ekino/gradle-java-plugin/commit/40bfc9c40a9c6db) saugereau *2019-03-26 17:23:24*

**chore(deploy.sh): update openssl command line**


[10179a4a6961bee](https://github.com/ekino/gradle-java-plugin/commit/10179a4a6961bee) saugereau *2019-03-26 17:17:29*

**chore(travis/gradle dependencies): update travis script for build/publish, update testset version**


[e3d1d37b0a1ad82](https://github.com/ekino/gradle-java-plugin/commit/e3d1d37b0a1ad82) saugereau *2019-03-26 16:23:18*

**chore(travis): travis integration**


[0536abaa72d628f](https://github.com/ekino/gradle-java-plugin/commit/0536abaa72d628f) saugereau *2019-02-05 09:49:15*


