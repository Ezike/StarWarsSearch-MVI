![Android Build](https://github.com/Ezike/StarWarsSearch/workflows/Android%20Build/badge.svg)

# Star wars search

Search for star wars characters from [Star Wars API](https://swapi.dev/).
Built on Clean Architecture with MVI (Uni-directional data flow)
                                                                                                                        
## Features
* Clean Architecture with MVI (Uni-directional data flow)
* Kotlin Coroutines with Flow
* Dagger Hilt
* Kotlin Gradle DSL
* GitHub actions for CI

Hi 👋🏼👋🏼👋🏼, thanks for checking out/reviewing my project. For the rest of this document, I will be explaining the reasons for the technical decisions I made for this case study, the problems I faced, and what I learnt from them.

## Table of Contents

- [Prerequisite](#prerequisite)
- [Design](#Design)
- [Architecture](#architecture)
- [Testing](#testing)
- [Tech stack](#Libraries)
- [Extras](#Extras)

## Prerequisite
To build this project, you require:
- Android Studio 4.2 canary 4 or higher
- Gradle 6.5 

<h2 align="left">Screenshots</h2>
<h4 align="center">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_recents.jpg" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_search.png" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_detail.jpg" width="30%" vspace="10" hspace="10"><br>                                                                                                                
                                                                                                                                  
## Design
Before stepping into any coding and architecture decisions, I first had to come up with a sort of idea of how I wanted the app to look, and the kind of experience I wanted users to have when using the app. This also guided my decisions on what architecture and tools were best suited to bring this user experience to life. In addition to having a search screen and a detail screen, I also added a search history screen where users can revisit any previously searched item without needing to type text into the search bar and wait for results.
As seen in the images of the app above [Design](#Design), the app launches into the recent searches screen where the user can either see their recent searches or a prompt that tells them their recent searches will be displayed there later. If the user has recent searches, there's also a button that allows the user clear them.

Typing text into the search bar transitions the user into a `searching state` which could lead to loaded search results, empty or error state.

One thing to take into account is how much state handling needs to be done in order to deliver a stellar user experience. This is why I chose to use the `Model-View-Intent` architecture for the presentation layer of the app. This will be discussed in-depth in the section below.

## Architecture

The application follows clean architecture mainly because of the many benefits it brings to software which includes scalability, maintainability and testability.
It also enforces separation of concerns and dependency inversion where higher and lower level layers all depend on abstractions. In the project, I separate my layers into different gradle modules namely:

- Domain
- Data 
- Remote 
- Cache

These modules/layers are Java/Kotlin modules except the cache module. The catch here is that these lower level layers need to be independent from the Android framework. One of the key points of clean architecture is that your low level layers should be platform agnostic. We can plug our Domain, data and presentation layers into a kotlin multiplatform project and it will run just fine because we don't depend on the android framework. The cache and remote layers are implementation details that can be provided in any form (Firebase, GraphQl server, REST, ROOM, SQLDelight, etc) as long as it conforms to the business rules / contracts defined in the data layer which in turn also conforms to contract defined in domain.
The project also has one feature module `character_search` that holds the UI code and presents data to the users. The main app module does nothing more than just tying all the many layers of our app together. 

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over vanilla dagger and it was very easy to setup. Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming. It also has a fine suite of operators which make it a robust solution. 

#### Presentation
As stated earlier, the presentation layer is implemented with MVI architecture. There is a kotlin module called presentation which defines the contract that presenters should adhere to. The layer is also platform agnostic, making it easy to change implementation details (ViewModel, etc).

<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596780277/mvi_image.png" width="20%" vspace="8" hspace="8"><br>   
                                                                                                                                  
MVI architecture has two main components - The model and the view, everything else is the data that flows between these two components. The view state comes from the Model and goes into the View for rendering. Intents come from the View and is consumed by the model for processing. This relationship is most times refered to as unidirectional Data Flow.

I have a model class called `State machine` which encapsulates logic of how to process intents and make new view states. It relies on an intent processor that takes intents from the view, liases with a third-party (in this case our domain layer) to process the intent and then returns a result. Our results are taken up by a view state reducer function that uses our previous state and the result from the intent processor to make a new state that will be rendered on the UI. 
The views (fragments/components) output intents and take state as input. The viewmodel which is our presenter outputs state and takes in intents to process. 

The viewmodel in the project's architecture is very lean, depending solely on the state machine. The main advantage for using ViewModel is that it survives configuration changes, and thus ensures our user data persists across screen rotation.  
MVI is a great architecture when you don't want any surprises in user experience as state only comes from one source and is immutable. On the other hand it does come with a lot of boilerplate. Thankfully, there are a couple of libraries out there that abstract the implementation details and make it a lot easier to use. This is more of a bare bones implementation which was taken in order to represent the core concepts of MVI.

#### State rendering
For each screen, there is a sealed class of Viewstate, Viewintent and results. It's also possible to want to render multiple view states in one screen. and this led to creation of `view components`. 
View components are basically compound UI components that extend a viewGroup, which knows how to render it's own view state and also emit intents. In the search screen we have two components - `SearchHistoryView` and `SearchResultView`. The `SearchFragment` then passes state to these components to render on the screen. It also takes intents from the components to process.

The detail screen was a bit more complex requiring 4 view components to render each detail - `PlanetView`, `FilmView`, `SpeciesView`, and `ProfileView`. These views encapsulate logic for rendering success, error and empty states for the corresponding detail. The data for the views are fetched concurrently, allowing either of the views to render whenever its data is available. It also allows the user to retry the data fetch for each individual component if it fails. The states for each of view is decoupled from one another and is cached in a Flow persisted in the Fragment's viewModel.

#### Domain
The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app. All other layers have their own representation of these domain models and Mapper classes (or adapters) are used to transform the domain models to each layer's definition of that data.
Usecases which represent a single unit of business logic are also defined in the domain layer, and is consumed by the presentation layer.

Writing mappers and models can take a lot of effort and result in boilerplate, but they make the codebase much more maintainable and robust.
#### Data
Data implements the contract for providing data defined in domain, and in turn provides a contract that will be used to fetch data from different sources.
We have two data sources - `Remote` and `Cache`. Remote relies on retrofit to fetch data from the swapi.dev api, while the cache room uses room to persist the search history. The swapi.dev api returns non https urls for fetching character details. The remote layer, contains an `OkHttp Interceptor` that modifies requests and change their scheme to `https`. It also has another interceptor that modifies error exceptions that deal with poor connectivity (SocketTimeout, UnknownHost) to convey an error message the user will understand. 

## Testing
Testing is done withy junit 4 and Googl truth for making assertions. Coroutines test library is also used to test the suspending calls and the Flows.
No mocking library was used. The test uses fake objects for all tests, making it easy to properly verify their interactions with dependencies, and simulate the behavior of the real objects.
Each layer has it's own tests. The remote layer makes use of Mockwebserver to test the api requests and veriify that mock Json responses provided in the test resource folder are returned. The cache layer includes tests for the Room data access objects (DAO), ensuring that data is saved and retrieved as expected.
The use cases in domain are also tested to be sure that they are called with the required parameters or throw a NoParams exception, and also return the correct data or complete.
The presentation layer is extensively tested to ensure that the correct view state is produced when an intent is processed. The intent processors take an intent and produce a result. This behavior is tested to ensure it produces the right results for each intent. The view state reducer test also verifies that the correct view state is produced when the previous state and result are computed. Viewmodel tests verify that each call to processIntent produces the correct view state. It looks trivial since it is similar to the reducer's test but it is still very important.

Espresso tests aren't much. The test dependencies for UI tests are provided by dagger. I also hope to improve the UI test coverage.

## Extras

The gradle setup uses Kotlin Gradle DSL which allows you write gradle scripts in a familiar language, and also bringing Kotlin's rich language features to gradle configuration. The project also uses ktlint and the spotless plugin to enforce proper code style. The CI pipeline defined on Github actions runs ktlint on the projecet and runs the unit tests on push, pull request and merge to master branch. There's also a Ktlint pre-commit and pre-push git hook that verifies the project before committing code or pushing to remote repository. 

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
- [Kotlin Gradle DSL](https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin)
