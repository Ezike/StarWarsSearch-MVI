![Android Build](https://github.com/Ezike/StarWarsSearch/workflows/Android%20Build/badge.svg)

Hey there 👋🏼👋🏼👋🏼

This project contains an implementation of the Componentization idea as shown by the UI Engineering team at Netflix.

Resources: [blog](https://netflixtechblog.com/making-our-android-studio-apps-reactive-with-ui-components-redux-5e37aac3b244), [repo](https://github.com/julianomoraes/componentizationArch), [talk](https://www.droidcon.com/media-detail?video=362740979)

## Features
* Clean Architecture with MVI (Uni-directional data flow)
* View components
* Kotlin Coroutines with Flow
* Dagger Hilt
* Kotlin Gradle DSL
* GitHub actions for CI

## Prerequisite
To build this project, you require:
- Android Studio Bumble bee
- Gradle 7.1
- Kotlin 1.5

Run the following command in the root of the project to setup your Android Studio:
```
./setup.sh
```
This script will configure [ktlint](https://github.com/shyiko/ktlint)

<h2 align="left">Screenshots</h2>
<h4 align="center">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_recents.jpg" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_search.png" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_detail.jpg" width="30%" vspace="10" hspace="10"><br>

## Libraries

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Presenter for persisting view state across config changes
- [FlowBinding](https://github.com/ReactiveCircus/FlowBinding) - converts traditional view click listeners and call backs to Kotlin flow
- [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.  
- [Moshi](https://github.com/square/moshi) - JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the star wars api with the retrofit client.
- [Robolectric](http://robolectric.org/) - Unit test on android framework.
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [Dagger Hilt](https://dagger.dev/hilt) - handles dependency injection
  
  ## License

```license
Copyright 2020-2021 Ezike Tobenna

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

